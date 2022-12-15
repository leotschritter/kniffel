package de.htwg.se.kniffel
package model.fieldComponent

import model.fieldComponent.fieldBaseImpl.Matrix

trait IField {
  def putMulti(valueList: List[String], putInValue: String, x: Int, y: Int): IField

  def undoMove(valueList: List[String], x: Int, y: Int): IField

  def numberOfPlayers: Int

  def getMatrix: Matrix[String]
}
