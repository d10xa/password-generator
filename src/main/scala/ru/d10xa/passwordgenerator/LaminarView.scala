package ru.d10xa.passwordgenerator

import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.html
import ru.d10xa.passwordgenerator.PasswordGeneratorOptions
import com.raquo.laminar.nodes.ReactiveElement
import com.raquo.domtypes.generic.nodes.Element
import com.raquo.domtypes.generic.codecs.StringAsIsCodec
import com.raquo.domtypes.generic.codecs.IntAsIsCodec
import com.raquo.domtypes.jsdom.defs.events.TypedTargetMouseEvent
import com.raquo.laminar.keys.LockedEventKey
import ru.d10xa.passwordgenerator.components.PasswordArea
import ru.d10xa.passwordgenerator.components.NumberPicker
import ru.d10xa.passwordgenerator.components.LabelRow

import javax.sound.sampled.Clip
import ru.d10xa.passwordgenerator.components.RangePicker
import ru.d10xa.passwordgenerator.components.Checkbox
import ru.d10xa.passwordgenerator.components.Tooltip
import ru.d10xa.passwordgenerator.Extensions.*

class LaminarView {

  def checkboxRow(
      text: String,
      signal: Signal[Boolean],
      signalDisabled: Signal[Boolean],
      observer: Observer[Boolean]
  ): HtmlElement =
    div(
      cls := "generator__check",
      label(
        text
      ),
      Checkbox(
        signal,
        signalDisabled,
        observer
      )
    )

  def blackLine(): Modifier[HtmlElement] = hr(cls := "generator__hr")

  def renderView(rootNode: dom.Element): Unit = {
    val state = new State

    def intInputRow(
        text: String,
        v: Signal[Int],
        plusEnabled: Signal[Boolean],
        f: Int => Command
    ): HtmlElement = LabelRow(
      className = "generator__int-input",
      labelText = text,
      element = div(
        NumberPicker(
          v = v,
          plusEnabled = plusEnabled,
          observer = state.commandObserver.contramap[Int](f)
        )
      )
    )
    val uppercaseCheckbox = checkboxRow(
      text = "A-Z",
      signal = state.passwordSignal.map(_.uppercaseEnabled),
      signalDisabled = state.passwordSignal.map(s =>
        !s.allowToDisableCheckbox && s.uppercaseEnabled
      ),
      observer = state.commandObserver.contramap(_ => UpdateUppercaseCheckbox())
    )
    val lowercaseCheckbox = checkboxRow(
      text = "a-z",
      signal = state.passwordSignal.map(_.lowercaseEnabled),
      signalDisabled = state.passwordSignal.map(s =>
        !s.allowToDisableCheckbox && s.lowercaseEnabled
      ),
      observer = state.commandObserver.contramap(_ => UpdateLowercaseCheckbox())
    )
    val numberCheckbox = checkboxRow(
      text = "0-9",
      signal = state.passwordSignal.map(_.numberEnabled),
      signalDisabled = state.passwordSignal.map(s =>
        !s.allowToDisableCheckbox && s.numberEnabled
      ),
      observer = state.commandObserver.contramap(_ => UpdateNumberCheckbox())
    )
    val specialCheckbox = checkboxRow(
      text = "!@#$%^&*",
      signal = state.passwordSignal.map(_.specialEnabled),
      signalDisabled = state.passwordSignal.map(s =>
        !s.allowToDisableCheckbox && s.specialEnabled
      ),
      observer = state.commandObserver.contramap(_ => UpdateSpecialCheckbox())
    )
    val ambiguousCheckbox = checkboxRow(
      text = "Ambiguous characters (l, I, O, 0, 1)",
      signal = state.passwordSignal.map(_.ambiguousEnabled),
      signalDisabled = Signal.fromValue(false),
      observer = state.commandObserver.contramap(_ => UpdateAmbiguousCheckbox())
    )

    val minimumUppercase = intInputRow(
      text = "Minimum uppercase",
      v = state.uppercaseValueSignal,
      plusEnabled = state.uppercasePlusEnabledSignal,
      f = i => UpdateMinUppercase(_ + i)
    )

    val minimumLowercase = intInputRow(
      text = "Minimum lowercase",
      state.lowercaseValueSignal,
      plusEnabled = state.lowercasePlusEnabledSignal,
      f = i => UpdateMinLowercase(_ + i)
    )

    val minimumNumbers = intInputRow(
      text = "Minimum numbers",
      state.minimumNumbersValueSignal,
      plusEnabled = state.minimumNumbersPlusEnabledSignal,
      f = i => UpdateMinNumbers(_ + i)
    )
    val minimumSpecial = intInputRow(
      text = "Minimum special",
      state.specialValueSignal,
      plusEnabled = state.minimumSpecialPlusEnabledSignal,
      f = i => UpdateMinSpecial(_ + i)
    )

    val intInputs = Vector(
      minimumUppercase,
      minimumLowercase,
      minimumNumbers,
      minimumSpecial
    )

    val lenghtRow = LabelRow(
      className = "generator__range-input",
      labelText = "Length",
      element = RangePicker(
        signal = state.passwordSignal.map(_.lengthValue),
        minValue = state.passwordSignal.map(_.lengthLeftBound),
        observer =
          state.commandObserver.contramap[Int](i => UpdateLength(_ => i))
      )
    )

    val header = h2(
      textAlign := "center",
      "Password Generator"
    )

    val allRows =
      Vector(
        header,
        PasswordArea(state.passwordState),
        uppercaseCheckbox,
        lowercaseCheckbox,
        numberCheckbox,
        specialCheckbox,
        ambiguousCheckbox,
        lenghtRow,
        minimumUppercase,
        minimumLowercase,
        minimumNumbers,
        minimumSpecial
      )

    val generatorDiv: ReactiveHtmlElement[html.Div] =
      div(
        cls := "generator",
        allRows.flatMap(cb => Vector(cb, blackLine()))
      )

    render(rootNode, generatorDiv)
  }
}
