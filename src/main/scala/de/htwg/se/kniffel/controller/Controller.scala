package de.htwg.se.kniffel
package controller


import model.Field
import util.Observable

case class Controller(var field: Field) extends Observable:
  def put(value: Int, x: Int, y: Int): Unit =
    field = field.put(value, x, y)
    notifyObservers
  override def toString: String = field.toString
