package ru.d10xa.passwordgenerator.components
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import ru.d10xa.passwordgenerator.Command
import ru.d10xa.passwordgenerator.UpdateLength
import com.raquo.domtypes.generic.codecs.IntAsIsCodec
object RangePicker {
  val minAttrInt = customProp("min", IntAsIsCodec)
  def apply(
      signal: Signal[Int],
      minValue: Signal[Int],
      observer: Observer[Int]
  ): HtmlElement = {
    div(
      child <-- signal.map(_.toString),
      input(
        typ := "range",
        minAttrInt <-- minValue,
        value <-- signal.map(_.toString),
        onInput.mapToValue.map(_.toInt) --> observer,
        onChange.mapToValue.map(_.toInt) --> observer
      ) 
    )
  }
}
