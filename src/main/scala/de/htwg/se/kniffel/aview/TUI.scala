package de.htwg.se.kniffel
package aview

import controller.Controller
import aview.UI
import de.htwg.se.kniffel.model.dicecup.DiceCup
import model.{Field, Game, Move, Player}

import scala.io.StdIn.readLine
import util.Observer

class TUI(controller: Controller) extends UI(controller) :
  controller.add(this)
  /*controller.add(this)*/
  //controller.add(this)
  //def this(controller: Controller) = this(UI(controller))

  override def run(): Unit =
    println(controller.field.toString)
    inputLoop()

  def update = println(controller.field.toString + "\n" + controller.diceCup.toString())


   def inputLoop(): Unit =
    analyseInput(readLine) match
      case None =>
      case Some(move) =>
        gameAndFieldInput(controller.game.currentPlayer, move)
        diceCupInput()
    inputLoop()


  def analyseInput(input: String): Option[Move] =
    val list = input.split("\\s").toList
    list.head match
      case "q" => None
      case "po" => diceCupPutOut(list.tail.map(_.toInt)); None
      case "pi" => diceCupPutIn(list.tail.map(_.toInt)); None
      case "d" => controller.doAndPublish(controller.dice()); None
      case "wd" => {
        val posAndDesc = list.tail.head
        val index: Option[Int] = controller.diceCup.indexOfField.get(posAndDesc)
        if (index.isDefined && controller.field.matrix.isEmpty(controller.game.currentPlayer.playerID, index.get))
          Some(Move(controller.diceCup.getResult(index.get).toString, 0, index.get))
        else
          println("Falsche Eingabe!"); None
      }
      case _ =>
        println("Falsche Eingabe!"); None