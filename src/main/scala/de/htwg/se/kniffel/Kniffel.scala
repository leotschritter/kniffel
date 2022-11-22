package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import de.htwg.se.kniffel.model.dicecup.DiceCup
import model.{Field, Game, Matrix}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(1)
  val dc = new DiceCup()
  val game = new Game(1)
  val controller = Controller(field, dc, game)
  val tui = TUI(controller)
  tui.run