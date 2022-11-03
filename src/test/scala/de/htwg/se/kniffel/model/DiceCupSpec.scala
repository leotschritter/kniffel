package de.htwg.se.kniffel.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class DiceCupSpec extends AnyWordSpec {
  "Dice Cup" when {
    "created" should {
      "have 5 Dices" in {
        val diceCup: DiceCup = new DiceCup()
        diceCup.inCupSize should be(5)
        diceCup.lockedSize should be(0)
        diceCup.inCup.foreach {
          s =>
            s should be < 7
            s should be > 0
        }

      }
    }
    "dices are thrown" should {
      val diceCup: DiceCup = new DiceCup()
      "contain two lists with all dices" in {
        val thrownDiceCup:DiceCup = diceCup.newThrow()
        thrownDiceCup.inCupSize + thrownDiceCup.lockedSize should be (5)
        thrownDiceCup.inCup.foreach {
          s =>
            s should be < 7
            s should be > 0
        }
      }
    }
    "dices are put out of the Dice Cup" should {
      val diceCup: DiceCup = new DiceCup()
      "should be inserted into the locked list of a new DiceCup Object" in {
        val sortOut: DiceCup = diceCup.putDicesOut(diceCup.inCup.take(2))
        sortOut.lockedSize should be(2)
        sortOut.inCupSize should be(3)
      }
    }
  }

}
