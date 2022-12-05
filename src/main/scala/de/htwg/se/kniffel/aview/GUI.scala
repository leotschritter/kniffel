package de.htwg.se.kniffel
package aview

import controller.Controller
import model.Move
import scala.swing._
import scala.swing.event._
import util.Event
import util.Observer
import aview.UI

class GUI(controller: Controller) extends Frame, UI(controller):
  controller.add(this)
  title = "Kniffel"

  def update(e: Event): Unit = e match
    case Event.Quit => this.dispose()
    case Event.Move => repaint()
  /*title = "TicTacToe"
  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }
  contents = new BorderPanel {
    add(new Label("Welcome to TicTacToe"), BorderPanel.Position.North)
    add(new CellPanel(2, 2), BorderPanel.Position.Center)
  }
  pack()
  centerOnScreen()
  open()

  def update(e: Event): Unit = e match
    case Event.Quit => this.dispose
    case Event.Move => repaint

  class CellPanel(x: Int, y: Int) extends GridPanel(x, y):
    List((0, 0, "X"), (0, 1, "O"), (1, 0, ""), (1, 1, "")).foreach(t => contents += new CellButton(t._1, t._2, t._3))

    def button(stone: String) = new Button(stone)

  class CellButton(x: Int, y: Int, stone: String) extends Button(stone):
    listenTo(mouse.clicks)
    reactions += {
      case MouseClicked(src, pt, mod, clicks, props) => {
        controller.doAndPublish(controller.put, Move(Stone.X, x, y))
      }
    }*/
