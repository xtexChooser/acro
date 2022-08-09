package acro.bot

import acro.Acro
import kotlinx.serialization.Serializable

object WSSpawnTowerAFKBot : Bot() {

    val config: Config get() = Acro.config.wsSpawnTowerAfk!!

    override val name: String get() = "wssptafk"
    override val password: String get() = config.password

    @Serializable
    data class Config(
        val password: String,
    )

}