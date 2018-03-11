package io.sylvanlibrary.api.util

class Paths {
  companion object {
    const val ROOT = "https://api.sylvanlibrary.io/v1"
    const val INDEX = "/"
  }

  public class Formats {
    companion object {
      const val ROOT = "formats"
      const val SHOW = ":name"
      const val SETS = ":name/sets"
      const val CARDS = ":name/cards"
    }
  }

  public class Sets {
    companion object {
      const val ROOT = "sets"
      const val SHOW = ":abbrev"
      const val CARDS = ":abbrev/cards"
    }
  }

  public class Cards {
    companion object {
      const val ROOT = "cards"
      const val SHOW = ":gatherer_id"
    }
  }
}
