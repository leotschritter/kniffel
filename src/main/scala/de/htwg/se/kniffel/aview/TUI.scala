package de.htwg.se.kniffel
package aview

import controller.Controller
import scala.io.StdIn.readLine
import util.Observer

class TUI(controller: Controller) extends Observer:
  controller.add(this)
  def run() =
    println(controller.field.toString)
    getInputAndPrintLoop()

  override def update = ???

  def getInputAndPrintLoop(): Unit =
    val input = readLine
    input match
      case "q" =>
      case _ => {
        val value = 2
        val x = 2
        val y = 2
        controller.put(value, x, y)
        println(controller.toString)
        getInputAndPrintLoop()
      }
