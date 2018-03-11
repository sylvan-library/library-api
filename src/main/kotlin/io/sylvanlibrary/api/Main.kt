package io.sylvanlibrary.api

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import io.javalin.ApiBuilder.get
import io.javalin.ApiBuilder.path
import io.javalin.Javalin
import io.javalin.translator.json.JavalinJacksonPlugin
import io.sylvanlibrary.api.format.FormatMapper
import io.sylvanlibrary.api.set.SetMapper
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
      val mapper: FormatMapper = kodein.instance()

      get(Paths.INDEX) { ctx -> ctx.json(mapper.all()) }
      get(Paths.Formats.SHOW) { ctx -> ctx.json(mapper.get(ctx.param("name")!!)) }
      get(Paths.Formats.SETS) { ctx -> ctx.status(501) }
      get(Paths.Formats.CARDS) { ctx -> ctx.status(501) }
    }

    path(Paths.Sets.ROOT) {
      val mapper: SetMapper = kodein.instance()

      get(Paths.INDEX) { ctx -> ctx.json(mapper.all()) }
      get(Paths.Sets.SHOW) { ctx -> ctx.json(mapper.get(ctx.param("abbrev")!!)) }
    }
  }
}
