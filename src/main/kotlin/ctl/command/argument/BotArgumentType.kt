package acro.ctl.command.argument

import acro.Acro
import acro.bot.Bot
import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

object BotArgumentType : ArgumentType<Bot> {

    private val notFoundException = DynamicCommandExceptionType { LiteralMessage("Bot not found: $it") }

    override fun parse(reader: StringReader): Bot {
        val name = reader.readString()
        return Acro.bots[name] ?: throw notFoundException.create(name)
    }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        examples.forEach {
            if (it.startsWith(builder.remainingLowerCase))
                builder.suggest(it)
        }
        return builder.buildFuture()
    }

    override fun getExamples() = Acro.bots.keys

}

fun bot() = BotArgumentType

fun getBot(ctx: CommandContext<*>, name: String) = ctx.getArgument(name, Bot::class.java)
