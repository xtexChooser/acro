package acro.ctl.command

import com.hcyacg.protocol.event.message.EventApi
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder

@Suppress("NOTHING_TO_INLINE")
inline fun literal(name: String): LiteralArgumentBuilder<EventApi> =
    LiteralArgumentBuilder.literal(name)

@Suppress("NOTHING_TO_INLINE")
inline fun <T> argument(name: String, type: ArgumentType<T>): RequiredArgumentBuilder<EventApi, T> =
    RequiredArgumentBuilder.argument(name, type)
