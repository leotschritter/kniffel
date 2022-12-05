package de.htwg.se.kniffel
package aview

import controller.Controller
import model.Move

import scala.swing.*
import scala.swing.event.*
import util.Event
import util.Observer
import aview.UI

import javax.swing.ImageIcon
import javax.swing.border.Border

class GUI(controller: Controller) extends Frame, UI(controller) :
  controller.add(this)
  title = "Kniffel"

  val first_column_second_part: List[String] =
    List("gesamt", "<html>Bonus bei 63<br>oder mehr", "<html>gesamt<br>oberer Teil", "Dreierpasch", "Viererpasch", "Full-House",
      "Kleine Straße", "Große Straße", "Kniffel", "Chance", "<html>gesamt<br>unterer Teil", "<html>gesamt<br>oberer Teil", "Endsumme")

  val second_column: List[String] =
    List("nur Einser\nzählen", "nur Zweier\nzählen", "nur Dreier\nzählen", "nur Vierer\nzählen", "nur Fünfer\nzählen",
      "nur Sechser\nzählen", "→", "plus 35", "→","alle Augen\nzählen", "alle Augen\nzählen", "25\nPunkte", "30\nPunkte",
      "40\nPunkte", "50\nPunkte", "alle Augen\nzählen", "→", "→", "→")

  val diceLinksField: List[String] = List("src/main/resources/3_mal_1.png", "src/main/resources/3_mal_2.png", "src/main/resources/3_mal_3.png",
    "src/main/resources/3_mal_4.png", "src/main/resources/3_mal_5.png", "src/main/resources/3_mal_6.png")

  val diceLinks: List[String] = List("src/main/resources/1.png", "src/main/resources/2.png", "src/main/resources/3.png",
    "src/main/resources/4.png", "src/main/resources/5.png", "src/main/resources/6.png")

  def update(e: Event): Unit = e match
    case Event.Quit => this.dispose()
    case Event.Move => repaint()

  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }
  contents = new BorderPanel {
    add(new Label("Welcome to Kniffel"), BorderPanel.Position.North)
    add(new LeftCellPanel(), BorderPanel.Position.West)
    add(new RightCellPanel(4), BorderPanel.Position.Center)
  }
  pack()
  centerOnScreen()
  open()

  /*class CellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers):
    List()*/
  /*class BorderCellPanel(numberOfPlayers: Int) extends BorderPanel(1, numberOfPlayers):
    contents += new LeftCellPanel()
    contents += new RightCellPanel(numberOfPlayers)*/

  class LeftCellPanel() extends GridPanel(1, 2):
    contents += new LeftCellPanelFirstColumn()
    contents += new LeftCellPanelSecondColumn()

  class LeftCellPanelFirstColumn() extends GridPanel(19, 1):
    for (i <- diceLinksField) {
      contents += new Label {
        icon = new ImageIcon(i)
      }
    }
    for (i <- first_column_second_part) {
      contents += new Label {
        font = new Font("Arial", 0, 13)
        horizontalAlignment = Alignment.Center
        text = i
        xLayoutAlignment = 5.0
        border = Swing.LineBorder(new Color(0, 0, 0))
      }
    }

  class LeftCellPanelSecondColumn() extends GridPanel(19, 1):
    for (i <- 0 until 19) {
      contents += new TextField(second_column(i))
    }

  class RightCellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers) :
    //List() :: (for(j <- 0 until 19) do (for i <- 0 until numberOfPlayers) (contents+= new CellButton("", i, j)))

    for (i <- 0 until 19) {
      for (j <- 0 until numberOfPlayers) {
        contents += new CellButton(""+ j + ", "+ i, j, i)
      }
    }
    //List(("X", 0, 0), ("O", 0, 1), ("", 1, 0), ("", 1, 1)).foreach(t => contents += new CellButton(t._1, t._2, t._3))

    def button(value: String) = new Button(value)

  class CellButton(value: String, x: Int, y: Int) extends Button(value) :
    listenTo(mouse.clicks)
    reactions += {
      case MouseClicked(src, pt, mod, clicks, props) => writeDown(Move("12", x, y))
    }
