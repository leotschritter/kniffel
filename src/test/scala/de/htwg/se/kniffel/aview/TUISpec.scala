package de.htwg.se.kniffel
package aview

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import model.{Field, Game, Move}
import scala.util.{Try,Success,Failure}

class TUISpec extends AnyWordSpec {
  "The TUI" should {
    val tui = TUI(Controller(new Field(4), DiceCup(List(), List(1, 2, 3, 4, 5), 2), new Game(4)))
    "recognize the input wd 1 0 as an input to the field at the position (0,0)" in {
      tui.analyseInput("wd 1") should be(Some(Move("1", 0, 0)))
    }
    "recognize the input o00 as move of stone O to field (0,0)" in {
      tui.analyseInput("po 1 2 3 4 5") should be(None)
    }
    "dice the DiceCup when input is d" in {
      tui.analyseInput("wd käsekuchen") should be (None)
    }
    "quit" in {
      tui.analyseInput("q") should be (None)
    }
    "a list is validated" in {
      val result = tui.invalidInput(List("wd"))
      result.isSuccess should be(false)
      val result2 = tui.invalidInput(List("wd", "1"))
      result2.isSuccess should be(true)
    }
  }
}
