package acro.ctl.command

import acro.ctl.command.argument.bot
import acro.ctl.command.argument.getBot
import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher

fun CommandDispatcher<EventApi>.reconnect() {
    val reconnect = register(
        literal("reconnect")
            .then(argument("bot", bot())
                .executes { ctx ->
                    getBot(ctx, "bot").client.disconnect("Reconnect request by guild user ${ctx.source.author.id}")
                    ctx.source.replyText("Reconnect requested")
                    0
                })
    )
    register(literal("reconn").redirect(reconnect))
}