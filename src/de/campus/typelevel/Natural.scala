package de.campus.typelevel

import de.campus.typelevel.Peano.*
import de.campus.typelevel.PeanoOps.<=

import scala.annotation.implicitNotFound

class Natural[MIN <: Nat, MAX <: Nat](val value: BigInt)(using MIN <= MAX)
