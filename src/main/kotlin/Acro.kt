package acro

import acro.bot.Bot
import acro.bot.WSSpawnTowerAFKBot
import acro.config.AcroConfig
import acro.ctl.QQGuildBot
import com.typesafe.config.ConfigFactory
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.hocon.decodeFromConfig
import okio.Path.Companion.toOkioPath
import org.jetbrains.exposed.sql.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.coroutines.EmptyCoroutineContext

object Acro : CoroutineScope {

    val logger: Logger = LoggerFactory.getLogger("Acro")
    override val coroutineContext = EmptyCoroutineContext
    val basePath = File(System.getProperty("acro.base_path", "./")).absoluteFile.toOkioPath()

    @OptIn(ExperimentalSerializationApi::class)
    val config = Hocon.decodeFromConfig<AcroConfig>(ConfigFactory.parseFile((basePath / "config.conf").toFile()))

    lateinit var database: Database
    val bots = mutableMapOf<String, Bot>()

    fun start() {
        logger.info("Starting...")
        connectDatabase()
        QQGuildBot.start()
        if (config.wsSpawnTowerAfk != null)
            WSSpawnTowerAFKBot.start()
    }

    private fun connectDatabase() {
        logger.info("Connecting to main database...")
        database = config.database.connect()
        logger.info("Connected to main database")
    }

}