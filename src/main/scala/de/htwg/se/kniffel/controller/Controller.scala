package de.htwg.se.kniffel
package controller

import scala.annotation.targetName
import model.{Field, Game, Move}
import util.Observable
import model.dicecup.DiceCup
import util.UndoManager
import controller.SetCommand
import util.Event

case class Controller(var field: Field, var diceCup: DiceCup, var game: Game) extends Observable :

  val undoManager = new UndoManager[Game, Field]

  def undo: Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.undoStep(game, field)
    game = r._1
    field = r._2
  }

  def redo: Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.redoStep(game, field)
    game = r._1
    field = r._2
  }

  def put(move: Move): Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.doStep(game, field, SetCommand(move))
    game = r._1
    field = r._2
    notifyObservers(Event.Move)
  }

  def doAndPublish(doThis: Move => Field, move: Move): Unit =
    field = doThis(move)
    notifyObservers(Event.Move)

  def doAndPublish(doThis: List[Int] => DiceCup, list: List[Int]): Unit =
    diceCup = doThis(list)
    notifyObservers(Event.Move)

  def doAndPublish(doThis: => DiceCup): Unit =
    diceCup = doThis
    notifyObservers(Event.Move)

  @targetName("next")
  def doAndPublish(doThis: => Game): Unit =
    game = doThis
    notifyObservers(Event.Move)

  def doAndPublish(doThis: (Int, Int) => Game, value: Int, y: Int): Unit =
    game = doThis(value, y)
    notifyObservers(Event.Move)

  def quit: Unit = notifyObservers(Event.Quit)

  def next(): Option[Game] =
    game.next()

  def sum(value: Int, y: Int): Game =
    game.sum(value, y)

  def putOut(list: List[Int]): DiceCup =
    diceCup.putDicesOut(list)

  def putIn(list: List[Int]): DiceCup =
    diceCup.putDicesIn(list)

  def dice(): DiceCup = {
    diceCup.dice()
    diceCup.state.throwDices(diceCup)
  }

  def putValToField(move: Move): Field =
    field.put(move.value, move.x, move.y)

  def nextRound(): DiceCup = diceCup.nextRound()

  override def toString: String = field.toString
