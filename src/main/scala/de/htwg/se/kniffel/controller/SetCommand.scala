package de.htwg.se.kniffel
package controller

import model.game.IGame
import model.Move
import util.Command

class SetCommand(move: Move, controller: Controller) extends Command[IGame]:

  override def doStep(t: IGame): IGame = t.move(move)

  override def redoStep(t: IGame): IGame = ???

  override def undoStep(t: IGame): IGame = ???

  override def noStep(t: IGame): IGame = t