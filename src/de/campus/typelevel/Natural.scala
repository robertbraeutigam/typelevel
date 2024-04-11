package de.campus.typelevel

import de.campus.typelevel.Natural.AddNatural
import de.campus.typelevel.Peano.{Nat, *}
import de.campus.typelevel.PeanoOps.*

sealed trait Natural[MIN <: Nat, MAX <: Nat] {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using
      minSum: MIN + MIN2,
      maxSum: MAX + MAX2,
      ev: minSum.Result <= maxSum.Result,
      add: AddNatural[minSum.Result, maxSum.Result]
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
        add: AddNatural[minSum.Result, maxSum.Result]
    ): Natural[minSum.Result, maxSum.Result] = add.plus(this, other)
  }

  trait AddNatural[MIN <: Nat, MAX <: Nat] {
    def plus(left: Natural[_, _], right: Natural[_, _]): Natural[MIN, MAX]
  }

  given optimizedAdd[MIN <: Nat, MAX <: Nat](using MAX <= _5, MIN <= MAX): AddNatural[MIN, MAX] with {
    override def plus(left: Natural[_, _], right: Natural[_, _]): Natural[MIN, MAX] = {
      println("Using optimized add...")
      OptimizedNatural[MIN, MAX](
        (left.asInstanceOf[OptimizedNatural[_, _]].b + right.asInstanceOf[OptimizedNatural[_, _]].b).toByte
      )
    }
  }

  given highAdd[MIN <: Nat, MAX <: Nat](using MAX > _5, MIN <= MAX): AddNatural[MIN, MAX] with {
    override def plus(left: Natural[_, _], right: Natural[_, _]): Natural[MIN, MAX] = {
      println("Unoptimized add..")
      (left, right) match
        case (left: HighNatural[_, _], right: HighNatural[_, _])           => HighNatural(left.v + right.v)
        case (left: HighNatural[_, _], right: OptimizedNatural[_, _])      => HighNatural(left.v + right.b.toInt)
        case (left: OptimizedNatural[_, _], right: HighNatural[_, _])      => HighNatural(left.b.toInt + right.v)
        case (left: OptimizedNatural[_, _], right: OptimizedNatural[_, _]) => HighNatural(left.b + right.b)
    }
  }

  class HighNatural[MIN <: Nat, MAX <: Nat](using MIN <= MAX)(val v: BigInt) extends Natural[MIN, MAX] {
    override def +[MIN2 <: Nat, MAX2 <: Nat](
        other: Natural[MIN2, MAX2]
    )(using
        minSum: MIN + MIN2,
        maxSum: MAX + MAX2,
        ev: minSum.Result <= maxSum.Result,
        add: AddNatural[minSum.Result, maxSum.Result]
    ): Natural[minSum.Result, maxSum.Result] = add.plus(this, other)
  }
}
