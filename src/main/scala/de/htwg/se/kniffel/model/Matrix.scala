package de.htwg.se.kniffel.model

case class Matrix[T](columns: Vector[Vector[Int]]):
  def this(cols:Int, rows:Int = 19) = this(Vector.tabulate(cols, rows) { (rows, cols) => 0})
  val size:Int = columns.size
  def cell(col:Int, row:Int):Int = columns(col)(row)
  def fill(col:Int, row:Int, value:Int):Matrix[T] = copy(columns.updated(col, columns(col).updated(row, value)))
