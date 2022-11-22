package de.htwg.se.kniffel.model.dicecup

import scala.collection.immutable.ListMap
import scala.util.Random

case class DiceCup(locked: List[Int], inCup: List[Int], remDices: Int):
  def this() = this(List.fill(0)(0), List.fill(5)(Random.between(1, 7)), 2)

  def newThrow(): DiceCup = {
    if (remDices >= 0)
      DiceCup(locked, List.fill(5 - locked.size)(Random.between(1, 7)), remDices - 1)
    else
      this
  }

  def dropListEntriesFromList(entriesToDelete: List[Int], shortenedList: List[Int], n: Int = 0): List[Int] = {
    if (entriesToDelete.size != n)
      dropListEntriesFromList(entriesToDelete,
        shortenedList.take(shortenedList.lastIndexOf(entriesToDelete.apply(n)))
          ++ shortenedList.drop(shortenedList.lastIndexOf(entriesToDelete.apply(n)) + 1),
        n + 1)
    else
      shortenedList
  }

  def nextRound(): DiceCup = DiceCup(List.fill(0)(0), List.fill(5)(0), 2)

  def putDicesIn(sortIn: List[Int]): DiceCup = DiceCup(dropListEntriesFromList(sortIn, locked), inCup ++ sortIn, remDices)

  def putDicesOut(sortOut: List[Int]): DiceCup = DiceCup(sortOut ++ locked, dropListEntriesFromList(sortOut, inCup), remDices)

  def mergeLists(list1: List[Int], list2: List[Int]): List[Int] = list1 ::: list2

  val kniffelEvaluator = new Evaluator(EvaluateStrategy.kniffel)
  val threeOfAKindEvaluator = new Evaluator(EvaluateStrategy.threeOfAKind)
  val fourOfAKindEvaluator = new Evaluator(EvaluateStrategy.fourOfAKind)
  val fullHouseEvaluator = new Evaluator(EvaluateStrategy.fullHouse)
  val bigStreetEvaluator = new Evaluator(EvaluateStrategy.bigStreet)
  val smallStreetEvaluator = new Evaluator(EvaluateStrategy.smallStreet)
  val sumEvaluator = new Evaluator(EvaluateStrategy.sum)
  
  def getResult(index: Int): Int =
    val list: List[Int] = mergeLists(inCup, locked)
    index match {
      case 0 | 1 | 2 | 3 | 4 | 5 => list.filter(_ == index + 1).sum
      case 9 => threeOfAKindEvaluator.getResult(list)
      case 10 => fourOfAKindEvaluator.getResult(list)
      case 11 => fullHouseEvaluator.getResult(list)
      case 12 => smallStreetEvaluator.getResult(list)
      case 13 => bigStreetEvaluator.getResult(list)
      case 14 => kniffelEvaluator.getResult(list)
      case 15 => sumEvaluator.getResult(list)
      case _ => 0
    }

  val indexOfField: ListMap[String, Int] =
    ListMap("1" -> 0, "2" -> 1, "3" -> 2, "4" -> 3, "5" -> 4, "6" -> 5,
      "3X" -> 9, "4X" -> 10, "FH" -> 11, "KS" -> 12, "GS" -> 13, "KN" -> 14, "CH" -> 15)

  override def toString() = "Im Becher: " + inCup.mkString(" ")
    + "\nRausgenommen: " + locked.mkString(" ")
    + "\nVerbleibende Würfe: " + (remDices + 1)
    + "\nBitte wählen Sie aus: " + indexOfField.keys.mkString(" ")
    + "\n"
