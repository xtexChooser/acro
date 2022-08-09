package acro.config

import acro.bot.WSSpawnTowerAFKBot
import acro.ctl.QQGuildBot
import com.github.steveice10.packetlib.ProxyInfo
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import java.net.InetSocketAddress

@Serializable
data class AcroConfig(
    val serverEndpoint: ServerEndpoint = ServerEndpoint(),
    val database: DatabaseConfig,
    val qqGuildBot: QQGuildBot.Config,
    val wsSpawnTowerAfk: WSSpawnTowerAFKBot.Config? = null,
) {

    @Serializable
    data class ServerEndpoint(
        val host: String = "ding.PotatoCraft.cn",
        val port: Int = 25565,
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

    @Serializable
    data class DatabaseConfig(
        val url: String,
        val user: String,
        val password: String,
        val driver: String? = null,
    ) {

        fun connect() = if (driver == null) {
            Database.connect(url, user = user, password = password)
        } else {
            Database.connect(url, driver = driver, user = user, password = password)
        }

    }

}
