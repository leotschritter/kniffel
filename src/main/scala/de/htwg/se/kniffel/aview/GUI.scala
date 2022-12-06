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
import scala.collection.immutable.HashMap

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

  val intToImg: Map[Int, ImageIcon] = Map(1 -> new ImageIcon(diceLinks.head), 2 -> new ImageIcon(diceLinks(1)), 3 -> new ImageIcon(diceLinks(2)),
    4 -> new ImageIcon(diceLinks(3)), 5 -> new ImageIcon(diceLinks(4)), 6 -> new ImageIcon(diceLinks.last))

  val imgToInt: Map[String, Int] = Map(diceLinks.head -> 1, diceLinks(1) -> 2, diceLinks(2) -> 3,
    diceLinks(3) -> 4, diceLinks(4) -> 5, diceLinks.last -> 6)

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
  def updateDiceCup(leftListView: ListView[ImageIcon], rightListView: ListView[ImageIcon], rem: Label, btn_dice: Button = new Button()): Unit = {
    if (controller.diceCup.remDices == -1)
      btn_dice.enabled = false
    val left: List[ImageIcon] = for (s <- controller.diceCup.inCup) yield intToImg(s)
    val right: List[ImageIcon] = for (s <- controller.diceCup.locked) yield intToImg(s)
    rem.text = "<html>Verbleibende<br>Würfe: " + (controller.diceCup.remDices + 1)
    leftListView.listData = left
    rightListView.listData = right
  }

  class RightPanel() extends BorderPanel :
    val leftListView: ListView[ImageIcon] = new ListView[ImageIcon]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    val rightListView: ListView[ImageIcon] = new ListView[ImageIcon]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    val rem: Label = new Label("<html>Verbleibende<br>Würfe: 3")
    add(new TopInnerPanel(rem), BorderPanel.Position.North)
    add(leftListView, BorderPanel.Position.West)
    add(new RightInnerPanel(leftListView, rightListView, rem), BorderPanel.Position.Center)
    add(rightListView, BorderPanel.Position.East)

  class TopInnerPanel(rem: Label) extends GridPanel(1, 3) :
    contents += new Label("Im Becher")
    contents += rem
    contents += new Label("Rausgenommen")

  class RightInnerPanel(leftListView: ListView[ImageIcon], rightListView: ListView[ImageIcon], rem: Label) extends BoxPanel(Orientation.Vertical) {
    val buttonDimension: Dimension = new Dimension(90, 50)
    contents += new Button {
      icon = new ImageIcon("src/main/resources/right_arrow.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          val intList: List[Int] = for (s <- leftListView.selection.items.toList) yield imgToInt(s.toString)
          diceCupPutOut(intList)
          updateDiceCup(leftListView, rightListView, rem)
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
          val intList: List[Int] = for (s <- rightListView.selection.items.toList) yield imgToInt(s.toString)
          diceCupPutIn(intList)
          updateDiceCup(leftListView, rightListView, rem)
      }
    }
    val btn_dice: Button = new Button {
      icon = new ImageIcon("src/main/resources/flying_dices_small.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          controller.doAndPublish(controller.dice())
          updateDiceCup(leftListView, rightListView, rem, btn_dice)
      }
    }
    contents += btn_dice
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
          preferredSize = new Dimension(60, 20)
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

    def errorMessage(): Unit = Dialog.showMessage(contents.head, "Feld ist schon belegt!", title = "Falsche Eingabe", messageType = Dialog.Message.Error)

    def getValue: String = controller.diceCup.getResult(x).toString
