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

}