package ru.d10xa.passwordgenerator

sealed trait Command
case class UpdateLength(f: Int => Int) extends Command
case class UpdateMinNumbers(f: Int => Int) extends Command
case class UpdateMinUppercase(f: Int => Int) extends Command
case class UpdateMinLowercase(f: Int => Int) extends Command
case class UpdateMinSpecial(f: Int => Int) extends Command
case class UpdateUppercaseCheckbox() extends Command
case class UpdateLowercaseCheckbox() extends Command
case class UpdateSpecialCheckbox() extends Command
case class UpdateNumberCheckbox() extends Command
case class UpdateAmbiguousCheckbox() extends Command
