package de.htwg.se.kniffel
package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import util.UndoManager

class UndoManagerSpec extends AnyWordSpec {
  "An UndoManager" should {
    val undoManager = new UndoManager[Int, Int]
    class C extends Command[Int, Int] {
      override def undoStep(a: Int, b: Int): (Int, Int) = (a - 1, b - 1)

      override def doStep(a: Int, b: Int): (Int, Int) = (a + 1, b + 1)

      override def noStep(a: Int, b: Int): (Int, Int) = (a, b)

      override def redoStep(a: Int, b: Int): (Int, Int) = (a + 1, b + 1)
    }

    val command = new C

    "have a do, undo and redo" in {
      var state = (0, 0)
      state = undoManager.doStep(state._1, state._2, command)
      state should be(1, 1)
      state = undoManager.undoStep(state._1, state._2)
      state should be(0, 0)
      state = undoManager.redoStep(state._1, state._2)
      state should be(1, 1)
    }

    "handle multiple undo steps correctly" in {
      var state = (0, 0)
      state = undoManager.doStep(state._1, state._2, command)
      state = undoManager.doStep(state._1, state._2, command)
      state should be(2, 2)
      state = undoManager.redoStep(state._1, state._2)
      state should be(2, 2)
      state = undoManager.undoStep(state._1, state._2)
      state should be(1, 1)
      state = undoManager.undoStep(state._1, state._2)
      state should be(0, 0)
      state = undoManager.redoStep(state._1, state._2)
      state should be(1, 1)
    }
  }
}
