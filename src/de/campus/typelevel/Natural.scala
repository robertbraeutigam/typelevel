package de.campus.typelevel

import de.campus.typelevel.Peano.*
import de.campus.typelevel.PeanoOps.*

class Natural[MIN <: Nat, MAX <: Nat](val value: BigInt)(using MIN <= MAX)(using minValue: BigIntValueOf[MIN]) {
  require(minValue.value <= value, s"Value $value less than MIN ${minValue.value}")
}

trait BigIntValueOf[N <: Nat] {
  val value: BigInt
}

given zeroCase: BigIntValueOf[_0] with                                                   {
  override val value: BigInt = 0
}
given inductiveCase[N <: Nat](using prev: BigIntValueOf[N]): BigIntValueOf[Succ[N]] with {
  override val value: BigInt = prev.value + 1
}
