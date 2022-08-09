package acro.ctl.command

import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher

fun CommandDispatcher<EventApi>.ping() {
    register(literal("ping")
        .executes { ctx ->
            ctx.source.replyText("Pong!")
            0
        })
}