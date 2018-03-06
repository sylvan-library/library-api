package io.sylvanlibrary.api

import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin
import io.javalin.translator.json.JavalinJacksonPlugin
import io.sylvanlibrary.api.controllers.BlocksController
import io.sylvanlibrary.api.daos.SetDao
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule


fun main(args: Array<String>) {
  val mapper = ObjectMapper()
  mapper.registerModule(Jdk8Module())
  mapper.registerModule(JavaTimeModule())
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  JavalinJacksonPlugin.configure(mapper)

  val app = Javalin.create().apply {
    port(7000)
  }.start()

  val jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/sylvanlibrary?user=sylvanlibrary&password=jacesucks")
      .installPlugin(PostgresPlugin())

  val setsDao = SetDao(jdbi)

  app.routes {
    path("status") {
      get("check") {
        it.status(200)
      }
    }

    path("formats") {
      get("/:name/sets") { ctx ->
        val format = ctx.param("name")!!

        ctx.json(setsDao.allForFormat(format))
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
