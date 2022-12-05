package de.htwg.se.kniffel
package aview

import controller.Controller
import model.Move
import scala.swing._
import scala.swing.event._
import util.Event
import util.Observer
import aview.UI

class GUI(controller: Controller) extends Frame, UI(controller) :
  controller.add(this)
  title = "Kniffel"

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
    add(new RightCellPanel(8), BorderPanel.Position.Center)
  }
  pack()
  centerOnScreen()
  open()

  /*class CellPanel(numberOfPlayers: Int) extends GridPanel(19, numberOfPlayers):
    List()*/
  /*class BorderCellPanel(numberOfPlayers: Int) extends BorderPanel(1, numberOfPlayers):
    contents += new LeftCellPanel()
    contents += new RightCellPanel(numberOfPlayers)*/

  class LeftCellPanel() extends GridPanel(19, 1):
    for(i <- 0 until 19) {
      contents += new TextField(controller.field.first_column(i))
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
