package de.htwg.se.kniffel.model

trait IField {
  def putMulti(valueList: List[String], putInValue: String, x: Int, y: Int): IField
  def undoMove(valueList: List[String], x: Int, y: Int): IField
  def numberOfPlayers: Int
  def getMatrix: Matrix[String]
}
