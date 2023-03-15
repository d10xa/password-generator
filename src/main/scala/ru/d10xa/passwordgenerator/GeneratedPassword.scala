package ru.d10xa.passwordgenerator
opaque type GeneratedPassword = String
object GeneratedPassword {
  def apply(s: String): GeneratedPassword = s
}
extension (p: GeneratedPassword) {
  def toString(): String = p.toString
}
