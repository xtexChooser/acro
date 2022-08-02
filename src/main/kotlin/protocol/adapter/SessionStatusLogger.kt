package acro.protocol.adapter

import acro.Acro
import com.github.steveice10.packetlib.event.session.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

object SessionStatusLogger : SessionAdapter() {

    val logger = LoggerFactory.getLogger("SessionStatus")

    override fun connected(event: ConnectedEvent) {
        logger.info("Connected to server")
    }

    override fun packetError(event: PacketErrorEvent) {
        logger.error("Packet error", event.cause)
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
        Acro.launch(CoroutineName("Reconnect")) {
            delay(Acro.config.serverEndpoint.reconnectDelay.toLong())
            Acro.connect()
        }
    }

}