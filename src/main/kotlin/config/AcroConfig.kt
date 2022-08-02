package acro.config

import com.github.steveice10.packetlib.ProxyInfo
import kotlinx.serialization.Serializable
import java.net.InetSocketAddress

@Serializable
data class AcroConfig(
    val serverEndpoint: ServerEndpoint = ServerEndpoint(),
    val playerName: String = "acro",
) {

    @Serializable
    data class ServerEndpoint(
        val host: String = "ricky.dyn.hjt.xyz",
        val port: Int = 33333,
        val proxy: ProxyConfig? = null,
        val reconnectDelay: Int = 5000,
    )

    @Serializable
    data class ProxyConfig(
        val type: ProxyInfo.Type,
        val host: String,
        val port: Int,
        val username: String? = null,
        val password: String? = null,
    ) {

        fun toProxyInfo() = ProxyInfo(type, InetSocketAddress(host, port), username, password)

    }

}
