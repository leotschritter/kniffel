package de.htwg.se.kniffel
package controller

import model.game.Game
import model.Field
import model.Move
import util.Command

class SetCommand(move: Move, controller: Controller) extends Command :

  override def doStep(): Unit =
    val result = (controller.game.sum(move.value.toInt, move.x, move.y), controller.field.put(move.value, move.x, move.y))
    controller.game = result._1
    controller.field = result._2

  override def redoStep(): Unit =
    val result = (controller.game.redoMove(move.value.toInt, move.x, move.y), controller.field.put(move.value, move.x, move.y))
    controller.game = result._1
    controller.field = result._2

  override def undoStep(): Unit =
    val result = (controller.game.undoMove(move.value.toInt, move.x, move.y), controller.field.undoMove(move.value, move.x, move.y))
    controller.game = result._1
    controller.field = result._2