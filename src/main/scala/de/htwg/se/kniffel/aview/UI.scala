package de.htwg.se.kniffel
package aview

import controller.Controller
import util.Observer
import model.{Field, Move, Player}
import model.dicecup.DiceCup

trait UI(controller: Controller) extends Observer {

  def run(): Unit = ???

  def multiFieldInput(player: Player = controller.game.currentPlayer, indexList: List[Int] = List(6, 7, 8, 16, 17, 18)): Unit = {
    for (i <- controller.game.playersList) {
      for (l <- indexList)
        controller.doAndPublish(
          controller.putValToField,
          Move(controller.game.resultNestedList(i.playerID)(indexList.indexOf(l)).toString, i.playerID, l)
        )
    }
  }

  def gameAndFieldInput(): Unit = {
    // controller.doAndPublish(controller.putValToField, Move(move.value, player.playerID, move.y))
    // controller.doAndPublish(controller.sum, move.value.toInt, move.x, move.y)

    controller.doAndPublish(controller.next().get)
  }

  def diceCupInput(): Unit = controller.doAndPublish(controller.nextRound())

  def diceCupPutIn(pi: List[Int]): Unit = controller.doAndPublish(controller.putIn, pi)

  def diceCupPutOut(po: List[Int]): Unit = controller.doAndPublish(controller.putOut, po)
}

/*abstract class UI(controller: Controller) extends IUI(controller) {


  def run(): Unit

  def multiFieldInput(player: Player = controller.game.currentPlayer, indexList: List[Int] = List(6, 7, 8, 16, 17, 18)): Unit = {
    for (i <- controller.game.playersList) {
      for (l <- indexList)
        controller.doAndPublish(
          controller.putValToField,
          Move(controller.game.resultNestedList(i.playerID)(indexList.indexOf(l)).toString, i.playerID, l)
        )
    }
  }

  def gameAndFieldInput(): Unit = {
    // controller.doAndPublish(controller.putValToField, Move(move.value, player.playerID, move.y))
    // controller.doAndPublish(controller.sum, move.value.toInt, move.x, move.y)

    controller.doAndPublish(controller.next().get)
  }

  def diceCupInput(): Unit = controller.doAndPublish(controller.nextRound())

  def diceCupPutIn(pi: List[Int]): Unit = controller.doAndPublish(controller.putIn, pi)

  def diceCupPutOut(po: List[Int]): Unit = controller.doAndPublish(controller.putOut, po)
}*/
