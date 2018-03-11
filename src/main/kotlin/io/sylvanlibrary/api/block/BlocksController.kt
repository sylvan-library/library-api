package io.sylvanlibrary.api.block

import io.sylvanlibrary.api.entities.Block
import java.util.Optional

class BlocksController {
  fun all(): List<Block> {
    return listOf()
  }

  fun one(id: String): Optional<Block> {
    return Optional.empty()
  }
}
