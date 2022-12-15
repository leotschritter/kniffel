package de.htwg.se.kniffel.model

trait IField {
  def putMulti(valueList: List[String], putInValue: String, x: Int, y: Int, indexList:List[Int] = List(6, 7, 8, 16, 17, 18)): IField
  def undoMove(valueList: List[String], x: Int, y: Int): IField
  def numberOfPlayers: Int
  def getMatrix: Matrix[String]
}
