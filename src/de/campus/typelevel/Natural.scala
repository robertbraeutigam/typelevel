package de.campus.typelevel

import de.campus.typelevel.Peano.{Nat, *}
import de.campus.typelevel.PeanoOps.*

sealed trait Natural[MIN <: Nat, MAX <: Nat] {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using
      minSum: MIN + MIN2,
      maxSum: MAX + MAX2,
      ev: minSum.Result <= maxSum.Result,
      small: _5 >? maxSum.Result
  ): Natural[minSum.Result, maxSum.Result]
}

object Natural {
  def apply[MIN <: Nat, MAX <: Nat](
      v: BigInt
  )(using ev: MIN <= MAX, factory: NaturalFactory[MIN, MAX]): Natural[MIN, MAX] = factory.create(v)

  trait NaturalFactory[MIN <: Nat, MAX <: Nat] {
    def create(v: BigInt): Natural[MIN, MAX]
  }

  given optimizedFactory[MIN <: Nat, MAX <: Nat](using MAX < _10, MIN <= MAX): NaturalFactory[MIN, MAX] with {
    override def create(v: BigInt): Natural[MIN, MAX] = OptimizedNatural[MIN, MAX](v.toByte)
  }
  given highFactory[MIN <: Nat, MAX <: Nat](using _5 < MAX, MIN <= MAX): NaturalFactory[MIN, MAX] with       {
    override def create(v: BigInt): Natural[MIN, MAX] = HighNatural[MIN, MAX](v)
  }

  class OptimizedNatural[MIN <: Nat, MAX <: Nat](using MIN <= MAX)(val b: Byte) extends Natural[MIN, MAX] {
    override def +[MIN2 <: Nat, MAX2 <: Nat](
        other: Natural[MIN2, MAX2]
    )(using
        minSum: MIN + MIN2,
        maxSum: MAX + MAX2,
        ev: minSum.Result <= maxSum.Result,
        small: _5 >? maxSum.Result
    ): Natural[minSum.Result, maxSum.Result] = if (small.isDefined) {
      println("Adding with optimization")
      OptimizedNatural((other.asInstanceOf[OptimizedNatural[_, _]].b + b).toByte)
    } else {
      println("Adding withOUT optimization")
      other match
        case natural: OptimizedNatural[_, _] => HighNatural(b + natural.b.toInt)
        case natural: HighNatural[_, _]      => HighNatural(b.toInt + natural.v)
    }
  }

  class HighNatural[MIN <: Nat, MAX <: Nat](using MIN <= MAX)(val v: BigInt) extends Natural[MIN, MAX] {
    override def +[MIN2 <: Nat, MAX2 <: Nat](
        other: Natural[MIN2, MAX2]
    )(using
        minSum: MIN + MIN2,
        maxSum: MAX + MAX2,
        ev: minSum.Result <= maxSum.Result,
        small: _5 >? maxSum.Result
    ): Natural[minSum.Result, maxSum.Result] = other match
      case natural: OptimizedNatural[_, _] => HighNatural(v + natural.b.toInt)
      case natural: HighNatural[_, _]      => HighNatural(v + natural.v)
  }
}
