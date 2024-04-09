package de.campus.typelevel

import de.campus.typelevel.Natural.*
import de.campus.typelevel.Peano.*
import de.campus.typelevel.Peano.>?.given

object Scratchpad extends App {
  val one = toNatural(0)
  val two = Natural[_2, _2](2)

  def readSensor(): Natural[_0, _10] = Natural(5)

  val r1 = one + two + one + two

  println(s"$r1 (${r1.getClass})")
}
