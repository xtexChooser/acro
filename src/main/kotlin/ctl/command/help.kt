package acro.ctl.command

import acro.ctl.QQGuildBot
import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher

fun CommandDispatcher<EventApi>.help() {
    register(literal("help")
        .executes { ctx ->
            ctx.source.replyText(buildString {
                append("Commands:\n")
                append(QQGuildBot.buildHelp(ctx.rootNode, ctx.source))
            })
            0
        })
}