package xeengine.src.main.characters

class CharacterClass (val name: String, val statRequirements: CharacterStats, val hpPerLevel: Int, val levelsPerAttack: Int, val skills: Array<CharacterSkill>, val hasSpells: Boolean, val thievery: Int = 0, val thieveryPerLevel: Int = 0)