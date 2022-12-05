package de.htwg.se.kniffel


import aview.{TUI, GUI}
import controller.Controller
import model.dicecup.DiceCup
import model.{Field, Game}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(4)
  val dc = new DiceCup()
  val game = new Game(4)
  val controller = Controller(field, dc, game)
  val tui = TUI(controller)
  val gui = new GUI(controller)
  tui.run()