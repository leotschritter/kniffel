package de.htwg.se.kniffel.model

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

  def sum(sumTop: Int, sumBottom: Int): Game = {
    val bonus: Int = if sumTop >= 63 then 35 else 0
    Game(playersList, currentPlayer, remainingMoves, resultNestedList.updated(
      playersList.indexOf(currentPlayer),
      List(sumTop) :+ bonus :+ (sumTop + bonus) :+ sumBottom :+ (sumTop + bonus) :+ (sumBottom + sumTop + bonus)
    ))
  }

  def getCurrentList:List[Int] = resultNestedList(playersList.indexOf(currentPlayer))

  /*def getNewNestedList(sumTop: Int, sumBottom: Int): List[List[Int]] = {
    val bonus: Int = if sumTop >= 63 then 35 else 0
    resultNestedList.updated(
      playersList.indexOf(currentPlayer),
      List(sumTop) :+ bonus :+ (sumTop + bonus) :+ sumBottom :+ (sumTop + bonus) :+ (sumBottom + sumTop + bonus)
    )
  }*/

  // def next(): Game = Game(playersList, getNextPlayer(), remainingMoves - 1)
  /*def initializePlayersList(numberOfPlayers: Int, currentList: List[Player], currentNumber: Int = 0): List[Player] = {
    if (numberOfPlayers != currentNumber)
      initializePlayersList(
        numberOfPlayers, currentList :+ Player(currentNumber, "Player" + currentNumber + 1), currentNumber + 1)
    else
      currentList
  }*/