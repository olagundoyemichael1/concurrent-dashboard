// Lab 8, Step 7: Wire and Test
package com.example.concurrent

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.module() {
    install(ContentNegotiation) { json() }
    val service = DashboardService()
    routing { dashboardRoutes(service) }
}

fun mainServer() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") { module() }.start(wait = true)
}

fun main() = mainServer()