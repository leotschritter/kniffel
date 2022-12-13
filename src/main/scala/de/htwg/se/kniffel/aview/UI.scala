package de.htwg.se.kniffel
package aview

import controller.Controller
import util.Observer
import model.Move
import model.dicecup.DiceCup

trait UI(controller: Controller) extends Observer {

  def run(): Unit = {}

  def writeDown(move: Move): Unit = {
    controller.put(move)
    controller.next()
    controller.doAndPublish(controller.nextRound())
  }

  def diceCupPutIn(pi: List[Int]): Unit = controller.doAndPublish(controller.putIn, pi)

  def diceCupPutOut(po: List[Int]): Unit = controller.doAndPublish(controller.putOut, po)
}
