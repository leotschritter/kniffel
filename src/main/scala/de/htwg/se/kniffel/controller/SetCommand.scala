package de.htwg.se.kniffel
package controller

import model.{Field, Game, Move}
import util.Command

class SetCommand(move: Move) extends Command[Game, Field] :

  override def doStep(game: Game, field: Field): (Game, Field) =
    (game.sum(move.value.toInt, move.x, move.y), field.put(move.value, move.x, move.y))

  override def redoStep(game: Game, field: Field): (Game, Field) =
    (game.sum(move.value.toInt, move.x, move.y).next().get, field.put(move.value, move.x, move.y))

  override def undoStep(game: Game, field: Field): (Game, Field) =
    (game.undoMove(move.value.toInt, move.x, move.y), field.undoMove(move.x).get)

  override def noStep(game: Game, field: Field): (Game, Field) =
    (game, field)