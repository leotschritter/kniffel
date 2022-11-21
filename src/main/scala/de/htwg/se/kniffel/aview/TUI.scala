package de.htwg.se.kniffel
package aview

import controller.Controller
import model.{Player, Move, Game}

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
      case None =>
      case Some(move) =>
        val currentPlayer: Player = controller.game.currentPlayer
        val currentPlayerIndex: Int = controller.game.playersList.indexOf(currentPlayer)
        val currentSumList: List[Int] =
          controller.game.getCurrentList
        val indexList: List[Int] = List(6, 7, 8, 16, 17, 18)
        controller.doAndPublish(controller.putValToField, Move(move.value, currentPlayerIndex, move.y))
        if (move.y < 6)
          controller.doAndPublish(controller.sum, move.value.toInt + currentSumList.head, currentSumList(3))
        else
          controller.doAndPublish(controller.sum, currentSumList.head, move.value.toInt + currentSumList(3))
        for (l <- indexList)
          controller.doAndPublish(
            controller.putValToField,
            Move(controller.game.getCurrentList(indexList.indexOf(l)).toString, currentPlayerIndex, l)
          )
        controller.doAndPublish(controller.nextRound())
        controller.doAndPublish(controller.next().get)
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
        if (index.isDefined && controller.field.matrix.isEmpty(controller.game.currentPlayer.playerID, index.get))
          Some(Move(controller.diceCup.getResult(index.get).toString, 0, index.get))
        else
          println("Falsche Eingabe!"); None
      }
      case _ =>
        println("Falsche Eingabe!"); None