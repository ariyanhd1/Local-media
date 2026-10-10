package com.jeet_studio.localstream4k.server

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UpnpDlnaService(private val serverName: String, private val port: Int) {
    private var isBroadcasting = false

    fun startBroadcast() {
        isBroadcasting = true
        Thread {
            try {
                val socket = DatagramSocket()
                val group = InetAddress.getByName("239.255.255.250")
                val ssdpNotify = "NOTIFY * HTTP/1.1\r\n" +
                        "HOST: 239.255.255.250:1900\r\n" +
                        "NT: urn:schemas-upnp-org:device:MediaServer:1\r\n" +
                        "NTS: ssdp:alive\r\n" +
                        "USN: uuid:localstream-pro-nas-4k\r\n" +
                        "SERVER: Android/14 UPnP/1.0 LocalStreamPro/4.8\r\n\r\n"

                val bytes = ssdpNotify.toByteArray()
                while (isBroadcasting) {
                    val packet = DatagramPacket(bytes, bytes.size, group, 1900)
                    socket.send(packet)
                    Thread.sleep(15000)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    fun stopBroadcast() {
        isBroadcasting = false
    }
}
