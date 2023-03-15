package ru.d10xa.passwordgenerator.components
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
object LabelRow {
  def apply(
    className: String,
    labelText: String,
    element: HtmlElement
  ): HtmlElement = {
    div(
        cls := className,
        labelText,
        element
    )
  }
}
