package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import model.{Field, Game}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(2)
  val dc = new DiceCup()
  val game = new Game(2)
  val controller = Controller(field, dc, game)
  val tui = TUI(controller)
  tui.run()