package de.htwg.se.kniffel
package aview

import controller.IController
import aview.UI
import de.htwg.se.kniffel.model.dicecupComponent.dicecupBaseImpl.DiceCup
import model.Move

import scala.util.{Failure, Success, Try}
import scala.io.StdIn.readLine
import util.{Event, Observer}
import Config.given
import de.htwg.se.kniffel.model.fileIOComponent.fileIOJsonImpl.FileIO

class TUI(using controller: IController) extends UI(controller) with Observer :
  controller.add(this)
  var continue = true

  override def run(): Unit =
    println(controller.getField.toString)
    inputLoop()

  def update(e: Event): Unit =
    e match {
      case Event.Quit => continue = false
      case _ => println(controller.getField.toString + "\n" + controller.getDicecup.toString + controller.getGame.getPlayerName + " ist an der Reihe.")
    }


  def inputLoop(): Unit =
    analyseInput(readLine) match
      case None => inputLoop()
      case Some(move) => writeDown(move)
    if continue then inputLoop()


  def analyseInput(input: String): Option[Move] =
    val list = input.split("\\s").toList
    list.head match
      case "q" => None
      case "po" => diceCupPutOut(list.tail.map(_.toInt)); None
      case "pi" => diceCupPutIn(list.tail.map(_.toInt)); None
      case "d" => controller.doAndPublish(controller.dice()); None
      case "u" => controller.undo(); None
      case "r" => controller.redo(); None
      case "s" => controller.save; None
      case "l" => controller.load; None
      case "wd" =>
        invalidInput(list) match {
          case Success(f) => val posAndDesc = list.tail.head
            val index: Option[Int] = controller.getDicecup.indexOfField.get(posAndDesc)
            if (index.isDefined && controller.getField.getMatrix.isEmpty(controller.getGame.getPlayerID, index.get))
              Some(Move(controller.getDicecup.getResult(index.get).toString, controller.getGame.getPlayerID, index.get))
            else
              println("Falsche Eingabe!"); None
          case Failure(v) => println("Falsche Eingabe"); None
        }
      case _ =>
        println("Falsche Eingabe!"); None

  def invalidInput(list: List[String]): Try[String] = Try(list.tail.head)
  
  def getController: IController = controller
         