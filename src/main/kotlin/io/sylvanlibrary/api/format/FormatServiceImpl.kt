package io.sylvanlibrary.api.format

import io.sylvanlibrary.api.card.Card
import io.sylvanlibrary.api.set.Set
import io.sylvanlibrary.api.set.SetDao
import io.sylvanlibrary.api.util.Paths
import java.util.*

class FormatServiceImpl(
    private val formatDao: FormatDao,
    private val setDao: SetDao
) : FormatService {
  override fun all(): List<Format> {
    val formats = formatDao.all()
    val sets = setDao.allForFormats(formats.map(DbFormat::id))

    return formats.map {
      val formatSets = sets[it.id] ?: listOf()

      Format(
          it.name,
          buildCanonicalUrl(it.name),
          formatSets.size,
          formatSets,
          listOf(),
          listOf()
      )
    }
  }

  override fun get(name: String): Optional<Format> {
    return formatDao.get(name).map { dbFormat ->
      val sets = setDao.allForFormats(listOf(dbFormat.id))
      val formatSets = sets[dbFormat.id] ?: listOf()

      Format(
          dbFormat.name,
          buildCanonicalUrl(dbFormat.name),
          formatSets.size,
          formatSets,
          listOf(),
          listOf()
      )
    }
  }

  override fun sets(name: String): List<Set> {
    return listOf()
  }

  override fun cards(name: String): List<Card> {
    return listOf()
  }

  private fun buildCanonicalUrl(name: String): String {
    return "${Paths.ROOT}/${Paths.Formats.ROOT}/${name.toLowerCase()}"
  }
}
