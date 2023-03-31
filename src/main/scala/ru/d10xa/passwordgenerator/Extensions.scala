package ru.d10xa.passwordgenerator

object Extensions {
  extension (b: Boolean) {
    def toInt: Int = if (b) 1 else 0
  }
  extension (that: PasswordGeneratorOptions) {
    def allowToDisableCheckbox: Boolean = {
      val c = that.uppercaseEnabled.toInt +
        that.lowercaseEnabled.toInt + that.specialEnabled.toInt + that.numberEnabled.toInt
      c > 1
    }
  }
}
