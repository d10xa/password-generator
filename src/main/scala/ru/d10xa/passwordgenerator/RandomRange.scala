package ru.d10xa.passwordgenerator

object RandomRange {

  val secureRandom: scala.util.Random =
    scala.util.Random.javaRandomToRandom(java.security.SecureRandom())

  def log2(x: Double): Double = scala.math.log(x) / scala.math.log(2)

  // https://github.com/EFForg/OpenWireless/blob/master/app/js/diceware.js
  // http://stackoverflow.com/questions/18230217/javascript-generate-a-random-number-within-a-range-using-crypto-getrandomvalues

  def randomMinMax(min: Integer, max: Integer): Integer = {
    var rval: Double = 0
    val range = max - min

    val bitsNeeded = scala.math.ceil(log2(range))
    if (bitsNeeded > 53.0) {
      throw new IllegalArgumentException("We cannot generate numbers larger than 53 bits.")
    }
    val bytesNeeded = Math.ceil(bitsNeeded / 8).intValue
    val mask = Math.pow(2, bitsNeeded) - 1

    val byteArray = new Array[Byte](bytesNeeded)
    secureRandom.nextBytes(byteArray)
    var p: Int = (bytesNeeded - 1) * 8
    for (i <- 0 until bytesNeeded) {
      rval = rval + (byteArray(i) * Math.pow(2, p))
      p = p - 8
    }
    rval = rval.intValue & mask.intValue
    if (rval >= range) {
      this.randomMinMax(min, max)
    } else min + rval.intValue
  }
}
