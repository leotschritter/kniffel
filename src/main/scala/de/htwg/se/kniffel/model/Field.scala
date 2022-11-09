package de.htwg.se.kniffel.model


case class Field(matrix: Matrix[Int]):
  def this(numberOfPlayers: Int) = this(new Matrix[Int](numberOfPlayers))
  val size = matrix.size
  val first_column: List[String] =
    List("1", "2", "3", "4", "5", "6", "G", "B", "O", "3x", "4x", "FH", "KS", "GS", "KN", "CH", "U", "O", "E")

  def cells(cellWidth: Int = 3, numberOfPlayers: Int = 4, desc: String = ""): String =
    "|" + desc.padTo(cellWidth, ' ') + ("|" + " " * cellWidth) * numberOfPlayers + "|" + '\n'

  def bar(cellWidth: Int = 3, numberOfPlayers: Int = 4): String = (("+" + "-" * cellWidth)
    * (numberOfPlayers + 1)) + "+" + '\n'

  //def cellss(cellWidth: Int, col: Int) =
    //matrix.col(col).map(_.toString).map(" " * ((cellWidth - matrix.col(col).toString().size))

  def header(cellWidth: Int = 3, numberOfPlayers: Int = 4): List[String] =
    (" " * (cellWidth + 1)) :: (for (n <- List.range(1, numberOfPlayers + 1)) yield "|"
      + ("P" + n).padTo(cellWidth, ' '))


  def  mesh(cellWidth: Int = 3, numberOfPlayers: Int = 4): String =
    (header() :+ "\n" :+ (for (s <- first_column) yield bar(cellWidth)
      + cells(cellWidth, numberOfPlayers, s)).mkString("") :+ bar(cellWidth)).mkString("")

  def put(value: Int, x: Int, y: Int) = copy(matrix.fill(x, y, value))

  override def toString = mesh()


