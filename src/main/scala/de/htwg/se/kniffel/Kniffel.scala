package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import model.{DiceCup, Field, Matrix}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(7)
  val dc = new DiceCup()
  val controller = Controller(field, dc)
  val tui = TUI(controller)
  tui.run