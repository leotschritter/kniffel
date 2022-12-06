package de.htwg.se.kniffel
package aview

import controller.Controller
import model.Move

import scala.swing.{Label, *}
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
    List("<html>nur Einser<br>zählen", "<html>nur Zweier<br>zählen", "<html>nur Dreier<br>zählen", "<html>nur Vierer<br>zählen",
      "<html>nur Fünfer<br>zählen", "<html>nur Sechser<br>zählen", "→", "plus 35", "→", "<html>alle Augen<br>zählen",
      "<html>alle Augen<br>zählen", "<html>25<br>Punkte", "<html>30<br>Punkte",
      "<html>40<br>Punkte", "<html>50<br>Punkte", "<html>alle Augen<br>zählen", "→", "→", "→")

  val diceLinksField: List[String] = List("src/main/resources/3_mal_1.png", "src/main/resources/3_mal_2.png", "src/main/resources/3_mal_3.png",
    "src/main/resources/3_mal_4.png", "src/main/resources/3_mal_5.png", "src/main/resources/3_mal_6.png")

  val diceLinks: List[String] = List("src/main/resources/1.png", "src/main/resources/2.png", "src/main/resources/3.png",
    "src/main/resources/4.png", "src/main/resources/5.png", "src/main/resources/6.png")

  val intToImg: Map[Int, ImageIcon] = Map(1 -> new ImageIcon(diceLinks.head), 2 -> new ImageIcon(diceLinks(1)), 3 -> new ImageIcon(diceLinks(2)),
    4 -> new ImageIcon(diceLinks(3)), 5 -> new ImageIcon(diceLinks(4)), 6 -> new ImageIcon(diceLinks.last))

  val imgToInt: Map[String, Int] = Map(diceLinks.head -> 1, diceLinks(1) -> 2, diceLinks(2) -> 3,
    diceLinks(3) -> 4, diceLinks(4) -> 5, diceLinks.last -> 6)

  val pnl_center = new RightCellPanel(controller.field.defaultPlayers)

  def update(e: Event): Unit = e match
    case Event.Quit => this.dispose()
    case Event.Move => pnl_center.repaint(); repaint()

  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }
  contents = new BorderPanel {
    add(new Label("Welcome to Kniffel"), BorderPanel.Position.North)
    val pnl_left = new LeftCellPanel()
    // val pnl_center = new RightCellPanel(controller.field.defaultPlayers)
    add(pnl_left, BorderPanel.Position.West)
    add(pnl_center, BorderPanel.Position.Center)
    add(new RightPanel(), BorderPanel.Position.East)
  }
  pack()
  centerOnScreen()
  open()

  def field(numberOfPlayers: Int = controller.game.playersList.length): List[Label] =
    (for {
      i <- 0 until 19
      j <- 0 until numberOfPlayers
    } yield new Label {
      text = controller.field.matrix.cell(j, i)
      preferredSize = new Dimension(60, 20)
      border = Swing.LineBorder(new Color(0, 0, 0))
    }).toList

  /*for (i <- 0 until 19) {
    for (j <- 0 until numberOfPlayers) {
      // contents += new CellButton("" + j + ", " + i, j, i)
      contents += new TextArea {
        text = controller.field.matrix.cell(j, i)
        preferredSize = new Dimension(60, 20)
        border = Swing.LineBorder(new Color(0, 0, 0))
      }
    }
  }*/
  //lass LeftCellPanel() extends GridPanel(1, 2) :
  /*class CellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers):
    List()*/
  /*class BorderCellPanel(numberOfPlayers: Int) extends BorderPanel(1, numberOfPlayers):
    contents += new LeftCellPanel()
    contents += new RightCellPanel(numberOfPlayers)*/
  def updateDiceCup(lstViewLeft: ListView[ImageIcon], lstViewRight: ListView[ImageIcon], lbl_rem: Label, btn_dice: Button = new Button()): Unit = {
    if (controller.diceCup.remDices == -1)
      btn_dice.enabled = false
    val lstLeft: List[ImageIcon] = for (s <- controller.diceCup.inCup) yield intToImg(s)
    val lstRight: List[ImageIcon] = for (s <- controller.diceCup.locked) yield intToImg(s)
    lbl_rem.text = "<html>Verbleibende<br>Würfe: " + (controller.diceCup.remDices + 1)
    lstViewLeft.listData = lstLeft
    lstViewRight.listData = lstRight
  }

  class RightPanel() extends BorderPanel :
    val lstViewLeft: ListView[ImageIcon] = new ListView[ImageIcon]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    val lstViewRight: ListView[ImageIcon] = new ListView[ImageIcon]() {
      selection.intervalMode = IntervalMode.MultiInterval
      preferredSize = new Dimension(100, 500)
    }
    val lbl_rem: Label = new Label("<html>Verbleibende<br>Würfe: 3")
    add(new TopInnerPanel(lbl_rem), BorderPanel.Position.North)
    add(lstViewLeft, BorderPanel.Position.West)
    add(new RightInnerPanel(lstViewLeft, lstViewRight, lbl_rem), BorderPanel.Position.Center)
    add(lstViewRight, BorderPanel.Position.East)

  class TopInnerPanel(lbl_rem: Label) extends GridPanel(1, 3) :
    contents += new Label("Im Becher")
    contents += lbl_rem
    contents += new Label("Rausgenommen")

  class RightInnerPanel(lstViewLeft: ListView[ImageIcon], lstViewRight: ListView[ImageIcon], lbl_rem: Label) extends BoxPanel(Orientation.Vertical) {
    val buttonDimension: Dimension = new Dimension(90, 50)
    contents += new Button {
      icon = new ImageIcon("src/main/resources/right_arrow.png") {
        preferredSize = buttonDimension
      }
      preferredSize = buttonDimension
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(src, pt, mod, clicks, props) =>
          val intList: List[Int] = for (s <- lstViewLeft.selection.items.toList) yield imgToInt(s.toString)
          diceCupPutOut(intList)
          updateDiceCup(lstViewLeft, lstViewRight, lbl_rem)
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
          val intList: List[Int] = for (s <- lstViewRight.selection.items.toList) yield imgToInt(s.toString)
          diceCupPutIn(intList)
          updateDiceCup(lstViewLeft, lstViewRight, lbl_rem)
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
          updateDiceCup(lstViewLeft, lstViewRight, lbl_rem, btn_dice)
      }
    }
    contents += btn_dice
  }

  //contents += new Bu
  class LeftCellPanel() extends GridPanel(1, 2) :
    contents += new LeftCellPanelFirstColumn(field())
    contents += new LeftCellPanelSecondColumn()

  class LeftCellPanelFirstColumn(var lst_label: List[Label]) extends GridPanel(19, 1) :
    for (i <- 0 until 6) {
      contents += new CellButton("", i, 0, lst_label) {
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
        contents += new CellButton("", i + 6, 0, lst_label) {
          font = new Font("Arial", 0, 13)
          horizontalAlignment = Alignment.Center
          text = first_column_second_part(i)
          xLayoutAlignment = 5.0
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
    }

  class LeftCellPanelSecondColumn() extends GridPanel(19, 1) :
    for (i <- 0 until 19) {
      if(i == 6 || i == 8 || 15 < i && i < 19)
        contents += new Label {
          icon = new ImageIcon("src/main/resources/right_arrow.png")
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
      else
        contents += new Label {
          text = second_column(i)
          font = new Font("Arial", 0, 13)
          horizontalAlignment = Alignment.Center
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
    }

  class RightCellPanel(numberOfPlayers: Int = controller.game.playersList.length) extends GridPanel(19, numberOfPlayers) :
    //List() :: (for(j <- 0 until 19) do (for i <- 0 until numberOfPlayers) (contents+= new CellButton("", i, j)))
    for (x <- field(numberOfPlayers)) yield contents += x
  /*for (i <- 0 until 19) {
      for (j <- 0 until numberOfPlayers) {
        // contents += new CellButton("" + j + ", " + i, j, i)
        contents += new TextArea {
          text = controller.field.matrix.cell(j, i)
          preferredSize = new Dimension(60, 20)
          border = Swing.LineBorder(new Color(0, 0, 0))
        }
      }
    }*/


  // def button(value: String) = new Button(value)


  class CellButton(value: String, x: Int, y: Int, var lst_label: List[Label]) extends Button(value) :

    listenTo(mouse.clicks)

    reactions += {
      case MouseClicked(src, pt, mod, clicks, props)
      =>
        if isEmpty(getYIndex, x)
        then
          writeDown(Move(getValue, getYIndex, x))
          update(Event.Move)
          lst_label = field()
        else errorMessage(); None
    }

    def getYIndex: Int = controller.game.currentPlayer.playerID

    def isEmpty(x: Int, y: Int): Boolean = controller.field.matrix.isEmpty(x, y)

    def errorMessage(): Unit = Dialog.showMessage(contents.head, "Feld ist schon belegt!", title = "Falsche Eingabe", messageType = Dialog.Message.Error)

    def getValue: String = controller.diceCup.getResult(x).toString
