package de.htwg.se.kniffel
package aview

import controller.Controller
import model.{DiceCup, Player, Move}

import scala.io.StdIn.readLine
import util.Observer

class TUI(controller: Controller) extends Observer :
  controller.add(this)

  def run() =
    println(controller.field.toString)
    getInputAndPrintLoop()

  override def update = println(controller.field.toString)

  def getInputAndPrintLoop(): Unit =
    val input = readLine
    input match
      case "q" =>
      case _ =>
        val value = "2"
        val x = 2
        val y = 2
        controller.putValToField(value, x, y)
        getInputAndPrintLoop()

  def analyseInput(input: String): Option[Move] =
    val list = input.split("\\s").toList
    list.head match
      case "q" => None
      case "po" => controller.putOut(list.tail.map(_.toInt)); None
      case "pi" => controller.putIn(list.tail.map(_.toInt)); None
      case "d" => controller.dice(); None //implement dice throw
      case "wd" =>
        Some(Move(list.tail.head, list.tail.apply(1).toInt , list.tail.apply(2).toInt))




