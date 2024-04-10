package de.campus.typelevel

import de.campus.typelevel.Peano.*

object Scratchpad extends App {
  val zero = new Natural[_0, _0](0)
  val one  = new Natural[_1, _1](1)
  val two  = new Natural[_2, _2](2)

  def readSensor(): Natural[_0, _10] = ???

  val strange = new Natural[_10, _0](0)
}
