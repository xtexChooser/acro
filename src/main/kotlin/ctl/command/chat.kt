package acro.ctl.command

import acro.ctl.command.argument.bot
import acro.ctl.command.argument.getBot
import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.arguments.StringArgumentType.greedyString

fun CommandDispatcher<EventApi>.chat() {
    register(
        literal("chat")
            .then(argument("bot", bot())
                .then(argument("text", greedyString())
                    .executes { ctx ->
                        getBot(ctx, "bot").sendChat(getString(ctx, "text"))
                        ctx.source.replyText("Message sent")
                        0
                    }
                )
            )
    )
}

fun CommandDispatcher<EventApi>.chatCommand() {
    register(
        literal("chat_cmd")
            .then(argument("bot", bot())
                .then(argument("command", greedyString())
                    .executes { ctx ->
                        getBot(ctx, "bot").sendChatCommand(getString(ctx, "command"))
                        ctx.source.replyText("Command sent")
                        0
                    }
                )
            )
    )
}
