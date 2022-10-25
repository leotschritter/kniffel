val f_column: List[String] =
  List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")


val cellWidth: Int = 4
def cells(cellWidth: Int = 3, numberOfPlayers: Int = 4, desc: String = ""): String = "|"
  + desc.padTo(cellWidth,' ') + ("|" + " " * cellWidth) * numberOfPlayers + "|" + '\n'

def bar(cellWidth: Int = 3, numberOfPlayers: Int = 4): String = (("+" + "-" * cellWidth)
  * (numberOfPlayers + 1)) + "+" + '\n'


def header(cellWidth: Int = 3, numberOfPlayers: Int = 4): List[String] = {
  (" " * (cellWidth + 1)) :: (for (n <- List.range(1,numberOfPlayers + 1)) yield "|"
    + ("P" + n).padTo(cellWidth, ' '))
}

def mesh(cellWidth: Int = 3, numberOfPlayers: Int = 4): String = {
  (header() :+ "\n" :+ (for (s <- f_column) yield bar(cellWidth)
    + cells(cellWidth, numberOfPlayers, s)).mkString("") :+ bar(cellWidth)).mkString("")
}

@main def main(): Unit =
//  println(mesh())
 println(cells(2,1))
  //println(bar(2,1))