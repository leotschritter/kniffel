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
    "To evaluate the result of each throw get result" should {
      val diceCup = new DiceCup()
      val list: List[Int] = List(2,2)
      val list2: List[Int] = List(2,2,2)
      val list3: List[Int] = List(2,3,4,5,6)
      "return the right value" in{
        diceCup.getResult(diceCup.mergeLists(list,list2),1) should be(10)
        diceCup.getResult(diceCup.mergeLists(list,list2),9) should be(10)
        diceCup.getResult(diceCup.mergeLists(list,list2),10) should be(10)
        diceCup.getResult(diceCup.mergeLists(list,list2),11) should be(0)
        diceCup.getResult(list3,12) should be(30)
        diceCup.getResult(diceCup.mergeLists(list,list2),12) should be(0)
        diceCup.getResult(list3,13) should be(40)
        diceCup.getResult(diceCup.mergeLists(list,list2),13) should be(0)
        diceCup.getResult(diceCup.mergeLists(list,list2),14) should be(50)
        diceCup.getResult(list3,14) should be(0)
        diceCup.getResult(diceCup.mergeLists(list,list2),15) should be(10)
      }
    }
  }
}
