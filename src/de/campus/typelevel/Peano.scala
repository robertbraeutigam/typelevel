package de.campus.typelevel

object Peano {
  trait Nat
  class _0             extends Nat
  class Succ[N <: Nat] extends Nat

  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]

  // Plus
  trait +[A <: Nat, B <: Nat] {
    type Result <: Nat
  }

  object + {
    type PlusFull[A <: Nat, B <: Nat, R <: Nat] = +[A, B] { type Result = R }

    given basic[N <: Nat]: PlusFull[_0, N, N]                                                                        = new +[_0, N] {
      override type Result = N
    }
    given inductive[A <: Nat, N <: Nat, R <: Nat](using induction: PlusFull[A, N, R]): PlusFull[Succ[A], N, Succ[R]] =
      new +[Succ[A], N] {
        override type Result = Succ[R]
      }
  }
}
