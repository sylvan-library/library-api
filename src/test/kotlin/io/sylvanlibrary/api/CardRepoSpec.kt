package io.sylvanlibrary.api

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.assertj.core.api.Assertions.assertThat

object CardRepoSpec: Spek({
  describe("a thing") {
    it("does a thing") {
      assertThat(CardRepo().aMethod()).isEqualTo("hello")
    }
  }
})
