package acro.protocol.adapter.tracker

import acro.bot.Bot
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import org.slf4j.Logger

class ServerInfoTracker(bot: Bot) : SessionAdapter() {

    private val logger: Logger = bot.getLogger("ServerInfoTracker")

    override fun packetReceived(session: Session, packet: Packet) {
        if (packet is ClientboundLoginPacket) {
            // @TODO: parse chat type registry
            //packet.registry["minecraft:chat_type"]
            logger.info("Registry: ${packet.registry}")
            logger.info("Dimensions: ${packet.worldNames.toList()}")
            logger.info("Hashed Seed: ${packet.hashedSeed}")
            logger.info("Max Players: ${packet.maxPlayers}")
            logger.info("View Distance: ${packet.viewDistance}")
            logger.info("Simulation Distance: ${packet.simulationDistance}")
            logger.info("Reduced Debug Info: ${packet.isReducedDebugInfo}")
            logger.info("Immediate Respawn: ${packet.isEnableRespawnScreen}")
            logger.info("Debug World: ${packet.isDebug}")
            logger.info("Super-flat World: ${packet.isFlat}")
        }
    }

}