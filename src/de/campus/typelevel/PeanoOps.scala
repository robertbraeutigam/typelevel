package de.campus.typelevel

import de.campus.typelevel.Peano.*

object PeanoOps {
  // Plus
  trait +[A <: Nat, B <: Nat] {
    type Result <: Nat
  }

  type PlusFull[A <: Nat, B <: Nat, R <: Nat] = +[A, B] { type Result = R }

  given basicPlus[N <: Nat]: PlusFull[_0, N, N]                                                                        =
    new +[_0, N] {
      override type Result = N
    }
  given inductivePlus[A <: Nat, N <: Nat, R <: Nat](using induction: PlusFull[A, N, R]): PlusFull[Succ[A], N, Succ[R]] =
    new +[Succ[A], N] {
      override type Result = Succ[R]
    }

  // Less
  trait <[A <: Nat, B <: Nat]

  given basicLess[A <: Nat]: <[_0, Succ[A]] with                                        {}
  given inductiveLess[A <: Nat, B <: Nat](using lte: <[A, B]): <[Succ[A], Succ[B]] with {}

  // Greater
  trait >[A <: Nat, B <: Nat]

  given basicGreater[A <: Nat]: >[Succ[A], _0] with                                        {}
  given inductiveGreater[A <: Nat, B <: Nat](using gte: >[A, B]): >[Succ[A], Succ[B]] with {}

  // Greater?
  type >?[A <: Nat, B <: Nat] = Option[A > B]

  given existsGreaterOption[A <: Nat, B <: Nat](using ev: A > B): (A >? B)        = Option(ev)
  given notExistsEqualGreaterOption[A <: Nat, B <: Nat](using A =:= B): (A >? B)  = Option.empty
  given notExistsLessGreaterOption[A <: Nat, B <: Nat](using ev: A < B): (A >? B) = Option.empty

  // <=
  trait <=[MIN <: Nat, MAX <: Nat]

  given basicLessOrEqual[MAX <: Nat]: <=[_0, MAX] with                                                      {}
  given inductiveLessOrEqual[MIN <: Nat, MAX <: Nat](using ev: <=[MIN, MAX]): <=[Succ[MIN], Succ[MAX]] with {}
}
