package de.htwg.se.kniffel
package model.fileIOComponent.fileIOJsonImpl

import model.fieldComponent.fieldBaseImpl.{Field, Matrix}
import model.gameComponent.gameBaseImpl.{Game, Player}

import scala.io.Source
import model.fileIOComponent.IFileIO
import model.dicecupComponent.IDiceCup
import model.fieldComponent.IField
import model.fieldComponent.IMatrix
import model.gameComponent.IGame
import play.api.libs.json.*

import javax.management.ValueExp


class FileIO extends IFileIO {

  override def saveGame(game: IGame): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close()
  }

  override def saveField(field: IField, matrix: IMatrix): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.json"))
    pw.write(Json.prettyPrint(fieldToJson(field, matrix)))
    pw.close()
  }

  override def loadGame: IGame = {
    val source: String = Source.fromFile("game.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val currentPlayerId: Int = (json \ "game" \ "currentPlayerID").get.toString.toInt
    val currentPlayerName: String = (json \ "game" \ "currentPlayerName").get.toString
    val remainingMoves: Int = (json \ "game" \ "remainingMoves").toString.toInt
    val nestedList: List[List[Int]] = getNestedListGame((json \ "game" \ "nestedList").toString)
    val ids = (json \\ "id").map(x => x.as[Int])
    val names = (json \\ "name").map(x => x.as[String])
    val playersList: List[Player] = (for (x <- ids.indices) yield Player(ids(x), names(x))).toList
    val game: IGame = Game(playersList, Player(currentPlayerId, currentPlayerName), remainingMoves, nestedList)
    game
  }

  override def loadField: IField = {
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val nestedVector: Vector[Vector[String]] = getMatrix((json \ "field" \ "matrix").get.toString)
    val matrix: Matrix[String] = Matrix(nestedVector)
    val field: IField = Field(matrix)
    field
  }

  def gameToJson(game: IGame): JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "nestedList" -> game.getNestedList.map(_.mkString(",")).mkString(";"),
        "remainingMoves" -> JsNumber(game.getRemainingMoves),
        "currentPlayerID" -> JsNumber(game.getPlayerID),
        "currentPlayerName" -> game.getPlayerName,
        "players" -> Json.toJson(
          Seq(for {
            x <- game.getPlayerTuples
          } yield {
            Json.obj(
              "id" -> JsNumber(x._1),
              "name" -> x._2)
          })
        )
      )
    )
  }

  def fieldToJson(field: IField, matrix: IMatrix): JsObject = {
    Json.obj(
      "field" -> Json.obj(
        "matrix" -> (for (i <- 0 until field.numberOfPlayers; j <- 0 until 19) yield matrix.cell(i, j)).map(_.mkString(",")).mkString(";")
      )
    )
  }

  def getMatrix(values: String): Vector[Vector[String]] = {
    val valueVector: Vector[String] = values.split(";").toVector
    (for (x <- valueVector.indices) yield valueVector(x).split(",").map(identity).toVector).toVector
  }

  def getNestedListGame(values: String): List[List[Int]] = {
    val valueList: List[String] = values.split(";").toList
    (for (x <- valueList.indices) yield valueList(x).split(",").map(_.toInt).toList).toList
  }
}