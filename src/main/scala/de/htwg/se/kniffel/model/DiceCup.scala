package de.htwg.se.kniffel.model

import scala.util.Random

case class DiceCup(locked: List[Int], inCup: List[Int], remDices: Int) {
  def this() = this(List.fill(0)(0), List.fill(5)(Random.between(1, 7)), 2)

  def newThrow(): DiceCup = {
    assert(remDices > 0)
    DiceCup(locked, List.fill(5 - lockedSize)(Random.between(1, 7)), remDices - 1)
  }

  def dropListEntriesFromList(entriesToDelete:List[Int], shortenedList:List[Int], n:Int = 0): List[Int] = {
    if (entriesToDelete.size != n)
      dropListEntriesFromList(entriesToDelete,
        shortenedList.take(shortenedList.lastIndexOf(entriesToDelete.apply(n)))
          ++ shortenedList.drop(shortenedList.lastIndexOf(entriesToDelete.apply(n)) + 1),
        n + 1)
    else
      shortenedList
  }

  def putDicesIn(sortIn: List[Int]): DiceCup = DiceCup(dropListEntriesFromList(sortIn, locked), inCup ++ sortIn, remDices)

  def putDicesOut(sortOut: List[Int]): DiceCup = DiceCup(sortOut ++ locked, dropListEntriesFromList(sortOut, inCup), remDices)

  val inCupSize: Int = inCup.size
  val lockedSize: Int = locked.size
}
