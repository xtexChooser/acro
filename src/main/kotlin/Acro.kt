package acro

import acro.config.AcroConfig
import acro.protocol.AcroMinecraftProtocol
import acro.protocol.adapter.SessionStatusLogger
import com.github.steveice10.mc.protocol.MinecraftConstants
import com.github.steveice10.packetlib.tcp.TcpClientSession
import com.typesafe.config.ConfigFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import okio.Path.Companion.toOkioPath
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

object Acro : CoroutineScope {

    val logger = LoggerFactory.getLogger("Acro")
    override val coroutineContext = EmptyCoroutineContext

    val basePath = File(System.getProperty("acro.base_path", "/")).toOkioPath()

    @OptIn(ExperimentalSerializationApi::class)
    val config = Hocon.decodeFromConfig<AcroConfig>(ConfigFactory.parseFile((basePath / "config.conf").toFile()))

    lateinit var protocol: AcroMinecraftProtocol
    lateinit var client: TcpClientSession

    fun start() {
        logger.info("Starting...")
        logger.info("Current Minecraft protocol: ${MinecraftConstants.GAME_VERSION}/${MinecraftConstants.PROTOCOL_VERSION}")
        connect()
    }

    fun connect() {
        logger.info("Connecting...")
        protocol = AcroMinecraftProtocol(config.playerName)
        client = config.serverEndpoint.run { TcpClientSession(host, port, protocol, proxy?.toProxyInfo()) }
        client.addListener(SessionStatusLogger)
        client.connect()
    }

}