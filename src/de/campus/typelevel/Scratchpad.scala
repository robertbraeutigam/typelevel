package de.campus.typelevel

import de.campus.typelevel.Natural.*
import de.campus.typelevel.Peano.*

object Scratchpad extends App {
  val one = Natural[_1, _1](1)
  val two = Natural[_2, _2](2)

  def readSensor(): Natural[_0, _10] = Natural(5)

  val r1 = one + two + one + two

  val l: Long = 4
  val i       = Integer.MAX_VALUE
  val r       = i + 0

  println(s"Equals ${r}")
}
