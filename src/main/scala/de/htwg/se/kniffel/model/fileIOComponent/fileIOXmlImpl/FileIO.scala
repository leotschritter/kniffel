package de.htwg.se.kniffel
package model.fileIOComponent.fileIOXmlImpl

import de.htwg.se.kniffel.model.dicecupComponent.IDiceCup
import de.htwg.se.kniffel.model.fieldComponent.{IField, IMatrix}
import de.htwg.se.kniffel.model.gameComponent.IGame
import model.fileIOComponent.IFileIO

import scala.xml.{Elem, NodeSeq, PrettyPrinter}

class FileIO extends IFileIO {

  override def saveDiceCup(diceCup: IDiceCup): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("dicecup.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(diceCupToXml(diceCup))
    pw.write(xml)
    pw.close()
  }


  override def saveGame(game: IGame): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameToXml(game))
    pw.write(xml)
    pw.close()
  }

  override def saveField(field: IField, matrix: IMatrix): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(fieldToXml(field, matrix))
    pw.write(xml)
    pw.close()
  }


  /*override def loadDiceCup: IDiceCup = ???

  override def loadGame: IGame = ???

  override def loadField: IField = ???*/

  /*  def fieldToXml(field:IField, matrix: IMatrix): Elem = {
      <field>

      </field>
    }*/

  def fieldToXml(field: IField, matrix: IMatrix): Elem = {
    <field number={field.numberOfPlayers.toString}>
      {for {
      col <- 0 until field.numberOfPlayers
      row <- 0 until 19
    } yield {
      <cell row={row.toString} col={col.toString}>
        {matrix.cell(row, col)}
      </cell>
    }}
    </field>
  }

  def diceCupToXml(diceCup: IDiceCup): Elem = {
    <dicecup remainingDices={diceCup.getRemainingDices.toString}>
      <locked quantity={diceCup.getLocked.length.toString}>
        {for {
        index <- diceCup.getLocked.indices
      } yield {
        <dice>
          {diceCup.getLocked(index)}
        </dice>
      }}
      </locked>
      <incup quantity={diceCup.getInCup.length.toString}>
        {for {
        index <- diceCup.getInCup.indices
      } yield {
        <dice>
          {diceCup.getInCup(index)}
        </dice>
      }}
      </incup>
    </dicecup>
  }

  def gameToXml(game: IGame): Elem = {
    <game remainingMoves={game.getRemainingMoves.toString} currentPlayerID={game.getPlayerID.toString} currentPlayerName={game.getPlayerName}>
      <scores>
        {for {
        col <- game.getPlayerTuples.indices
      } yield {
        <player playerid={game.getPlayerTuples(col)._1.toString} playername={game.getPlayerTuples(col)._2}>
          <total>
            {game.getResultNestedList(col).head}
          </total>
          <bonus>
            {game.getResultNestedList(col)(1)}
          </bonus>
          <total_of_upper_section>
            {game.getResultNestedList(col)(2)}
          </total_of_upper_section>
          <total_of_lower_section>
            {game.getResultNestedList(col)(3)}
          </total_of_lower_section>
          <grand_total>
            {game.getResultNestedList(col).last}
          </grand_total>
        </player>
      }}
      </scores>
    </game>
  }
}
