package de.htwg.se.kniffel
package aview

import controller.Controller
import model.Move

import scala.swing.*
import scala.swing.event.*
import scala.swing.ListView.*
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
      "nur Sechser\nzählen", "→", "plus 35", "→", "alle Augen\nzählen", "alle Augen\nzählen", "25\nPunkte", "30\nPunkte",
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
    add(new RightCellPanel(controller.field.defaultPlayers), BorderPanel.Position.Center)
    add(new RightPanel(), BorderPanel.Position.East)
  }
  pack()
  centerOnScreen()
  open()

  //lass LeftCellPanel() extends GridPanel(1, 2) :
  /*class CellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers):
    List()*/
  /*class BorderCellPanel(numberOfPlayers: Int) extends BorderPanel(1, numberOfPlayers):
    contents += new LeftCellPanel()
    contents += new RightCellPanel(numberOfPlayers)*/
  def updateDiceCup(leftListView: ListView[Int], rightListView: ListView[Int]): Unit = {
    leftListView.listData = controller.diceCup.inCup
    rightListView.listData = controller.diceCup.locked
  }

  class RightPanel() extends BorderPanel:
    val leftListView: ListView[Int] = new ListView[Int]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    val rightListView: ListView[Int] = new ListView[Int]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    add(leftListView, BorderPanel.Position.West)
    add(new RightInnerPanel(leftListView, rightListView), BorderPanel.Position.Center)
    add(rightListView, BorderPanel.Position.East)

  class RightInnerPanel(leftListView: ListView[Int], rightListView: ListView[Int]) extends BoxPanel(Orientation.Vertical) {
    val buttonDimension: Dimension = new Dimension(90, 50)
    contents += new Button {
      icon = new ImageIcon("src/main/resources/right_arrow.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          diceCupPutOut(leftListView.selection.items.toList)
          updateDiceCup(leftListView, rightListView)
      }
    }
    contents += new Button {
      icon = new ImageIcon("src/main/resources/left_arrow.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          diceCupPutIn(rightListView.selection.items.toList)
          updateDiceCup(leftListView, rightListView)
      }
    }
    contents += new Button {
      icon = new ImageIcon("src/main/resources/flying_dices_small.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          controller.doAndPublish(controller.dice())
          updateDiceCup(leftListView, rightListView)
      }
    }
  }

  //contents += new Bu
  class LeftCellPanel() extends GridPanel(1, 2) :
    contents += new LeftCellPanelFirstColumn()
    contents += new LeftCellPanelSecondColumn()

  class LeftCellPanelFirstColumn() extends GridPanel(19, 1) :
    for (i <- 0 until 6) {
      contents += new CellButton("", i, 0) {
        icon = new ImageIcon(diceLinksField(i))
        //size = new Dimension(12, 12)
      }
    }
    for (i <- 0 until 13) {
      if (i < 3 || i > 9)
        contents += new Label {
          font = new Font("Arial", 0, 13)
          horizontalAlignment = Alignment.Center
          text = first_column_second_part(i)
          xLayoutAlignment = 5.0
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
      else
        contents += new CellButton("", i + 6, 0) {
          font = new Font("Arial", 0, 13)
          horizontalAlignment = Alignment.Center
          text = first_column_second_part(i)
          xLayoutAlignment = 5.0
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
    }

  class LeftCellPanelSecondColumn() extends GridPanel(19, 1) :
    for (i <- 0 until 19) {
      contents += new TextField(second_column(i))
    }

  class RightCellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers) :
    //List() :: (for(j <- 0 until 19) do (for i <- 0 until numberOfPlayers) (contents+= new CellButton("", i, j)))
    for (i <- 0 until 19) {
      for (j <- 0 until numberOfPlayers) {
        contents += new CellButton("" + j + ", " + i, j, i)
        contents += new TextArea {
          text = controller.field.matrix.cell(j, i)
          preferredSize = new Dimension(60,20)
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
      }
    }


  // def button(value: String) = new Button(value)


  class CellButton(value: String, x: Int, y: Int) extends Button(value) :

    listenTo(mouse.clicks)

    reactions += {
      case MouseClicked(src, pt, mod, clicks, props)
      => if isEmpty(getYIndex, x) then writeDown(Move(getValue, getYIndex, x)) else errorMessage(); None
    }
    def getYIndex: Int = controller.game.currentPlayer.playerID

    def isEmpty(x: Int, y: Int): Boolean = controller.field.matrix.isEmpty(x, y)

    def errorMessage(): Unit = Dialog.showMessage(contents.head, "Feld ist schon belegt!", title="Falsche Eingabe", messageType = Dialog.Message.Error)

    def getValue: String = controller.diceCup.getResult(x).toString
