package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import model.Field
import model.Matrix




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(2)
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run()
//  println(mesh())