package de.htwg.se.kniffel
package controller

import scala.annotation.targetName
import model.{Field, Move}
import de.htwg.se.kniffel.model.dicecup.DiceCup
import de.htwg.se.kniffel.model.game.Game
import util.Observable
import util.UndoManager
import controller.SetCommand

case class Controller(var field: Field, var diceCup: DiceCup, var game: Game) extends Observable :


  val undoManager = new UndoManager

  def undo: Unit = undoManager.undoStep

  def redo: Unit = undoManager.redoStep; notifyObservers
/*  def undo: (Game, Field) = undoManager.undoStep(game, field)

  def redo: (Game, Field) = undoManager.redoStep(game, field)*/

  def put(move: Move): Unit = undoManager.doStep(SetCommand(move, this)); notifyObservers

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

/*  @targetName("put")
  def doAndPublish(doThis: Move => (Game, Field), move: Move): Unit =
    val gf = doThis(move)
    game = gf._1
    field = gf._2
    notifyObservers*/

  def next(): Option[Game] =
    game.next()

  def sum(value: Int, x: Int, y: Int): Game =
    game.sum(value, x, y)

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
