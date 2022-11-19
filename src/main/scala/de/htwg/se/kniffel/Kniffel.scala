package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import model.{DiceCup, Field, Matrix, Game}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(4)
  val dc = new DiceCup()
  val game = new Game(4)
  val controller = Controller(field, dc, game)
  val tui = TUI(controller)
  tui.run