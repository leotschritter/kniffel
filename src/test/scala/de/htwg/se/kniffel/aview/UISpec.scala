package de.htwg.se.kniffel
package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import de.htwg.se.kniffel.model.Game
import model.{Field, Move, Player}

class UISpec extends AnyWordSpec {
  "An UI" when {
    val controller: Controller = Controller(new Field(4), DiceCup(List(), List(1, 2, 3, 4, 5), 2), new Game(4))
    val ui = TUI(controller)
    "dices are put in or out" should {
      "put out or in" in {
        ui.diceCupPutOut(List(1, 2, 3))
        controller.diceCup.locked should be(List())
        ui.diceCupPutIn(List(1, 2))
        controller.diceCup.locked should be(List())
      }
    }
    "the player writes down a number" should {
      ui.writeDown(Move("2", 0, 0))
      "set the number into the field and trigger a new round" in {
        controller.diceCup.locked should be (List())
        controller.diceCup.inCup should be (List())
        controller.field.matrix.cell(0, 0) should be("2")
        controller.field.matrix.cell(0, 6) should be ("2")
        controller.field.matrix.cell(0, 7) should be ("0")
        controller.field.matrix.cell(0, 8) should be ("2")
        controller.field.matrix.cell(0, 16) should be ("0")
        controller.field.matrix.cell(0, 17) should be ("2")
        controller.field.matrix.cell(0, 18) should be ("2")
        controller.game.currentPlayer.playerID should be (1)
      }
    }
  }
}
