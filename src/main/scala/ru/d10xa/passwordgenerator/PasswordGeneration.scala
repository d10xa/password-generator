package ru.d10xa.passwordgenerator

import scala.util.chaining.scalaUtilChainingOps
import scala.util.chaining.*

object PasswordGeneration {

  private enum CharType:
    case AnyChar, Lowercase, Uppercase, Number, Special

  val secureRandom: scala.util.Random =
    scala.util.Random.javaRandomToRandom(java.security.SecureRandom())

  private def makePositions(o: PasswordGeneratorOptions): Vector[CharType] = {
    val positions: Vector[CharType] =
      Vector.fill(o.lowercaseValueEnabled)(CharType.Lowercase) ++
        Vector.fill(o.uppercaseValueEnabled)(CharType.Uppercase) ++
        Vector.fill(o.numberValueEnabled)(CharType.Number) ++
        Vector.fill(o.specialValueEnabled)(CharType.Special)
    val fillGaps: Vector[CharType] =
      Vector.fill(o.lengthValue - positions.length)(CharType.AnyChar)
    secureRandom.shuffle(positions ++ fillGaps)
  }

  def generatePassword(o: PasswordGeneratorOptions): GeneratedPassword  =
    // no sanitize
    // no passphrase
    makePositions(o)
      .map {
        case CharType.Lowercase => o.lowercaseCharSet
        case CharType.Uppercase => o.uppercaseCharSet
        case CharType.Number => o.numberCharSet
        case CharType.Special => o.specialCharSet
        case CharType.AnyChar => o.allCharSet
      }
      .map(charSet => charSet(RandomRange.randomMinMax(0, charSet.length)))
      .mkString
      .pipe(GeneratedPassword.apply)
}
