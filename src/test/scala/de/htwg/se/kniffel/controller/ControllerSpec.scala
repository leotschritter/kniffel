/*
package de.htwg.se.kniffel
package controller

import de.htwg.se.kniffel.model.dicecup.DiceCup
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.{Field, Game, Move, Player}
import de.htwg.se.kniffel.model
import util.Observer
import util.Event

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {
    val controller = Controller(new Field(2), new DiceCup(), new Game(4))
    val controller2 = Controller(new Field(1), new DiceCup(), new Game(1))

    "notify its observers on change" in {
      class TestObserver(controller: Controller) extends Observer :
        controller.add(this)
        var bing = false

        def update(e: Event) = bing = true

      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.doAndPublish(controller.dice())
      testObserver.bing should be(true)
    }

    "dices are put out the Dice Cup or in" should {
      val sortOut: DiceCup = controller.putOut(controller.diceCup.inCup.take(2))
      "be inserted into the locked list of a new DiceCup Object" in {
        sortOut.locked.size should be(2)
        sortOut.inCup.size should be(3)
      }
      "be inserted into the inCup list of a new DiceCup Object" in {
        val putIn: DiceCup = controller.putIn(controller.diceCup.locked.take(2))
        putIn.locked.size should be(0)
        putIn.inCup.size should be(5)
      }
    }

    "dices are thrown" should {
      "contain two lists with all dices" in {
        val thrownDiceCup: DiceCup = controller.dice()
        thrownDiceCup.inCup.size + thrownDiceCup.locked.size should be(5)
        thrownDiceCup.inCup.foreach {
          s =>
            s should be < 7
            s should be > 0
        }
      }
    }

    "set a new Game object" when {
      "finishing a move" in {
        controller2.next()
        controller2.game should be(Some(model.Game(List(Player(0, "Player 1")), Player(0, "Player 1"), 12, List(List(0, 0, 0, 0, 0, 0)))).get)
      }
    }
    "after a Move" when {
      "write down the result" in {
        val con = controller.nextRound()
        con.remDices should be(2)
        con.inCup.size should be(0)
        con.locked.size should be(0)
      }
    }
    "when toString is called" should {
      "toString" in {
        controller.toString should be(controller.field.mesh())
      }
    }
    "when undo/redo/put is called" should {
      "put" in {
        controller.put(Move("11", 0, 0))
        controller.field.matrix.cell(0, 0) should be("11")
      }
      "undo" in {
        controller.undo
        controller.field.matrix.cell(0, 0) should be("")
      }
      "redo" in {
        controller.redo
        controller.field.matrix.cell(0, 0) should be("11")
      }
    }
    "when game quit" should {
      "end the game" in {
        val controller3: Controller = controller
        controller.quit().toString should be(controller3.quit().toString)
      }
    }
  }
}
*/
