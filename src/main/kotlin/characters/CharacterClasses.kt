package xeengine.src.main.characters

object CharacterClasses {
    val Knight = CharacterClass(10, 5, arrayOf(CharacterSkill.ArmsMaster), false)
    val Paladin = CharacterClass(8, 6, arrayOf(CharacterSkill.Crusader), true)
    val Archer = CharacterClass(7, 6, emptyArray(), true)
    val Cleric = CharacterClass(5, 7, emptyArray(), true)
    val Sorcerer = CharacterClass(4, 8, arrayOf(CharacterSkill.Cartographer), true)
    val Robber = CharacterClass(8, 6, arrayOf(CharacterSkill.Thievery), false)
    val Ninja = CharacterClass(7, 5, arrayOf(CharacterSkill.Thievery), false)
    val Barbarian = CharacterClass(12, 4, emptyArray(), false)
    val Druid = CharacterClass(6, 7, arrayOf(CharacterSkill.DirectionSense), true)
    val Ranger = CharacterClass(9, 6, arrayOf(CharacterSkill.Pathfinder), true)
}