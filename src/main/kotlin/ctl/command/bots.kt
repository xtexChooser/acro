package acro.ctl.command

import acro.Acro
import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher

fun CommandDispatcher<EventApi>.bots() {
    register(literal("bots")
        .executes { ctx ->
            ctx.source.replyText(buildString {
                append("Bots:\n")
                Acro.bots.forEach { (name, bot) ->
                    append(" - $name\n")
                    append("    Player Name: ${bot.playerName}\n")
                    append("    Session Connected: ${bot.client.isConnected}\n")
                    append("    Session Sharable: ${bot.client.isSharable}\n")
                    append("    Implementation: ${bot::class.qualifiedName}\n")
                }
            })
            0
        })
}