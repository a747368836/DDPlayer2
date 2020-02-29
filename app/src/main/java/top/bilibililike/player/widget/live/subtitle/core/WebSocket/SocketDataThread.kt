package top.bilibililike.subtitle.subtitle.WebSocket

import android.util.Log

import org.json.JSONObject
import top.bilibililike.player.widget.live.subtitle.core.WebSocket.DanmakuCallBack

import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.net.Socket
import java.net.SocketException

class SocketDataThread : Runnable {
    private var socket: Socket? = null
    private lateinit var roomId: String
    private lateinit var socketManager: SocketManager
    private var keepRunning = true
    private lateinit var callBack: DanmakuCallBack
    private lateinit var cacheBytes: ByteArray
    fun start(roomId: String) {
        this.roomId = roomId
        socketManager = SocketManager()
    }

    fun bind(callBack: DanmakuCallBack) {
        this.callBack = callBack
    }

    override fun run() {
        socket = socketManager.createSocket(this.roomId)
        if (socket!!.isConnected){
            val hdp = HandleDataThread()
            try {
                hdp.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            hdp.start()
        }



    }



    private inner class HandleDataThread : Thread() {
        internal lateinit var input: BufferedInputStream
        override fun run() {
            super.run()
            if (!socket!!.isClosed) {
                var bufferSize = 10 * 1024
                try {
                    bufferSize = socket!!.receiveBufferSize
                    println("连接成功bufferSize$bufferSize")

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                cacheBytes = ByteArray(bufferSize)
                while (keepRunning) {
                    try {
                        input = BufferedInputStream(socket!!.getInputStream())
                        val retLength = input.read(cacheBytes)
                        if (retLength > 0 && keepRunning) {
                            val recvData = ByteArray(retLength)
                            System.arraycopy(cacheBytes, 0, recvData, 0, retLength)
                            analyzeData(recvData)
                        }
                    } catch (e: SocketException) {
                        socketManager.stopHeartbeat()
                        if (!socket!!.isClosed) {
                            socket!!.close()
                        }
                        socket = socketManager.reConnect()
                        Log.d(TAG, "Socket Reconnected")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

        }

        private fun analyzeData(rawData: ByteArray) {

            val rawDataLength = rawData.size
            if (rawDataLength <= 32) {
                println("错误的数据或者不需要处理")
            } else {
                val inputStream = DataInputStream(ByteArrayInputStream(rawData))
                try {
                    val msgLength = inputStream.readInt()
                    if (msgLength > 32 && msgLength == rawDataLength) {
                        // 过滤头部长度和协议版本（两个short）
                        inputStream.readInt()
                        val action = inputStream.readInt()
                        //去除掉sequence
                        inputStream.readInt()
                        if (action == 5) {
                            val msgBodyLength = rawDataLength - 16
                            val msgBody = ByteArray(msgBodyLength)
                            if (inputStream.read(msgBody) == msgBodyLength) {
                                val jsonStr = String(msgBody, charset("UTF-8"))
                                val jsonObject = JSONObject(jsonStr)
                                //Log.d(TAG,"json = $jsonStr")
                                val cmd = jsonObject.get("cmd") as String
                                if (cmd == "DANMU_MSG") {
                                    var danMuData = jsonObject.getJSONArray("info").getString(1)
                                    Log.d("弹幕消息 = ", danMuData)
                                    if (danMuData.matches("(.*)(【.*】)(.*)".toRegex())) {
                                        if(danMuData.matches("(.+)(【.*】(.*))".toRegex())){
                                            danMuData = danMuData.replaceFirst("【","：").replace("】", "")
                                        }else{
                                            danMuData = danMuData.replaceFirst("【", "").replace("】", "")
                                        }

                                        val builder = StringBuilder(danMuData)
                                        callBack.onShow(builder.toString())
                                        Log.d("Subtitle", builder.toString())
                                    }
                                }
                            }
                        }
                    } else if (msgLength > 32 && msgLength < rawDataLength) {
                        //多条消息的处理
                        val singleData = ByteArray(msgLength)
                        System.arraycopy(rawData, 0, singleData, 0, msgLength)
                        analyzeData(singleData)
                        val remainLen = rawDataLength - msgLength
                        val remainDate = ByteArray(remainLen)
                        System.arraycopy(rawData, msgLength, remainDate, 0, remainLen)
                        analyzeData(remainDate)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        }
    }

    fun stop() {
        keepRunning = false
        if (!socket!!.isClosed) {
            socket!!.close()
        }
        socketManager.stopHeartbeat()
    }

    companion object {
        private val TAG = SocketDataThread::class.java.simpleName
    }
}
