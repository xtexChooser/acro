package acro.protocol.adapter

import com.github.steveice10.mc.protocol.packet.login.clientbound.ClientboundCustomQueryPacket
import com.github.steveice10.mc.protocol.packet.login.serverbound.ServerboundCustomQueryPacket
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CustomQueryHandler: SessionAdapter() {

    private val logger: Logger = LoggerFactory.getLogger("CustomQueryHandler")

    override fun packetReceived(session: Session, packet: Packet) {
        if(packet is ClientboundCustomQueryPacket) {
            logger.info("Received custom query packet: ${packet.channel} ${packet.messageId} with ${packet.data.size} bytes")
            session.send(ServerboundCustomQueryPacket(packet.messageId))
        }
    }

}