package acro.protocol.adapter

import acro.bot.Bot
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundSystemChatPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import net.kyori.adventure.text.TextComponent
import org.slf4j.Logger

class LoginHandler(private val bot: Bot) : SessionAdapter() {

    private val logger: Logger = bot.getLogger("LoginHandler")

    override fun packetReceived(session: Session, packet: Packet) {
        when (packet) {
            is ClientboundSystemChatPacket -> {
                val content = packet.content
                if (content is TextComponent && content.content() == "Welcome ${bot.playerName} on Your Minecraft Server server") {
                    logger.info("Bot logged in successfully")
                }
            }

            is ClientboundLoginPacket -> {
                logger.info("Requesting for login")
                bot.sendChatCommand("login " + bot.password)
            }
        }
    }

}