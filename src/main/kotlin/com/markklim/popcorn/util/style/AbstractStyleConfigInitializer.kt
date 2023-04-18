package com.markklim.popcorn.util.style

import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

abstract class AbstractStyleConfigInitializer(
    val project: Project,
) {
    abstract fun init()

    protected fun copyConfig(from: File, to: File, path: String) {
        val fromFile = File(from, path)
        val toFile = File(to, path)

        if (!toFile.parentFile.exists() && !toFile.parentFile.mkdirs() && !toFile.createNewFile()) {
            throw IllegalStateException("Failed to create directories: ${toFile.parentFile.absolutePath}")
        }

        val sourceStream: InputStream = if (fromFile.exists()) {
            FileInputStream(fromFile)
        } else {
            this.javaClass.getResourceAsStream("/style/$path")
                ?: throw IllegalStateException("Default styles not found")
        }

        toFile.createNewFile()
        toFile.writeBytes(sourceStream.readAllBytes())
    }

    companion object {
        const val USER_CONFIG_PATH = "gradle/style/config/"
    }
}
