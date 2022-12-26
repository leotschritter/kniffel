package de.htwg.se.kniffel

import controller.IController
import controller.controllerBaseImpl.Controller
import model.gameComponent.IGame
import model.gameComponent.gameBaseImpl.Game
import model.dicecupComponent.IDiceCup
import model.dicecupComponent.dicecupBaseImpl.DiceCup
import model.fieldComponent.{IField, IMatrix}
import model.fieldComponent.fieldBaseImpl.{Field, Matrix}

object Config {

  val numberOfPLayers = 2
  val field = new Field(numberOfPLayers)
  given IField = field
  val dicecup = new DiceCup()
  given IDiceCup = dicecup
  val game = new Game(numberOfPLayers)
  given IGame = game

  val controller = new Controller()
  given IController = controller
}
