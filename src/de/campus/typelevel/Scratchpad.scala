package de.campus.typelevel

import de.campus.typelevel.Peano.*

object Scratchpad extends App {
  val one = Natural[_0, _1](1)
  val two = Natural[_0, _2](2)

  val r1 = one + two
}
