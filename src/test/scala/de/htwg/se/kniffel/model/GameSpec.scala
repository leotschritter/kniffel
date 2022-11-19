package de.htwg.se.kniffel.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.collection.immutable

class GameSpec extends AnyWordSpec {
  "A Game" when {
    val players: List[Player] = List(Player(0, "Player1"), Player(1, "Player2"), Player(2, "Player3"), Player(3, "Player4"))
    val game = Option(Game(players, players.head, players.length * 13, List.fill(players.length, 6)(0)))
    "created" should {
      "have a list of players" in {
        game.get.playersList should not be empty
      }
      "have the first player at first" in {
        game.get.currentPlayer should be(players.head)
      }
    }
    "move has ended" should {
      "get the next Player where the current player is not the last listElement" in {
        game.get.next().get.currentPlayer.playerName should be ("Player2")
      }
    }
  }
}
