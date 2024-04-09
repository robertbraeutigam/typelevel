package de.campus.typelevel

object Peano {
  trait Nat
  class _0             extends Nat
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

  // Plus
  trait +[A <: Nat, B <: Nat] {
    type Result <: Nat
  }

  object + {
    type PlusFull[A <: Nat, B <: Nat, R <: Nat] = +[A, B] { type Result = R }

    given basic[N <: Nat]: PlusFull[_0, N, N]                                                                        =
      new +[_0, N] {
        override type Result = N
      }
    given inductive[A <: Nat, N <: Nat, R <: Nat](using induction: PlusFull[A, N, R]): PlusFull[Succ[A], N, Succ[R]] =
      new +[Succ[A], N] {
        override type Result = Succ[R]
      }
  }

  // Less
  trait <[A <: Nat, B <: Nat]

  object < {
    given basic[A <: Nat]: <[_0, Succ[A]] with                                        {}
    given inductive[A <: Nat, B <: Nat](using lte: <[A, B]): <[Succ[A], Succ[B]] with {}
  }

  // Greater
  trait >[A <: Nat, B <: Nat]

  object > {
    given basic[A <: Nat]: >[Succ[A], _0] with                                        {}
    given inductive[A <: Nat, B <: Nat](using gte: >[A, B]): >[Succ[A], Succ[B]] with {}
  }
}
