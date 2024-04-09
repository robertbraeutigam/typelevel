package de.campus.typelevel

import de.campus.typelevel.Peano.*
import scala.quoted.*

case class Natural[MIN <: Nat, MAX <: Nat](value: BigInt) {
  def +[MIN2 <: Nat, MAX2 <: Nat](
      other: Natural[MIN2, MAX2]
  )(using min: MIN + MIN2, max: MAX + MAX2, isHigh: max.Result >? _5): Natural[min.Result, max.Result] = {
    if (isHigh.isEmpty) {
      println("Adding low numbers...")
    } else {
      println("Adding high numbers...")
    }
    Natural(value + other.value)
  }
}

object Natural {
  transparent inline def toNatural(inline n: Int): Any = ${ toNaturalMacro('n) }

  def toNaturalMacro(n: Expr[Int])(using Quotes): Expr[Any] = {
    import quotes.reflect.*

    def peanoType(n: Int): TypeRepr = n match {
      case 0 => TypeRepr.of[_0]
      case _ => AppliedType(TypeRepr.of[Succ], List(peanoType(n - 1)))
    }

    def createNatural(n: Int): Expr[Any] = {
      val tpe = peanoType(n)
      '{ Natural[_0, _0](0) }
    }

    n.value match {
      case Some(number) if number >= 0 => createNatural(number)
      case Some(_)                     => report.errorAndAbort("Negative number cannot be converted to Natural number")
      case None                        => report.errorAndAbort("Not a constant expression")
    }
  }
}
