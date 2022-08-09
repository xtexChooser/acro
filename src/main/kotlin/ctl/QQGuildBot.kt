package acro.ctl

import acro.Acro
import acro.ctl.command.*
import com.hcyacg.protocol.common.BotEvent
import com.hcyacg.protocol.common.BotManager
import com.hcyacg.protocol.event.message.AtMessageCreateEvent
import com.hcyacg.protocol.event.message.EventApi
import com.hcyacg.protocol.event.message.MessageCreateEvent
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.tree.CommandNode
import kotlinx.serialization.Serializable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object QQGuildBot : BotEvent() {

    val logger: Logger = LoggerFactory.getLogger("QQGuildBot")
    val config get() = Acro.config.qqGuildBot
    val commandDispatcher = CommandDispatcher<EventApi>()

    fun start() {
        logger.info("Starting QQ guild bot")
        registerCommands()
        BotManager.configuration("Bot ${config.botID}.${config.token}", true).addListen(this)
        logger.info("QQ guild bot online")
    }

    private fun registerCommands() {
        commandDispatcher.apply {
            whoAmI()
            help()
            ping()
            bots()
            stop()
            reconnect()
            chat()
            chatCommand()
        }
    }

    override suspend fun onAtMessageCreate(event: AtMessageCreateEvent) {
        logger.info("<<< (AT) " + event.messageContent())
        val command = event.messageContent().trim().trimStart('/')
        executeCommand(event, command)
    }

    override suspend fun onMessageCreate(event: MessageCreateEvent) {
        logger.info("<<< " + event.content)
        val command = event.content.trim()
        if (command.startsWith('~')) {
            executeCommand(event, command.substring(1))
        }
    }

    fun executeCommand(event: EventApi, command: String) {
        logger.info("Executing command: $command")
        try {
            val parsed = commandDispatcher.parse(command, event)
            if (parsed.context.command == null
                && parsed.context.nodes.size == 1
                && command == parsed.context.nodes.first().node.name
            ) {
                logger.info("Displaying usage for $command")
                event.replyText(buildString {
                    append("Usages:\n")
                    append(buildHelp(parsed.context.nodes.first().node, event))
                })
            } else {
                commandDispatcher.execute(parsed)
            }
        } catch (e: CommandSyntaxException) {
            event.replyText(e.message!!)
        } catch (e: Throwable) {
            logger.error("Error executing command: $command", e)
            event.replyText(e.toString())
        }
    }

    fun buildHelp(node: CommandNode<EventApi>, source: EventApi) = buildString {
        commandDispatcher.getAllUsage(node, source, true)
            .forEach {
                append(" - ").append(it).append('\n')
            }
    }

    @Serializable
    data class Config(
        val botID: Int,
        val token: String,
        val guildID: String,
        val channelID: String,
    )

}