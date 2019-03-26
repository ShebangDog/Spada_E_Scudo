package Status

import Character.Identifilable

object Utility {
  def lst: List[Figure] = List[Figure](HP(0), Attack(0), Defend(0), Speed(0), ChargeTime(0))

  def identificationString(figure: Figure): String = (figure match {
    case _: HP => HP
    case _: Attack => Attack
    case _: Defend => Defend
    case _: Speed => Speed
    case _: ChargeTime => ChargeTime
  }).identificationString
}
