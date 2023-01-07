package de.htwg.se.kniffel
package model.fileIOComponent

import model.fieldComponent.IField
import model.gameComponent.IGame

trait IFileIO {
/*  def loadField: IField
  def loadGame: IGame*/
  def saveField(field: IField): Unit
  def saveGame(game: IGame) : Unit
}
