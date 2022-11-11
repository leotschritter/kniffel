package de.htwg.se.kniffel


import aview.TUI
import controller.Controller
import model.{DiceCup, Field, Matrix}




@main def main(): Unit =
  println("Welcome to Kniffel")
  val field = new Field(7)
  val dc = new DiceCup()
  val controller = Controller(field, dc)
  controller.putValToField("73", 6, 7)
  // println(controller.field.matrix.cell(6, 7))
  val tui = TUI(controller)
  tui.run()
//  println(mesh())