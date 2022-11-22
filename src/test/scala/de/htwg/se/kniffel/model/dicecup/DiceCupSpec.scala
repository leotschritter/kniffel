package de.htwg.se.kniffel.model

import de.htwg.se.kniffel.model.dicecup.DiceCup
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
        val thrownDiceCup: DiceCup = diceCup.newThrow()
        thrownDiceCup.inCup.size + thrownDiceCup.locked.size should be(5)
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
      val list: List[Int] = List.range(1, 6)
      "contain" in {
        val emptyList: List[Int] = diceCup.dropListEntriesFromList(list, list)
        emptyList.size should be(0)
      }
    }
    "To evaluate the result of each throw get result" should {
      val diceCup = new DiceCup(List(2, 2), List(2, 2, 2), 2)
      val diceCup2 = new DiceCup(List(2, 3), List(4, 5, 6), 2)
      val diceCup3 = new DiceCup(List(2, 2), List(3, 3, 3), 2)
      "return the right value" in {
        diceCup.getResult(1) should be(10)
        diceCup.getResult(9) should be(10)
        diceCup.getResult(10) should be(10)
        diceCup.getResult(11) should be(0)
        diceCup3.getResult(11) should be(25)
        diceCup2.getResult(12) should be(30)
        diceCup.getResult(12) should be(0)
        diceCup2.getResult(13) should be(40)
        diceCup.getResult(13) should be(0)
        diceCup.getResult(14) should be(50)
        diceCup2.getResult(14) should be(0)
        diceCup.getResult(15) should be(10)
        diceCup.getResult(1998) should be(0)
      }
    }
    "when displayed" should {
      "have a specific format" in {
        val diceCup: DiceCup = new DiceCup(List(2, 2), List(2, 2, 2), 2)
        diceCup.toString() should be(
          "Im Becher: 2 2 2\nRausgenommen: 2 2\nVerbleibende Würfe: 3\n"
            + "Bitte wählen Sie aus: " + diceCup.indexOfField.keys.mkString(" ") + "\n"
        )
      }
    }
   /* "calculating a sum with a predicate" should {
      "be 0" in {
        val diceCup = new DiceCup(List(1, 2, 3, 4, 5), List(), 2)
        diceCup.getSum(diceCup.locked, diceCup.checkKniffel(diceCup.locked)) should be(0)
      }
    }*/
    "nextRound" should {
      "return a new DiceCup" in {
        var dc = DiceCup(List(1, 2, 3, 4, 5), List(), 2)
        dc = dc.nextRound()
        dc.inCup should be(List(0,0,0,0,0))
        dc.locked.size should be(0)
      }
    }
    "After 3 Throws newThrow" should {
      "not return a different Dicecup" in {
        var d = DiceCup(List(), List(0,0,0,0,0),-1)
        d = d.newThrow()
        d.remDices should be(-1)
        d should be(DiceCup(List(), List(0,0,0,0,0),-1))
      }
    }
  }
}

