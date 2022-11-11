package de.htwg.se.kniffel
package controller


import model.Field
import model.DiceCup
import model.Move
import util.Observable

case class Controller(var field: Field, var diceCup: DiceCup) extends Observable :

  def doAndPublish(doThis: Move => Field, move: Move): Unit =
    field = doThis(move)
    notifyObservers

  def putOut(list: List[Int]): DiceCup =
    diceCup.putDicesOut(list)

  def putIn(list: List[Int]): DiceCup =
    diceCup.putDicesIn(list)

  def dice(): DiceCup =
    diceCup.newThrow()

  def putValToField(value: String, x: Int, y: Int): Field =
    field.put(value, x, y)

  override def toString: String = field.toString
