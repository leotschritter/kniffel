package de.htwg.se.kniffel
package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import model.{Field, Game, Move, Player}

class UISpec extends AnyWordSpec {
  "An UI" when {
    val controller = new Controller(new Field(4), DiceCup(List(), List(1, 2, 3, 4, 5), 2), new Game(4))
    val ui = TUI(controller)
    "dices are put in or out" should {
      "put out or in" in {
        ui.diceCupPutOut(List(1, 2, 3))
        controller.diceCup.locked should be(List(1, 2, 3))
        ui.diceCupPutIn(List(1, 2))
        controller.diceCup.locked should be(List(3))
      }
    }
    "next Round is set" should {
      ui.diceCupInput()
      "return a empty DiceCup" in {
        controller.diceCup.locked should be (List())
      }
    }
    "values are put into the Field" should {
      ui.gameAndFieldInput(new Player(0, "Player 1"), Move("12", 0, 2))
      "have the correct sum" in {
        controller.game.resultNestedList(0)(5) should be (12)
      }
    }
  }
}
