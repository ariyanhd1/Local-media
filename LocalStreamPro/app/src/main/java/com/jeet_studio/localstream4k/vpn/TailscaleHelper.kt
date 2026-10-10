package com.jeet_studio.localstream4k.vpn

import java.net.NetworkInterface

object TailscaleHelper {
    fun getTailscaleIp(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface = interfaces.nextElement()
                if (iface.name.contains("tun", true) || iface.name.contains("tailscale", true)) {
                    val addresses = iface.inetAddresses
                    while (addresses.hasMoreElements()) {
                        val addr = addresses.nextElement()
                        val host = addr.hostAddress ?: ""
                        if (host.startsWith("100.")) {
                            return host
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
