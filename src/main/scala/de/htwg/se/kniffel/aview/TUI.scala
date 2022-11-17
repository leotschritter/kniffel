package de.htwg.se.kniffel
package aview

import controller.Controller
import model.{DiceCup, Player, Move}

import scala.io.StdIn.readLine
import util.Observer

class TUI(controller: Controller) extends Observer :
  controller.add(this)

  def run =
    println(controller.field.toString)
    inputLoop()

  override def update = println(controller.field.toString + "\n" + controller.diceCup.toString())

  def inputLoop(): Unit =
    analyseInput(readLine) match
      case None => return
      case Some(move) => controller.doAndPublish(controller.putValToField, move)
    inputLoop()


  def analyseInput(input: String): Option[Move] =
    val list = input.split("\\s").toList
    list.head match
      case "q" => None
      case "po" => controller.doAndPublish(controller.putOut, list.tail.map(_.toInt)); None
      case "pi" => controller.doAndPublish(controller.putIn, list.tail.map(_.toInt)); None
      case "d" => controller.doAndPublish(controller.dice()); None
      case "wd" => {
        val posAndDesc = list.tail.head
        val index: Option[Int] = controller.diceCup.indexOfField.get(posAndDesc)
        if (index.isDefined)
          Some(Move(controller.diceCup.getResult(index.get).toString, list.tail.apply(1).toInt, index.get))
        else
          println("Falsche Eingabe!");
          None
      }
      case _ =>
        println("Falsche Eingabe!"); None