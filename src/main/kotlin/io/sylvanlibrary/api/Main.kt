package io.sylvanlibrary.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin
import io.javalin.translator.json.JavalinJacksonPlugin
import io.sylvanlibrary.api.format.FormatService
import io.sylvanlibrary.api.util.Paths

fun main(args: Array<String>) {
  val kodein = Kodein { import(mainModule) }

  JavalinJacksonPlugin.configure(kodein.instance())

  val app = Javalin
      .create()
      .port(7000)
      .start()

  app.routes {
    get("status/check") { it.status(200) }

    path(Paths.Formats.ROOT) {
      val controller: FormatService = kodein.instance()

      get(Paths.INDEX) { ctx -> ctx.json(controller.all()) }
      get(Paths.Formats.SHOW) { ctx -> ctx.json(controller.get(ctx.param("name")!!)) }
      get(Paths.Formats.SETS) { ctx -> ctx.json(controller.sets(ctx.param("name")!!)) }
      get(Paths.Formats.CARDS) { ctx -> ctx.json(controller.cards(ctx.param("name")!!)) }
    }
  }
}
