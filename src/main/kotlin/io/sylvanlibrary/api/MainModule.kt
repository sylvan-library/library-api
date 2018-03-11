package io.sylvanlibrary.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.salomonbrys.kodein.*
import io.sylvanlibrary.api.format.FormatDao
import io.sylvanlibrary.api.format.FormatDaoImpl
import io.sylvanlibrary.api.format.FormatService
import io.sylvanlibrary.api.format.FormatServiceImpl
import io.sylvanlibrary.api.set.SetDao
import io.sylvanlibrary.api.set.SetDaoImpl
import io.sylvanlibrary.api.util.Config
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin

val mainModule = Kodein.Module {
  bind<FormatService>() with singleton { FormatServiceImpl(instance(), instance(), instance()) }
  bind<FormatDao>() with singleton { FormatDaoImpl(instance()) }
  bind<SetDao>() with singleton { SetDaoImpl(instance(), instance()) }
  bind() from singleton { Config() }

  bind<Jdbi>() with singleton {
    Jdbi.create(this.instance<Config>().connStr)
        .installPlugin(PostgresPlugin())
  }

  bind<ObjectMapper>() with singleton {
    val mapper = ObjectMapper()

    mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
    mapper.registerModule(Jdk8Module())
    mapper.registerModule(JavaTimeModule())
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    mapper
  }
}
