package acro.protocol

import com.github.steveice10.mc.protocol.MinecraftProtocol
import com.github.steveice10.packetlib.Session

class AcroMinecraftProtocol : MinecraftProtocol {

    constructor() : super()
    constructor(username: String) : super(username)

}
