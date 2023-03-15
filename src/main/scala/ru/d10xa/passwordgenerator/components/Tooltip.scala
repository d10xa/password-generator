package ru.d10xa.passwordgenerator.components
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given

object Tooltip {
  def apply(text: String) = div(
      cls := "tooltip__div",
      div(cls("tooltip__text"), text)
  )
}
