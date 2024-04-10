package de.campus.typelevel

import de.campus.typelevel.Peano.*

object Scratchpad extends App {
  val zero = Natural[_0, _0](0)
  val one  = Natural[_1, _1](1)
  val two  = Natural[_2, _2](2)

  def readSensor(): Natural[_0, _10] = ???

  val result = one + one + two
}
