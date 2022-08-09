package acro.ctl.command

import acro.ctl.command.argument.bot
import acro.ctl.command.argument.getBot
import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher
import kotlin.system.exitProcess

fun CommandDispatcher<EventApi>.stop() {
    register(literal("stop")
        .executes { ctx ->
            if (ctx.source.guildId == "5237615478283154023") {
                ctx.source.replyText("检测到频道机器人提审测试频道，stop命令将被忽略")
                return@executes 1
            }
            ctx.source.replyText("Stopping Acro...")
            exitProcess(0)
        }
        .then(argument("bot", bot())
            .executes { ctx ->
                val bot = getBot(ctx, "bot")
                ctx.source.replyText("Stopping bot ${bot.name}...")
                bot.client.disconnect("!Disconnect by QQ guild administrator ${ctx.source.author.id}")
                0
            })
    )
}