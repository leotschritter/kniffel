package de.htwg.se.kniffel.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MatrixSpec extends AnyWordSpec {
  "Matrix" when {
    "empty" should {
      "be created with specific dimension" in {
        val matrix = new Matrix(2)
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(0)))
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val matrix = new Matrix(2)
      "contain value" in {
        matrix.cell(0, 0) should be(0)
      }
      "value should be insertable" in {
        val returnedMatrix = matrix.fill(0, 0, 73)
        returnedMatrix.cell(0, 0) should be(73)
      }
    }
  }
}
