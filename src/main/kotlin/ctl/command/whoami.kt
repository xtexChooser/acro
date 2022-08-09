package acro.ctl.command

import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.CommandDispatcher

fun CommandDispatcher<EventApi>.whoAmI() {
    register(literal("whoami")
        .executes { ctx ->
            val event = ctx.source
            event.replyText(buildString {
                append("Guild ID: ").append(event.guildId).append('\n')
                append("Channel ID: ").append(event.channelId).append('\n')
                append("Message ID: ").append(event.id).append('\n')
                append("Message Timestamp: ").append(event.timestamp).append('\n')
                append("Author: ").append(event.author.username).append('/').append(event.author.id).append('\n')
                append("Member: ").append('\n')
                    .append("  Joined At: ").append(event.member.joinedAt).append('\n')
                    .append("  Roles: ").append(event.member.roles).append('\n')
            })
            0
        })
}