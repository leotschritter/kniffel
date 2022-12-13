package de.htwg.se.kniffel.model

case class Player(playerID:Int, playerName:String) extends IPlayer
  def getPlayerID: Int = playerID
  def getPlayerName: String = playerName
