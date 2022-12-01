/*
package de.htwg.se.kniffel
package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import de.htwg.se.kniffel.model.game.Game
import model.{Field, Move, Player}

class UISpec extends AnyWordSpec {
  "An UI" when {
    val controller = Controller(new Field(4), DiceCup(List(), List(1, 2, 3, 4, 5), 2), new Game(4))
    val ui = TUI(controller)
    "dices are put in or out" should {
      "put out or in" in {
        ui.diceCupPutOut(List(1, 2, 3))
        controller.diceCup.locked should be(List())
        ui.diceCupPutIn(List(1, 2))
        controller.diceCup.locked should be(List())
      }
    }
    "next Round is set" should {
      ui.diceCupInput()
      "return a empty DiceCup" in {
        controller.diceCup.locked should be (List())
      }
    }
    "values are put into the Field" should {
      ui.gameAndFieldInput(Move("12", 0, 2), Player(0, "Player 1"))
      "have the correct sum" in {
        controller.game.resultNestedList(0)(5) should be (12)
        ui.gameAndFieldInput(Move("12", 0, 9), Player(1, "Player 2"))
        controller.game.resultNestedList(1)(5) should be (12)
      }
    }
  }
}
*/
