package de.campus.typelevel

import de.campus.typelevel.Peano.*

class Natural[MIN <: Nat, MAX <: Nat](val value: BigInt)(using MIN <= MAX)

trait <=[MIN <: Nat, MAX <: Nat]

given basic[MAX <: Nat]: <=[_0, MAX] with                                                      {}
given inductive[MIN <: Nat, MAX <: Nat](using ev: <=[MIN, MAX]): <=[Succ[MIN], Succ[MAX]] with {}
