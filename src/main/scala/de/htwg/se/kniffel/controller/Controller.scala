package de.htwg.se.kniffel
package controller

import scala.annotation.targetName
import model.{Field, Move}
import de.htwg.se.kniffel.model.dicecup.DiceCup
import de.htwg.se.kniffel.model.game.Game
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

  def doAndPublish(doThis: (Int, Int, Int) => Game, value: Int, x: Int, y: Int): Unit =
    game = doThis(value, x, y)
    notifyObservers

  def next(): Option[Game] =
    game.next()

  def sum(value: Int, x: Int, y: Int): Game =
    game.sum(value, x, y)

  def putOut(list: List[Int]): DiceCup =
    diceCup.putDicesOut(list)

  def putIn(list: List[Int]): DiceCup =
    diceCup.putDicesIn(list)

  def dice(): DiceCup = { diceCup.dice(); diceCup.state.throwDices(diceCup)}

  def putValToField(move: Move): Field =
    field.put(move.value, move.x, move.y)

  def nextRound(): DiceCup = diceCup.nextRound()

  override def toString: String = field.toString
