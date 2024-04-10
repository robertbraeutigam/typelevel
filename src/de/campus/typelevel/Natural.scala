package de.campus.typelevel

import de.campus.typelevel.Peano.*
import de.campus.typelevel.PeanoOps.*

class Natural[MIN <: Nat, MAX <: Nat](val value: BigInt)(using MIN <= MAX) {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using
      minSum: MIN + MIN2,
      maxSum: MAX + MAX2,
      ev: minSum.Result <= maxSum.Result
  ): Natural[minSum.Result, maxSum.Result] =
    Natural(value + other.value)
}
