package io.sylvanlibrary.api

import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin
import io.sylvanlibrary.api.controllers.BlocksController

fun main(args: Array<String>) {
  val app = Javalin.create().apply {
    port(7000)
  }.start()

  app.routes {
    path("status") {
      get("check") {
        it.status(200)
      }
    }

    path("blocks") {
      get("/") { ctx ->
        ctx.json(BlocksController().all())
      }

      get(":id") { ctx ->
        ctx.result("block for id ${ctx.param("id")}")
      }

      get(":id/sets") { ctx -> ctx.result("sets for block ${ctx.param("id")}") }
    }

    path("sets") {
      get("/") { ctx -> ctx.result("all sets!") }
      get(":abbrev") { ctx -> ctx.result("set ${ctx.param("abbrev")}") }
      get(":abbrev/cards") { ctx -> ctx.result("cards for set ${ctx.param("abbrev")}") }
    }

    path("cards") {
      get("/") {ctx ->
        val filter = ctx.queryParamOrDefault("filter", "")

        ctx.result("all cards with filter $filter")
      }
    }

    path("artists") {
      get("/") { ctx -> ctx.result("all artists!") }
      get(":id") { ctx -> ctx.result("artist ${ctx.param("id")}") }
      get(":id/cards") { ctx -> ctx.result("cards for artist ${ctx.param("id")}") }
    }
  }
}
