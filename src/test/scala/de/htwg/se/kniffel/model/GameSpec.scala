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
    //Hier:
    "with no moves remaining next" should {
      "not build a new game" in {
        val players2: List[Player] = List(Player(0, "Player1"))
        var game2 = Option(Game(players2, players2.head, players2.length * 13, List.fill(players2.length, 6)(0)))

        for( a <- 1 to 12){
          game2 = game2.get.next()
        }
        game2.get.remainingMoves should be(1)
        game2.get.next() should be(None)
      }
    }
    "after writing down a Move Game" should {
      "return a new Game" in {
        val players2: List[Player] = List(Player(0, "Player1"))
        val game2 = Option(Game(players2, players2.head, players2.length * 13, List.fill(players2.length, 6)(0)))
        game2.get.sum(62,0) should be(Game(List(Player(0,"Player1")),Player(0,"Player1"),13,List(List(62, 0, 62, 0, 62, 62))))
        game2.get.sum(63,0) should be(Game(List(Player(0,"Player1")),Player(0,"Player1"),13,List(List(63, 35, 98, 0, 98, 98))))
        game2.get.getCurrentList should be(List(0, 0, 0, 0, 0, 0))
      }
    }
  }
}
