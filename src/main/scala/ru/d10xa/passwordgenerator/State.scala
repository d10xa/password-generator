package ru.d10xa.passwordgenerator
import com.raquo.laminar.api.L
import com.raquo.laminar.api.L.*
import com.raquo.laminar.api.L.given
import ru.d10xa.passwordgenerator.Extensions.*

class State {
  val passwordState: Var[PasswordGeneratorOptions] =
    Var(
      PasswordGeneratorOptions(
        lengthValue = 8,
        ambiguousEnabled = false,
        numberEnabled = true,
        numberValue = 1,
        uppercaseEnabled = true,
        uppercaseValue = 1,
        lowercaseEnabled = true,
        lowercaseValue = 1,
        specialEnabled = true,
        specialValue = 1
      )
    )

  val passwordSignal = passwordState.signal
  val uppercaseValueSignal = passwordSignal.map(_.uppercaseValue)
  val uppercasePlusEnabledSignal = passwordSignal
    .map(state => state.uppercaseRightBound > state.uppercaseValue)
  val lowercaseValueSignal = passwordSignal.map(_.lowercaseValue)
  val lowercasePlusEnabledSignal = passwordSignal
    .map(state => state.lowercaseRightBound > state.lowercaseValue)
  val minimumNumbersValueSignal = passwordSignal.map(_.numberValue)
  val minimumNumbersPlusEnabledSignal = passwordSignal
    .map(state => state.numberRightBound > state.numberValue)
  val specialValueSignal = passwordSignal.map(_.specialValue)
  val minimumSpecialPlusEnabledSignal = passwordSignal
    .map(state => state.specialRightBound > state.specialValue)

  val commandObserver: Observer[Command] = Observer[Command] {
    case UpdateLength(f) =>
      passwordState.update(_.updateLength(f))
    case UpdateMinNumbers(f) =>
      passwordState.update(_.updateNumber(f))
    case UpdateMinUppercase(f) =>
      passwordState.update(_.updateUppercase(f))
    case UpdateMinLowercase(f) =>
      passwordState.update(_.updateLowercase(f))
    case UpdateMinSpecial(f) =>
      passwordState.update(_.updateSpecial(f))
    case UpdateUppercaseCheckbox() =>
      passwordState.update { state =>
        val enabled = state.uppercaseEnabled
        if (!state.allowToDisableCheckbox && enabled) {
          state
        } else {
          state.copy(uppercaseEnabled = !enabled)
        }
      }
    case UpdateLowercaseCheckbox() =>
      passwordState.update { state =>
        val enabled = state.lowercaseEnabled
        if (!state.allowToDisableCheckbox && enabled) {
          state
        } else {
          state.copy(lowercaseEnabled = !enabled)
        }
      }
    case UpdateSpecialCheckbox() =>
      passwordState.update { state =>
        val enabled = state.specialEnabled
        if (!state.allowToDisableCheckbox && enabled) {
          state
        } else {
          state.copy(specialEnabled = !enabled)
        }
      }
    case UpdateNumberCheckbox() =>
      passwordState.update { state =>
        val enabled = state.numberEnabled
        if (!state.allowToDisableCheckbox && enabled) {
          state
        } else {
          state.copy(numberEnabled = !enabled)
        }
      }
    case UpdateAmbiguousCheckbox() =>
      passwordState.update { state =>
        state.copy(ambiguousEnabled = !state.ambiguousEnabled)
      }
  }

}
