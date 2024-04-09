package de.campus.typelevel

import de.campus.typelevel.Peano.{Nat, +}

case class Natural[MIN <: Nat, MAX <: Nat](value: BigInt) {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using min: MIN + MIN2, max: MAX + MAX2): Natural[min.Result, max.Result] = Natural(value + other.value)
}