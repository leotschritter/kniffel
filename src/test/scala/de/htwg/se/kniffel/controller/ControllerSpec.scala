package de.htwg.se.kniffel
package controller

import de.htwg.se.kniffel.model.dicecup.DiceCup
import de.htwg.se.kniffel.model.game.Game
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import model.{Field, Move, Player, game}
import util.Observer

class ControllerSpec extends AnyWordSpec {
  "The Controller" should {
    val controller = Controller(new Field(2), new DiceCup(), new Game(4))
    val controller2 = Controller(new Field(1), new DiceCup(), new Game(1))
    "put a stone on the field when a move is made" in {
      val fieldWithMove = controller.putValToField(Move("12", 1, 2))
      fieldWithMove.matrix.cell(1, 2) should be("12")
      fieldWithMove.matrix.cell(0, 0) should be("")
    }
    "notify its observers on change" in {
      class TestObserver(controller: Controller) extends Observer :
        controller.add(this)
        var bing = false

        def update = bing = true
      val testObserver = TestObserver(controller)
      testObserver.bing should be(false)
      controller.doAndPublish(controller.putValToField, Move("73", 1, 2))
      testObserver.bing should be(true)
      controller.doAndPublish(controller.dice())
      testObserver.bing should be(true)
      controller.doAndPublish(controller.sum(62, 0, 0))
      testObserver.bing should be(true)
      controller.doAndPublish(controller.sum, controller.game.getCurrentList.head, 22, 33)
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
    "return an new Game" when {
      "finishing a move" in {
        controller2.next() should be(Some(game.Game(List(Player(0, "Player 1")), Player(0, "Player 1"), 12, List(List(0, 0, 0, 0, 0, 0)))))
        controller2.sum(63, 0, 0) should be(game.Game(List(Player(0, "Player 1")), Player(0, "Player 1"), 13, List(List(63, 35, 98, 0, 98, 98))))
        //controller2.
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
  }
}
