package de.htwg.se.kniffel
package aview

import controller.Controller
import util.Observer
import model.{Field, Move, Player}
import model.dicecup.DiceCup

abstract class UI(controller: Controller) extends Observer {


  def run(): Unit

  def multiFieldInput(player: Player = controller.game.currentPlayer, indexList: List[Int] = List(6, 7, 8, 16, 17, 18)): Unit = {
    for (l <- indexList)
      controller.doAndPublish(
        controller.putValToField,
        Move(controller.game.getCurrentList(indexList.indexOf(l)).toString, player.playerID, l)
      )
  }

  def gameAndFieldInput(): Unit = {
    // controller.doAndPublish(controller.putValToField, Move(move.value, player.playerID, move.y))
    // controller.doAndPublish(controller.sum, move.value.toInt, move.x, move.y)

    controller.doAndPublish(controller.next().get)
  }

/*
  def gameAndFieldInputUndo(move: Move, player: Player = controller.game.currentPlayer, indexList: List[Int] = List(6, 7, 8, 16, 17, 18)): Unit  = {
    controller.doAndPublish(controller.putValToField, Move(move.value, controller.game.getPreviousPlayer.playerID, move.y))
    controller.doAndPublish(controller.undoMove, move.value.toInt, move.x, move.y)
    for (l <- indexList)
      controller.doAndPublish(
        controller.putValToField,
        Move(controller.game.getCurrentList(indexList.indexOf(l)).toString, controller.game.getPreviousPlayer.playerID, l)
      )
    controller.doAndPublish(controller.next().get)
  }
*/

  def diceCupInput(): Unit = controller.doAndPublish(controller.nextRound())

  def diceCupPutIn(pi: List[Int]): Unit = controller.doAndPublish(controller.putIn, pi)

  def diceCupPutOut(po: List[Int]): Unit = controller.doAndPublish(controller.putOut, po)
}
