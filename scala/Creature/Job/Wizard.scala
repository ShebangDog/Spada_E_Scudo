package Creature.Job

import Creature.{Creature, CreatureUtility, Person}
import Effector.{Effector, Spell, Transitionable}
import Identifilable.Identifilable
import _root_.Effector.Equipment.Equipment
import Status.Status

class Wizard(_name: String, _status: Status, equipment: Equipment, _spellLst: List[Spell], _transitionableEffectorLst: List[Effector with Transitionable] = Nil) extends Person(_name, _status, equipment, _transitionableEffectorLst) {
  override def identify: String = "wizard"

  override def spellLst: List[Spell] = _spellLst

  override def flucstrateStatus(identificatable: Identifilable, func: Int => Int): Wizard = {
    val lst = _status.intMap.unzip._2.toList

    Wizard(
      _name,
      Status((func(_status.intMap(identificatable)) :: lst.reverse).reverse),
      equipment,
      spellLst,
      _transitionableEffectorLst
    )
  }

  override def chant(spell: Spell, target: Creature, participantMap: Map[String, Creature]): Map[String, Creature] =
    participantMap + CreatureUtility.creatureToMapElem(target.applyEffect(spell))

  override def clearEffect: Creature = Wizard(_name, _status, equipment, _spellLst)

  override def applyEffect(effect: Effector): Creature = effect match {
    case transitionableEffect: Effector with Transitionable => Wizard(_name, _status, equipment, _spellLst, transitionableEffect :: _transitionableEffectorLst)
    case _ => this.flucstrateStatus(effect.adaptType, effect.activate)
  }
}

object Wizard {
  def apply(name: String, status: Status, equipment: Equipment, spellLst: List[Spell], transitionableEffectorLst: List[Effector with Transitionable] = Nil) : Wizard =
    new Wizard(name, status, equipment, spellLst, transitionableEffectorLst)
}
