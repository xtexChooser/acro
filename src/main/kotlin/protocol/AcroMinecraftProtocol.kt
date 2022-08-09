package acro.protocol

import acro.protocol.adapter.CustomQueryHandler
import com.github.steveice10.mc.protocol.MinecraftProtocol
import com.github.steveice10.packetlib.Session

class AcroMinecraftProtocol : MinecraftProtocol {

    constructor() : super()
    constructor(username: String) : super(username)

    override fun newClientSession(session: Session) {
        super.newClientSession(session)
        session.addListener(CustomQueryHandler)
    }

}
