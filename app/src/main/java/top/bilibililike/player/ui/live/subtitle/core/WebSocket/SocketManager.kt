package top.bilibililike.subtitle.subtitle.WebSocket

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*
import java.util.concurrent.TimeUnit


class SocketManager {
    private var socket: Socket? = null
    private var disposable: Disposable? = null
    private var keepRunning = true

    companion object {
        const val TAG = "SocketManager"
        lateinit var roomId: String
        const val socketServerUrl = "broadcastlv.chat.bilibili.com"
        private val heartBeatByteArray = "[object Object]".toByteArray(charset("utf-8"))
    }

    fun createSocket(roomId: String): Socket {
        SocketManager.roomId = roomId
        val address = InetSocketAddress(socketServerUrl, 788)
        socket = Socket()
        socket!!.receiveBufferSize = 10 * 1024
        socket!!.connect(address)
        sendJoinRoomMsg(roomId)
        val observable = Observable.interval(10, TimeUnit.SECONDS)
                .takeWhile({keepRunning})
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext { aLong ->
                    sendSocketData(16 + heartBeatByteArray.size, 16, 1, 2, 1, heartBeatByteArray)
                    Log.d(TAG, "弹幕 sendHeartBeat")
                }
                .doOnDispose { Log.d(TAG, "heartBeat onDispose") }
                .doOnError { throwable ->
                    if (throwable.message!!.contains("SocketException") && !socket!!.isClosed) {
                        Log.d(TAG, "弹幕 onError Socket")
                        socket!!.close()
                        socket = reConnect()
                    }
                }

        disposable = observable.subscribe()
        return socket!!
    }

    fun sendSocketData(totalLength: Int, headLength: Int, version: Int, action: Int, param5: Int, data: ByteArray) {
        val outputStream = DataOutputStream(socket?.getOutputStream())
        outputStream.writeInt(totalLength)
        outputStream.writeShort(headLength)
        outputStream.writeShort(version)
        outputStream.writeInt(action)
        outputStream.writeInt(param5)
        outputStream.write(data)
        outputStream.flush()
    }

    fun sendJoinRoomMsg(roomID: String) {
        val uid: String = "" + (Random().nextInt(899999) + 100000)
        val jsonBody = "{\"roomid\": $roomID, \"uid\": $uid}"
        sendSocketData(jsonBody.length + 16, 16, 1, 7, 1, jsonBody.toByteArray(charset("utf-8")))
    }

    fun reConnect(): Socket {
        stopHeartbeat()
        return createSocket(roomId)
    }

    fun stopHeartbeat() {
        keepRunning = false
        if (disposable != null && !disposable!!.isDisposed){
            disposable?.dispose()
        }

    }

}