/*
package de.htwg.se.kniffel
package model.fileIOComponent

import controller.IController
import controller.controllerBaseImpl.Controller
import model.dicecupComponent.IDiceCup
import model.dicecupComponent.dicecupBaseImpl.DiceCup
import model.fieldComponent.IField
import model.fieldComponent.fieldBaseImpl.{Field, Matrix}
import model.fileIOComponent.fileIOXmlImpl.FileIO
import model.gameComponent.IGame
import model.gameComponent.gameBaseImpl.{Game, Player}

object LoadConfig {
/*(matrix: Vector[Vector[String]], resultNestedList: List[List[Int]], playerslist: List[(Int, String)],
                 currentPlayer: (Int, String), remainingMoves: Int, locked: List[Int], inCup: List[Int], remDices: Int)*/
  val field = fileIO.loadField
  given IField = field

  val dicecup = DiceCup(locked, inCup, remDices)
  given IDiceCup = dicecup

  val players = for x <- playerslist yield Player(x._1, x._2)
  val game = Game(players, Player(currentPlayer._1, currentPlayer._2), remainingMoves, resultNestedList)
  given IGame = game

  val controller = new Controller()
  given IController = controller


  val fileIO = new FileIO()
  given IFileIO = fileIO
}
*/
