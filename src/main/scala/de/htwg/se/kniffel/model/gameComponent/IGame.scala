package de.htwg.se.kniffel
package model.gameComponent

trait IGame {
  def next(): Option[IGame]

  def undoMove(value: Int, y: Int): IGame

  def sum(value: Int, y: Int): IGame

  def getPlayerID: Int

  def getPlayerName: String

  def getPlayerName(x: Int): String

  def getResultNestedList(x: Int): List[Int]
}
