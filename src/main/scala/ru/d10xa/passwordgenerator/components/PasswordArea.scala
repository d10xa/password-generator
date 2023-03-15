package ru.d10xa.passwordgenerator.components

import com.raquo.domtypes.jsdom.defs.events.TypedTargetMouseEvent
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import com.raquo.laminar.keys.LockedEventKey
import org.scalajs.dom
import ru.d10xa.passwordgenerator.Clipboard
import ru.d10xa.passwordgenerator.PasswordGeneration
import ru.d10xa.passwordgenerator.GeneratedPassword
import ru.d10xa.passwordgenerator.PasswordGeneratorOptions
import ru.d10xa.passwordgenerator.svg.CopySvg
import ru.d10xa.passwordgenerator.svg.RefreshSvg
import javax.script.SimpleBindings
import com.raquo.laminar.nodes.ReactiveHtmlElement
import ru.d10xa.passwordgenerator.Clipboard.copy
import com.raquo.airstream.core.ObserverList


object PasswordArea {

  def apply(passwordState: Var[PasswordGeneratorOptions]): HtmlElement = {
    val generatedPassword: Signal[GeneratedPassword] =
      passwordState.signal.map(PasswordGeneration.generatePassword)

    val onClickV: EventProcessor[TypedTargetMouseEvent[dom.Element], Unit] =
      onClick.preventDefault.mapToValue.map(_ => ())

    val copyToClipboardEvent =
      composeEvents(onClickV)(_.withCurrentValueOf(generatedPassword))

    def o =
      Observer[GeneratedPassword](onNext => Clipboard.copy(onNext.toString()))

    val copyBus = new EventBus[Unit]
    val maybeTooltip = EventStream.merge(
      copyBus.events.mapTo(Some(Tooltip("Copied!"))),
      copyBus.events.flatMap { _ =>
        EventStream.fromValue(None, emitOnce = true).delay(500)
      }
    )

    val element = div(
      cls := "password-view-row",
      child.maybe <-- maybeTooltip,
      PasswordArea.Password(generatedPassword),
      div(
        cls := "password-view-row__buttons",
        button(
          cls := "password-view-row__button",
          RefreshSvg(),
          onClick.mapToValue --> passwordState.updater((opt, _) =>
            opt.copy(justCounter = opt.justCounter + 1)
          )
        ),
        button(
          cls := "password-view-row__button",
          CopySvg(),
          copyToClipboardEvent --> { v =>
            o.onNext(v)
            copyBus.emit(())
          }
        )
      )
    )

    element
  }

  def Password(
      generatedPassword: Signal[GeneratedPassword]
  ): HtmlElement =
    div(
      cls := "password-view-row__password",
      p(
        cls := "password-view-row__p",
        child <-- generatedPassword.map(_.toString)
      )
    )
}
