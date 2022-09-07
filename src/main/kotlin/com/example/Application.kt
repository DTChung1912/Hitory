package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*

private val HOME_HOST = "192.168.1.7"
private val T3_HOST = "192.168.123.102"
private val PIRAGO_GORAKU_HOST = "172.17.0.48"

fun main() {
    embeddedServer(Tomcat, port = 8080, host = T3_HOST) {
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
