package io.sylvanlibrary.api.util

class Config {
  val host: String = System.getenv("API_HOST")
  val connStr: String = System.getenv("API_CONN_STR")
}
