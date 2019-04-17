package GameManage.FlowManage.Choice

import Creature.Creature
import Effector.Spell
import Identifilable.Identifilable
import GameManage.FlowManage._

trait Choice {
  def declareTarget(executerName: String, participantMap: Map[String, Creature], readFunc: () => String): Map[String, Creature] => Map[String, Creature]
}

case object Attack extends Choice with Identifilable {
  override def declareTarget(executerName: String, participantMap: Map[String, Creature], readFunc: () => String): Map[String, Creature] => Map[String, Creature] = {
    val enemyNameLst = participantMap.toList.unzip._1.filter(_ != executerName)
    def declare: String = {
      val key = readFunc()

      if (enemyNameLst.contains(key)) key else declare
    }

    println("攻撃する相手を選択")
    val target = declare

    Action.Attack.execute(executerName, target, _)
  }

  override def identify: String = "attack"
}

case object Chant extends Choice with Identifilable {
  override def declareTarget(executerName: String, participantMap: Map[String, Creature], readFunc: () => String): Map[String, Creature] => Map[String, Creature] = {
    def declareSpell: Spell = {
      val spellName = readFunc()
      participantMap(executerName).spellLst.filter(_.identify == spellName) match {
        case Nil => declareSpell
        case spell :: _ => spell
      }
    }

    def declareTargetName: String = {
      val targetName = readFunc()

      if (participantMap.toList.unzip._1.contains(targetName)) targetName else declareTargetName
    }

    println("使用する呪文を選択")
    val useSpell = declareSpell

    println("呪文をかける相手を選択")
    val targetName = declareTargetName

    Action.Chant.execute(executerName, useSpell, targetName, _)
  }

  override def identify: String = "chant"
}

case object Defend extends Choice with Identifilable {
  override def declareTarget(executerName: String, participantMap: Map[String, Creature], readFunc: () => String): Map[String, Creature] => Map[String, Creature] =
    Action.Defend.execute(executerName, _)

  override def identify: String = "defend"
}
