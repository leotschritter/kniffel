package de.htwg.se.kniffel.model


case class Field(matrix: Matrix[String]):
  def this(numberOfPlayers: Int) = this(new Matrix[String](numberOfPlayers))

  val defaultPlayers: Int = matrix.rows.flatten.length / 19

  val first_column: List[String] =
    List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")

  val first_column_long: List[String] =
    List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")


  def cells(cellWidth: Int = 3, numberOfPlayers: Int = defaultPlayers, desc: String = "", v: List[String] = List.fill(defaultPlayers)("")): String =
    "|" + desc.padTo(cellWidth, ' ') + (for (s <- v) yield "|" + s.padTo(cellWidth, ' ')).mkString("") + "|" + '\n'

  def bar(cellWidth: Int = 3, numberOfPlayers: Int = defaultPlayers): String = (("+" + "-" * cellWidth)
    * (numberOfPlayers + 1)) + "+" + '\n'

  def header(cellWidth: Int = 3, numberOfPlayers: Int = defaultPlayers): List[String] =
    (" " * (cellWidth + 1)) :: (for (n <- List.range(1, numberOfPlayers + 1)) yield "|"
      + ("P" + n).padTo(cellWidth, ' '))

  def mesh(cellWidth: Int = 3, numberOfPlayers: Int = defaultPlayers): String =
    (header() :+ "\n" :+ (for (s <- 0 to 18) yield bar(cellWidth)
      + cells(cellWidth, numberOfPlayers, first_column.apply(s), matrix.rows.toList.flatten.slice(
      0 + s * numberOfPlayers, s * numberOfPlayers + numberOfPlayers
    ))).mkString("") :+ bar(cellWidth)).mkString("")

  def put(value: String, x: Int, y: Int): Field = {
    //val last: Option[Int] = if (y < 9 && y > 5) || (y > 15 && y < 19) then lastY else Option(y)
    copy(matrix.fill(x, y, value))
  }

  def undoMove(x: Int, y: Int): Field = put("", x, y)


  override def toString = mesh()

/*
def put(value: String, x: Int, y: Int): Field = {
  val last: Option[List[Int]] = if (y < 9 && y > 5) || (y > 15 && y < 19) then lastY else
    lastY match
      case None => None
      case _ => Option(lastY.get:+y)
  copy(matrix.fill(x, y, value), last)
}

def undoMove(x: Int): Option[Field] = {
  lastY match
    case None => None
    case _ =>
      Option(copy(matrix.fill(x, lastY.get.last, ""), Option(lastY.get.dropRight(1))))
}
*/