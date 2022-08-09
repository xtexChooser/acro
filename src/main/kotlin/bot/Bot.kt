package acro.bot

import acro.Acro
import acro.protocol.adapter.tracker.ServerInfoTracker
import acro.protocol.AcroMinecraftProtocol
import acro.protocol.adapter.LoginHandler
import acro.protocol.adapter.RespawnHandler
import acro.protocol.adapter.SessionLogger
import acro.protocol.adapter.tracker.PlayerInfoTracker
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket
import com.github.steveice10.packetlib.packet.Packet
import com.github.steveice10.packetlib.tcp.TcpClientSession
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import net.kyori.adventure.text.minimessage.MiniMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class Bot : CoroutineScope {

    val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)

    override val coroutineContext = Acro.coroutineContext + CoroutineName(logger.name)

    abstract val name: String
    open val playerName: String get() = "acro$name"
    abstract val password: String

    lateinit var protocol: AcroMinecraftProtocol
    lateinit var client: TcpClientSession
    val messageFormatter = MiniMessage.miniMessage()

    open fun start() {
        Acro.bots[name] = this
        connect()
    }

    fun connect() {
        logger.info("Connecting to game server...")
        protocol = AcroMinecraftProtocol(playerName)
        client = Acro.config.serverEndpoint.run { TcpClientSession(host, port, protocol, proxy?.toProxyInfo()) }
        client.addListener(SessionLogger(this))
        client.addListener(PlayerInfoTracker(this))
        client.addListener(ServerInfoTracker(this))
        client.addListener(RespawnHandler(this))
        client.addListener(LoginHandler(this))
        initClient()
        client.connect()
    }

    protected open fun initClient() {
    }

    fun getLogger(name: String): Logger = LoggerFactory.getLogger(logger.name + "/" + name)

    operator fun plus(packet: Packet) = client.send(packet)

    fun sendChat(text: String) {
        this + ServerboundChatPacket(text, System.currentTimeMillis(), 0, ByteArray(0), false)
    }

    fun sendChatCommand(text: String) {
        this + ServerboundChatCommandPacket(text, System.currentTimeMillis(), 0, emptyMap(), false)
    }

}