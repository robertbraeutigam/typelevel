package de.campus.typelevel

import de.campus.typelevel.Peano.*

case class Natural[MIN <: Nat, MAX <: Nat](value: BigInt) {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using min: MIN + MIN2, max: MAX + MAX2, lte: max.Result < _5): Natural[min.Result, max.Result] = Natural(
    value + other.value
  )

}
