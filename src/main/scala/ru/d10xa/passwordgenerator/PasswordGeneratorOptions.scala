package ru.d10xa.passwordgenerator

case class PasswordGeneratorOptions(
    lengthValue: Int,
    ambiguousEnabled: Boolean,
    numberEnabled: Boolean,
    numberValue: Int,
    uppercaseEnabled: Boolean,
    uppercaseValue: Int,
    lowercaseEnabled: Boolean,
    lowercaseValue: Int,
    specialEnabled: Boolean,
    specialValue: Int,
    justCounter: Int = 1 // only to change equality
) {

  val lowercaseValueEnabled: Int = if (lowercaseEnabled) lowercaseValue else 0
  val uppercaseValueEnabled: Int = if (uppercaseEnabled) uppercaseValue else 0
  val numberValueEnabled: Int = if (numberEnabled) numberValue else 0
  val specialValueEnabled: Int = if (specialEnabled) specialValue else 0

  val lowercaseValueEnabled01: Int = if (lowercaseEnabled) 1 else 0
  val uppercaseValueEnabled01: Int = if (uppercaseEnabled) 1 else 0
  val numberValueEnabled01: Int = if (numberEnabled) 1 else 0
  val specialValueEnabled01: Int = if (specialEnabled) 1 else 0

  val allValuesEnabledSum: Int =
    lowercaseValueEnabled + uppercaseValueEnabled +
      numberValueEnabled + specialValueEnabled
  val numberRightBound: Int =
    lengthValue - (allValuesEnabledSum - numberValueEnabled)
  val lowercaseRightBound: Int =
    lengthValue - (allValuesEnabledSum - lowercaseValueEnabled)
  val uppercaseRightBound: Int =
    lengthValue - (allValuesEnabledSum - uppercaseValueEnabled)
  val specialRightBound: Int =
    lengthValue - (allValuesEnabledSum - specialValueEnabled)
  val lengthLeftBound: Int = lowercaseValueEnabled01 + uppercaseValueEnabled01 +
    numberValueEnabled01 + specialValueEnabled01

  val lowercaseCharSet: Vector[Char] = {
    val chars = "abcdefghijkmnopqrstuvwxyz".toVector
    if (ambiguousEnabled) chars ++ "l" else chars
  }
  val uppercaseCharSet: Vector[Char] = {
    val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ".toVector
    if (ambiguousEnabled) chars ++ "IO" else chars
  }
  val numberCharSet: Vector[Char] = {
    val chars = "23456789".toVector
    if (ambiguousEnabled) chars ++ "01" else chars
  }
  val specialCharSet: Vector[Char] = "!@#$%^&*".toVector

  def ifEnabled(condition: Boolean, v: Vector[Char]): Vector[Char] =
    if (condition) v else Vector.empty

  private def fitInRange(minValue: Int, maxValue: Int)(value: Int) =
    scala.math.max(minValue, scala.math.min(maxValue, value))

  def updateLength(f: Int => Int): PasswordGeneratorOptions = {
    def fit = fitInRange(1, 128)
    val newValue = fit(f(lengthValue))
    this.copy(lengthValue = scala.math.max(newValue, lengthLeftBound))
  }
  def updateLength(): PasswordGeneratorOptions = this.updateLength(identity)
  
  def updateNumber(f: Int => Int): PasswordGeneratorOptions = {
      def fit = fitInRange(1, numberRightBound)
      val oldValue = numberValue
      val newValue = fit(f(oldValue))
      this.copy(numberValue = newValue)
  }
  def updateUppercase(f: Int => Int): PasswordGeneratorOptions = {
    def fit = fitInRange(1, uppercaseRightBound)
    val oldValue = uppercaseValue
    val newValue = fit(f(oldValue))
    this.copy(uppercaseValue = newValue)
  }

  def updateLowercase(f: Int => Int): PasswordGeneratorOptions = {
    def fit = fitInRange(1, lowercaseRightBound)
    val oldValue = lowercaseValue
    val newValue = fit(f(oldValue))
    this.copy(lowercaseValue = newValue)
  }

  def updateSpecial(f: Int => Int): PasswordGeneratorOptions = {
    def fit = fitInRange(1, specialRightBound)
    val oldValue = specialValue
    val newValue = fit(f(oldValue))
    this.copy(specialValue = newValue)
  }

  val allCharSet: Vector[Char] =
    ifEnabled(lowercaseEnabled, lowercaseCharSet) ++
      ifEnabled(uppercaseEnabled, uppercaseCharSet) ++
      ifEnabled(numberEnabled, numberCharSet) ++
      ifEnabled(specialEnabled, specialCharSet)
  
}
