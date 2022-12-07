package de.htwg.se.kniffel
package controller

import model.{Field, Game, Move}
import util.Command

class SetCommand(move: Move) extends Command[Game, Field] :

  override def doStep(game: Game, field: Field): (Game, Field) =
    val g = game.sum(move.value.toInt, move.y)
    (g, field.putMulti(g.resultNestedList(move.x).map(_.toString), move.value, move.x, move.y))

  override def redoStep(game: Game, field: Field): (Game, Field) =
    val g = game.sum(move.value.toInt, move.y).next().get
    (g, field.putMulti(g.resultNestedList(move.x).map(_.toString), move.value, move.x, move.y))

  override def undoStep(game: Game, field: Field): (Game, Field) =
    val g = game.undoMove(move.value.toInt, move.y)
    (g, field.undoMove(g.resultNestedList(move.x).map(_.toString), move.x, move.y))

  override def noStep(game: Game, field: Field): (Game, Field) =
    (game, field)