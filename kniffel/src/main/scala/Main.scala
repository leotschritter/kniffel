val f_column: List[String] =
  List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")
val number_of_players: Int = 4

def cells(cellWidth: Int = 3, desc: String) = "|" + desc + ("|" + " " * cellWidth) * (number_of_players) + "|" + '\n'

def bar(cellWidth: Int = 3) = (("+" + ("-") * cellWidth) * (number_of_players + 1)) + "+" + '\n'


def header(cellWidth: Int = 3): Unit = {
  print(" " * (cellWidth + 1))
  (for (n <- List.range(1, number_of_players + 1)) print("|" + ("P" + n).padTo(3, ' ')))
  println("|")
}

def mesh(cellWidth: Int = 3): Unit = {
  header()
  for (s <- f_column) print(bar(cellWidth) + cells(cellWidth, s.padTo(3, ' ')))
  print(bar(cellWidth))
}

@main def main: Unit =
  mesh()