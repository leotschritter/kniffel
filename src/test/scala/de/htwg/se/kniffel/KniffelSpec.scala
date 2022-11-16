/*
package de.htwg.se.kniffel

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class KniffelSpec extends AnyWordSpec {
  "Kniffel" should {
    "have a bar as String of form '+---+---+---+---+---+'" in {
      bar() should be("+---+---+---+---+---+" + '\n')
    }
    "have a scalable bar" in {
      bar(1, 1) should be("+-+-+" + '\n')
      bar(1, 2) should be("+-+-+-+" + '\n')
      bar(2, 1) should be("+--+--+" + '\n')
    }
    "have cells as String in form of ''" in {
      cells()should be("|   |   |   |   |   |" + '\n')
    }
    "have scalable cells" in {
      cells(1, 1) should be("| | |" + '\n')
      cells(1, 2) should be("| | | |" + '\n')
      cells(2, 1) should be("|  |  |" + '\n')
    }
    "have a header as List in Form of'List(    , |P1 , |P2 , |P3 , |P4 )'" in {
      header()shouldBe a [List[String]]
      header().mkString("")should be("    |P1 |P2 |P3 |P4 ")
    }
  }
  "have a mesh as a String" in {
    mesh()should be(
      "    |P1 |P2 |P3 |P4 \n" +
        "+---+---+---+---+---+\n" +
        "|1  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|2  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|3  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|4  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|5  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|6  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|G  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|B  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|O  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|3x |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|4x |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|FH |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|KS |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|GS |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|KN |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|CH |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|U  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|O  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n" +
        "|E  |   |   |   |   |\n" +
        "+---+---+---+---+---+\n")
  }

}*/
