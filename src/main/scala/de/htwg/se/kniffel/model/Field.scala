package de.htwg.se.kniffel.model

import de.htwg.se.kniffel.model.Matrix

case class Field(matrix: Matrix[Int]):
  def this(numberOfPlayers: Int) = this(new Matrix[Int](numberOfPlayers))



