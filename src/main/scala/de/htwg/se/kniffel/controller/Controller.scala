package de.htwg.se.kniffel
package controller

import scala.annotation.targetName
import model.{Field, Game, Move}
import de.htwg.se.kniffel.model.dicecup.DiceCup
import util.Observable

case class Controller(var field: Field, var diceCup: DiceCup, var game: Game) extends Observable:

  def doAndPublish(doThis: Move => Field, move: Move): Unit =
    field = doThis(move)
    notifyObservers

  def doAndPublish(doThis: List[Int] => DiceCup, list: List[Int]): Unit =
    diceCup = doThis(list)
    notifyObservers

  def doAndPublish(doThis: => DiceCup): Unit =
    diceCup = doThis
    notifyObservers

  @targetName("next")
  def doAndPublish(doThis: => Game): Unit =
    game = doThis
    notifyObservers

  def doAndPublish(doThis: (Int, Int) => Game, sumTop:Int, sumBottom:Int): Unit =
    game = doThis(sumTop, sumBottom)
    notifyObservers

  def next(): Option[Game] =
    game.next()

  def sum(sumTop: Int, sumBottom: Int): Game =
    game.sum(sumTop, sumBottom)

  def putOut(list: List[Int]): DiceCup =
    diceCup.putDicesOut(list)

  def putIn(list: List[Int]): DiceCup =
    diceCup.putDicesIn(list)

  def dice(): DiceCup =
    diceCup.newThrow()

  def putValToField(move: Move): Field =
    field.put(move.value, move.x, move.y)

  def nextRound() = diceCup.nextRound()
  override def toString: String = field.toString
