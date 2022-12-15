package de.htwg.se.kniffel
package controller

import scala.annotation.targetName
import model.{IField, IGame, Move}
import util.Observable
import model.dicecup.DiceCup
import util.UndoManager
import controller.SetCommand
import util.Event

case class Controller(var field: IField, var diceCup: DiceCup, var game: IGame) extends Observable :

  val undoManager = new UndoManager[IGame, IField]

  def undo: Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.undoStep(game, field)
    game = r._1
    field = r._2
    notifyObservers(Event.Move)
  }

  def redo: Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.redoStep(game, field)
    game = r._1
    field = r._2
    notifyObservers(Event.Move)
  }

  def put(move: Move): Unit = {
    diceCup = diceCup.nextRound()
    val r = undoManager.doStep(game, field, SetCommand(move))
    game = r._1
    field = r._2
  }

  def quit(): Unit = notifyObservers(Event.Quit)

  def next(): Unit =
    game = game.next().get

  // doAndPublish for putOut and putIn
  def doAndPublish(doThis: List[Int] => DiceCup, list: List[Int]): Unit =
    diceCup = doThis(list)
    notifyObservers(Event.Move)

  def putOut(list: List[Int]): DiceCup =
    diceCup.putDicesOut(list)

  def putIn(list: List[Int]): DiceCup =
    diceCup.putDicesIn(list)

  // doAndPublish for nextRound() and dice()
  def doAndPublish(doThis: => DiceCup): Unit =
    diceCup = doThis
    notifyObservers(Event.Move)

  def dice(): DiceCup = {
    diceCup.dice()
    diceCup.throwDices(diceCup)
  }

  def nextRound(): DiceCup = diceCup.nextRound()

  override def toString: String = field.toString
