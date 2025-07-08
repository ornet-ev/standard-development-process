package org.somda

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.file
import org.apache.logging.log4j.kotlin.Logging
import org.somda.asciidoc.RunConverter
import org.somda.run.ModelToAsciidoc
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    DispatchConvert().main(args)
}

/**
 * Command line handling.
 */
class DispatchConvert : CliktCommand("build-apkp") {

    private val asciidoc by option("--asciidoc", "-a").flag(default = false)

    private val xmlschema by option("--xmlschema", "-x").flag(default = false)

    private val configFile by option("--config-file", "-f", help = "path to config file")
        .file()
        .required()
        .validate {
            check(it.exists()) {
                "File $it does not exist"
            }

            check(it.canRead()) {
                "File $it is not readable"
            }
        }


    override fun run() {
        logger.info { "Run with config file path $configFile" }

        if (asciidoc && xmlschema) {
            logger.error { "Cannot perform both conversions at once, use either --asciidoc or --xmlschema" }
            exitProcess(1)
        }

        if (asciidoc) {
            RunConverter(configFile)
            exitProcess(0)
        }

        if (xmlschema) {
            ModelToAsciidoc(configFile)
            exitProcess(0)
        }

        logger.warn { "No conversion option selected, use either --asciidoc or --xmlschema" }
        exitProcess(1)
    }

    private companion object : Logging
}