package io.sylvanlibrary.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.salomonbrys.kodein.*
import io.sylvanlibrary.api.format.FormatMapper
import io.sylvanlibrary.api.format.FormatMapperImpl
import io.sylvanlibrary.api.set.SetMapper
import io.sylvanlibrary.api.set.SetMapperImpl
import io.sylvanlibrary.api.util.Config
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin

val mainModule = Kodein.Module {
  bind<FormatMapper>() with singleton { FormatMapperImpl(instance(), instance(), instance()) }
  bind<SetMapper>() with singleton { SetMapperImpl(instance(), instance()) }
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
