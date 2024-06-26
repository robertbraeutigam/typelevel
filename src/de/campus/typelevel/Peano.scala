package de.campus.typelevel

object Peano {
  sealed trait Nat
  trait _0             extends Nat
  class Succ[N <: Nat] extends Nat

  type _1  = Succ[_0]
  type _2  = Succ[_1]
  type _3  = Succ[_2]
  type _4  = Succ[_3]
  type _5  = Succ[_4]
  type _6  = Succ[_5]
  type _7  = Succ[_6]
  type _8  = Succ[_7]
  type _9  = Succ[_8]
  type _10 = Succ[_9]
}
