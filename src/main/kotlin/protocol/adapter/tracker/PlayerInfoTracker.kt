package acro.protocol.adapter.tracker

import acro.bot.Bot
import acro.protocol.data.EntityID
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import com.github.steveice10.mc.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import org.slf4j.Logger

class PlayerInfoTracker(bot: Bot) : SessionAdapter() {

    private val logger: Logger = bot.getLogger("PlayerInfoTracker")

    var playerEntityID: EntityID = 0
    lateinit var gameMode: GameMode
    lateinit var dimension: String

    override fun packetReceived(session: Session, packet: Packet) {
        if (packet is ClientboundLoginPacket) {
            playerEntityID = packet.entityId
            gameMode = packet.gameMode
            dimension = packet.worldName
            logger.info("Player Entity ID: $playerEntityID")
            logger.info("Hardcore: ${packet.isHardcore}")
            logger.info("Game Mode: $gameMode")
            logger.info("Previous Game Mode: ${packet.previousGamemode}")
            logger.info("Dimensions: ${packet.worldNames.toList()}")
        }
    }

}