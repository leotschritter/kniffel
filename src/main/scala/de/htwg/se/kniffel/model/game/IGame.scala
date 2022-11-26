package de.htwg.se.kniffel
package model.game

import model.Move
import model.game.Game

trait IGame {
  def move(move: Move) : IGame

}
