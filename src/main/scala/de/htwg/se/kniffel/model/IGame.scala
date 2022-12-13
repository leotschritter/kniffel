package de.htwg.se.kniffel.model

trait IGame {
  def next(): Option[IGame]
  def undoMove(value: Int, y: Int): IGame
  def sum(value: Int, y: Int): IGame
  def getCurrentPlayer : IPlayer
  def getPlayer(x: Int): IPlayer
}

trait IPlayer {
 def getPlayerID: Int
 def getPlayerName: String

}