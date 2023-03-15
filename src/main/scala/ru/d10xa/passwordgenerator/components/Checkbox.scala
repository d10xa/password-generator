package ru.d10xa.passwordgenerator.components
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
object Checkbox {
  def apply(
      signal: Signal[Boolean],
      signalDisabled: Signal[Boolean],
      observer: Observer[Boolean]
  ): HtmlElement =
    div(
      cls := "checkbox",
      input(
        cls := "checkbox__input",
        tpe("checkbox"),
        disabled <-- signalDisabled,
        controlled(
          checked <-- signal,
          onClick.mapToChecked --> observer
        )
      )
    )
}
