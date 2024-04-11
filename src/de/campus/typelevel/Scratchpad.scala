package de.campus.typelevel

import de.campus.typelevel.Peano.*

object Scratchpad extends App {
  val zero = Natural[_0, _0](0)
  val one  = Natural[_1, _1](1)
  val two  = Natural[_2, _2](2)
  val ten  = Natural[_10, _10](10)

  println(s"Type of zero: ${zero.getClass}")
  println(s"Type of ten: ${ten.getClass}")

  val result = one + one + two + two

  println(s"Result: $result")
}
