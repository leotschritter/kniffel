package de.htwg.se.kniffel.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class DiceCupSpec extends AnyWordSpec {
  "Dice Cup" when {
    "created" should {
      "have 5 Dices" in {
        val diceCup: DiceCup = new DiceCup()
        diceCup.inCup.size should be(5)
        diceCup.locked.size should be(0)
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
        thrownDiceCup.inCup.size + thrownDiceCup.locked.size should be (5)
        thrownDiceCup.inCup.foreach {
          s =>
            s should be < 7
            s should be > 0
        }
      }
    }
    "dices are put out the Dice Cup or in" should {
      val diceCup: DiceCup = new DiceCup()
      val sortOut: DiceCup = diceCup.putDicesOut(diceCup.inCup.take(2))
      "be inserted into the locked list of a new DiceCup Object" in {
        sortOut.locked.size should be(2)
        sortOut.inCup.size should be(3)
      }
      "be inserted into the inCup list of a new DiceCup Object" in {
        val putIn: DiceCup = sortOut.putDicesIn(sortOut.locked.take(2))
        putIn.locked.size should be(0)
        putIn.inCup.size should be(5)
      }
    }
    "list Entries are dropped from another list" should {
      val diceCup: DiceCup = new DiceCup()
      val list:List[Int] = List.range(1, 6)
      "contain" in {
        val emptyList:List[Int] = diceCup.dropListEntriesFromList(list, list)
        emptyList.size should be(0)
      }
    }
  }
}
