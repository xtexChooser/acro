package acro.protocol.adapter

import com.github.steveice10.mc.protocol.packet.handshake.client.HandshakePacket
import com.github.steveice10.mc.protocol.packet.login.server.LoginPluginRequestPacket
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent
import com.github.steveice10.packetlib.event.session.PacketSendingEvent
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.Packet
import org.slf4j.LoggerFactory

object FML2HandshakeHandler : SessionAdapter() {

    val logger = LoggerFactory.getLogger("FML2Handshake")

    override fun packetSending(event: PacketSendingEvent) {
        val packet = event.getPacket<Packet>()
        if (packet is HandshakePacket) {
            logger.info("Handshake packet found, appending FML2 suffix to hostname")
            event.setPacket(packet.run { withHostname("$hostname${0.toChar()}FML2${0.toChar()}") })
        }
    }

    override fun packetReceived(event: PacketReceivedEvent) {
        val packet = event.getPacket<Packet>()
        if (packet is LoginPluginRequestPacket) {
            if (packet.channel == "fml:loginwrapper") {
                logger.info("Got FML login wrapper plugin request")
            } else {
                logger.info("Got unknown login plugin request, channel: ${packet.channel}")
            }
        }
    }

}