package ru.d10xa.passwordgenerator

import org.scalajs.dom
import org.scalajs.dom.Attr
import org.scalajs.dom.Element
import org.scalajs.dom.HTMLButtonElement
import org.scalajs.dom.document

import java.util.Date
import scala.scalajs.js.annotation.JSExportTopLevel

object PasswordGeneratorApp {

  @JSExportTopLevel("render_password_generator")
  def render(rootNode: dom.Element): Unit =
    new LaminarView().renderView(rootNode)

}
