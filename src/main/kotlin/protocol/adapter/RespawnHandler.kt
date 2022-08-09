package acro.protocol.adapter

import acro.bot.Bot
import com.github.steveice10.mc.protocol.data.game.ClientCommand
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundRespawnPacket
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerCombatKillPacket
import com.github.steveice10.mc.protocol.packet.ingame.serverbound.ServerboundClientCommandPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import org.slf4j.Logger

class RespawnHandler(private val bot: Bot) : SessionAdapter() {

    private val logger: Logger = bot.getLogger("PlayerInfoTracker")

    override fun packetReceived(session: Session, packet: Packet) {
        when (packet) {
            is ClientboundLoginPacket -> {
                if (packet.lastDeathPos != null) {
                    logger.info("Player died during the last session")
                    requestRespawn()
                }
            }

            is ClientboundPlayerCombatKillPacket -> {
                logger.info("CombatKillPacket received")
                logger.info(
                    "Player died, killed by entity ${packet.killerId}, message: " +
                            bot.messageFormatter.serialize(packet.message)
                )
                requestRespawn()
            }

            is ClientboundRespawnPacket -> {
                if (packet.lastDeathPos != null) {
                    logger.info("Respawn successfully, last death at: ${packet.lastDeathPos}")
                }
            }
        }
    }

    private fun requestRespawn() {
        logger.info("Requesting respawn...")
        bot + ServerboundClientCommandPacket(ClientCommand.RESPAWN)
        logger.info("Respawn requested")
    }

}