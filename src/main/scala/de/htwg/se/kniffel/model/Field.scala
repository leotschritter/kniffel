package de.htwg.se.kniffel.model


case class Field(matrix: Matrix[String]):
  def this(numberOfPlayers: Int) = this(new Matrix[String](numberOfPlayers))

  val defaultPlayers: Int = matrix.rows.flatten.length / 19

  val first_column: List[String] =
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

  def put(value: String, x: Int, y: Int): Field = copy(matrix.fill(x, y, value))

  def putMulti(values: List[String], x: Int, y_coordinates: List[Int], currentField: Field = Field(matrix), n: Int = 0): Field =
    if (n != values.length)
      putMulti(values, x, y_coordinates, put(values(n), x, y_coordinates(n)), n + 1)
    else
      currentField

  override def toString = mesh()


