package acro.protocol.adapter

import acro.Acro
import acro.bot.Bot
import com.github.steveice10.mc.protocol.data.game.BuiltinChatType
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundDisconnectPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundPlayerChatPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatCommandPacket
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundChatPacket
import com.github.steveice10.mc.protocol.packet.login.clientbound.ClientboundLoginDisconnectPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.*
import com.github.steveice10.packetlib.packet.Packet
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger

class SessionLogger(private val bot: Bot) : SessionAdapter() {

    private val logger: Logger = bot.getLogger("SessionStatus")

    override fun connected(event: ConnectedEvent) {
        logger.info("Connected to server")
    }

    override fun packetError(event: PacketErrorEvent) {
        logger.error("Packet error", event.cause)
    }

    override fun packetReceived(session: Session, packet: Packet) {
        when (packet) {
            is ClientboundLoginDisconnectPacket -> {
                logger.info(
                    "Disconnecting during the protocol login phase, reason: " +
                            bot.messageFormatter.serialize(packet.reason)
                )
            }

            is ClientboundDisconnectPacket -> {
                logger.info(
                    "Server requested disconnecting, reason: " +
                            bot.messageFormatter.serialize(packet.reason)
                )
            }

            is ClientboundPlayerChatPacket -> {
                logger.info(buildString {
                    append("<<< [PLAYER] ")
                    if (packet.senderTeamName != null) {
                        append('[')
                        append(bot.messageFormatter.serialize(packet.senderTeamName!!))
                        append(']')
                    }
                    append(bot.messageFormatter.serialize(packet.senderName))
                    append(": ")
                    val chatType =
                        BuiltinChatType.values().getOrNull(packet.typeId)?.name?.uppercase() ?: packet.typeId.toString()
                    if (chatType != "CHAT")
                        append("($chatType) ")
                    if (packet.unsignedContent != null) {
                        append("(UNSIGNED): ")
                        append(bot.messageFormatter.serialize(packet.unsignedContent!!))
                        append(" (SIGNED): ")
                        append(bot.messageFormatter.serialize(packet.signedContent))
                    } else {
                        append("(SIGNED): ")
                        append(bot.messageFormatter.serialize(packet.signedContent))
                    }
                })
                if (System.currentTimeMillis() - packet.timeStamp > 1000 * 60 * 2) {
                    logger.warn("Received a chat message before two minutes! Current: ${System.currentTimeMillis()}, Message: ${packet.timeStamp}")
                }
            }

            is ClientboundSystemChatPacket -> {
                logger.info(buildString {
                    append("<<< [SYSTEM] ")
                    if (packet.typeId == 0)
                        append("[ACTIONBAR] ")
                    append(bot.messageFormatter.serialize(packet.content))
                })
            }
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        when (packet) {
            is ServerboundChatPacket ->
                logger.info(">>>(CHAT ${packet.timeStamp} ${packet.salt}) ${packet.message}")

            is ServerboundChatCommandPacket ->
                logger.info(">>>(COMMAND ${packet.timeStamp} ${packet.salt}) ${packet.command}")
        }
    }

    override fun disconnecting(event: DisconnectingEvent) {
        logger.info("Disconnecting from server: ${event.reason}")
    }

    override fun disconnected(event: DisconnectedEvent) {
        if (event.cause == null) {
            logger.info("Disconnected from server: ${event.reason}")
        } else {
            logger.error("Disconnected from server: ${event.reason}", event.cause)
        }
        if (!event.reason.startsWith('!')) {
            bot.launch(CoroutineName("Reconnect")) {
                delay(Acro.config.serverEndpoint.reconnectDelay.toLong())
                bot.connect()
            }
        }
    }

}