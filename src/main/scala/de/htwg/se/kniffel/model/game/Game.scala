package de.htwg.se.kniffel
package model.game

import model.Player

case class Game(playersList: List[Player], currentPlayer: Player, remainingMoves: Int, resultNestedList: List[List[Int]]):
  def this(numberOfPlayers: Int)
  = this((for (s <- 0 until numberOfPlayers) yield Player(s, "Player " + (s + 1))).toList,
    Player(0, "Player 1"),
    numberOfPlayers * 13,
    List.fill(numberOfPlayers, 6)(0))

  def next(): Option[Game] = {
    if (remainingMoves == 1)
      None
    else
      Some(Game(playersList, getNextPlayer, remainingMoves - 1, resultNestedList))
  }

  def getNextPlayer: Player =
    playersList((playersList.indexOf(currentPlayer) + 1) % playersList.length)

  def sum(value: Int, x: Int, y: Int): Game = {
    val sumTop: Int = if y < 6 then value + resultNestedList(playersList.indexOf(currentPlayer)).head else
      resultNestedList(playersList.indexOf(currentPlayer)).head
    val sumBottom: Int = if y > 8 then value + resultNestedList(playersList.indexOf(currentPlayer))(3) else
      resultNestedList(playersList.indexOf(currentPlayer))(3)
    val bonus: Int = if sumTop >= 63 then 35 else 0
    Game(playersList, currentPlayer, remainingMoves, resultNestedList.updated(
      playersList.indexOf(currentPlayer),
      List(sumTop) :+ bonus :+ (sumTop + bonus) :+ sumBottom :+ (sumTop + bonus) :+ (sumBottom + sumTop + bonus)
    ))
  }

  def getCurrentList: List[Int] = resultNestedList(playersList.indexOf(currentPlayer))
