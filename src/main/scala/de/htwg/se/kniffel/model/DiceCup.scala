package de.htwg.se.kniffel.model

import scala.util.Random

case class DiceCup(locked: List[Int], inCup: List[Int]) {
  def this() = this(List.fill(0)(0), List.fill(5)(Random.between(1, 7)))

  def newThrow(): DiceCup = DiceCup(this.locked, List.fill(5 - lockedSize)(Random.between(1, 7)))

  def putDicesOut(sortOut:List[Int]): DiceCup = DiceCup(sortOut++locked, List.fill(5-sortOut.size-locked.size)(0))
  val inCupSize: Int = inCup.size
  val lockedSize: Int = locked.size
}
