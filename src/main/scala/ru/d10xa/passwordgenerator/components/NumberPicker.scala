package ru.d10xa.passwordgenerator.components
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import ru.d10xa.passwordgenerator.Command

object NumberPicker {
  def apply(
      v: Signal[Int],
      plusEnabled: Signal[Boolean],
      observer: Observer[Int]
  ): HtmlElement =
    div(
      child <-- v.map(_.toString),
      button(
        cls := "number-picker__button",
        "-",
        onClick.map[Int](_ => -1) --> observer
      ),
      button(
        cls := "number-picker__button",
        "+",
        disabled <-- plusEnabled.map(!_),
        onClick.map[Int](_ => 1) --> observer
      )
    )
}
