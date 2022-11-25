package de.htwg.se.kniffel
package aview

import controller.Controller
import util.Observer
import model.{Field, Game, Move, Player}
import model.dicecup.DiceCup

abstract class UI(controller: Controller) extends Observer {


  def run(): Unit

  def gameAndFieldInput(player: Player, move: Move, indexList: List[Int] = List(6, 7, 8, 16, 17, 18)): Unit = {
    val currentPlayerIndex: Int = controller.game.playersList.indexOf(player)
    val currentSumList: List[Int] = controller.game.getCurrentList
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
    controller.doAndPublish(controller.next().get)
  }
  def diceCupInput(): Unit = controller.doAndPublish(controller.nextRound())

  def diceCupPutIn(pi: List[Int]): Unit = controller.doAndPublish(controller.putIn, pi)

  def diceCupPutOut(po: List[Int]): Unit = controller.doAndPublish(controller.putOut, po)
}
