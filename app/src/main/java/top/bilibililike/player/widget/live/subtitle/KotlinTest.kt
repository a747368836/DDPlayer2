package top.bilibililike.subtitle

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okio.Buffer
import okio.ByteString
import top.bilibililike.subtitle.subtitle.WebSocket.SocketManager
import java.io.DataOutputStream
import java.util.*
import java.util.concurrent.TimeUnit

const val ROOM_ID = "14617277"
fun main() {
    linkStart(ROOM_ID)

}


lateinit var request: Request
lateinit var webSocket: WebSocket

fun linkStart(roomID: String) {
    request = Request.Builder()
            .url("ws://broadcastlv.chat.bilibili.com:2244/sub")
            .addHeader("Host","broadcastlv.chat.bilibili.com")
            .addHeader("Origin","https://live.bilibili.com")
            .addHeader("Upgrade","websocket")
            .addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
            .build()
    val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    webSocket = okHttpClient.newWebSocket(request,WebSocketListener())
    sendJoinRoomMsg(ROOM_ID)
    //okHttpClient.dispatcher().executorService().shutdown()

    val socketManager = SocketManager()
    //val dataThread = SocketDataThread()
    //dataThread.start(ROOM_ID)
    //dataThread.run()

}

private class WebSocketListener : okhttp3.WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        //sendJoinRoomMsg(ROOM_ID)
        val str = response.body()?.string()
        println("onOpen() $str")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("onMessage() $text")
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
        println("onMessage(byte) $bytes")
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        println("onClosing $reason")
    }

    override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
        println("onClosed $reason")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        val str = response?.body()?.string()
        val thro = t.toString()
        println("onFailure $str")
        print(thro)
        t?.printStackTrace()
    }

}

fun sendData(totalLength: Int, headLength: Int, version: Int, action: Int, param5: Int, data: ByteArray) {
    val buffer = Buffer()
    val outputStream = DataOutputStream(buffer.outputStream())
    outputStream.writeInt(totalLength)
    outputStream.writeShort(headLength)
    outputStream.writeShort(version)
    outputStream.writeInt(action)
    outputStream.writeInt(param5)
    outputStream.write(data)
    outputStream.flush()
    outputStream.close()
    val byteStr = buffer.readByteString().hex()
    //for (byte in byteStr) println(byte)
    print(byteStr)
    webSocket.send(byteStr)

}

fun sendJoinRoomMsg(roomID: String) {
    val uid: String = "" + (Random().nextInt(899999) + 100000)
    val jsonBody = "{\"roomid\": $roomID, \"uid\": $uid}"
    sendData(jsonBody.length + 16, 16, 1, 7, 1, jsonBody.toByteArray(charset("utf-8")))
}