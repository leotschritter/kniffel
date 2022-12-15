package de.htwg.se.kniffel
package controller

import util.Observable
import model.dicecup.DiceCup
import model.{IField, IGame, Move}

trait IController extends Observable {
  def undo: Unit
  def redo: Unit
  def put(move: Move): Unit
  def quit(): Unit
  def next(): Unit
  def doAndPublish(doThis: List[Int] => DiceCup, list: List[Int]): Unit
  def putOut(list: List[Int]): DiceCup
  def putIn(list: List[Int]): DiceCup
  def doAndPublish(doThis: => DiceCup): Unit
  def dice(): DiceCup
  def nextRound(): DiceCup
  def toString: String
  def getField: IField
  def getDicecup: DiceCup
  def getGame: IGame
  
}
