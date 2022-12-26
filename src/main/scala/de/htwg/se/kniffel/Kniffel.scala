package de.htwg.se.kniffel


import aview.{GUI, TUI}
import controller.controllerBaseImpl.Controller
import model.dicecupComponent.dicecupBaseImpl.DiceCup
import model.fieldComponent.fieldBaseImpl.Field
import model.gameComponent.gameBaseImpl.Game
import Config.{given}


@main def main(): Unit =
  println("Welcome to Kniffel")
//  val field = new Field
//  val dc = new DiceCup
//  val game = new Game
//  val controller = new Controller
  val tui = TUI()
  val gui = new GUI()
  tui.run()

