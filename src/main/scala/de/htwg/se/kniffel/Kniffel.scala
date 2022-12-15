package de.htwg.se.kniffel


import aview.{GUI, TUI}
import controller.controllerBaseImpl.Controller
import model.dicecupComponent.dicecupBaseImpl.DiceCup
import model.fieldComponent.fieldBaseImpl.Field
import model.gameComponent.gameBaseImpl.Game


@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(2)
  val dc = new DiceCup()
  val game = new Game(2)
  val controller = Controller(field, dc, game)
  val tui = TUI(controller)
  val gui = new GUI(controller)
  tui.run()