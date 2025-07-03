package xeengine.src.main.character.primitives

import xeengine.src.main.character.defined.CharacterSkills
import xeengine.src.main.character.defined.CharacterStats

class CharacterClass (
    val name: String,
    val statRequirements: CharacterStats,
    val hpPerLevel: Int,
    val levelsPerAttack: Int,
    val skills: Array<CharacterSkills>,
    val hasSpells: Boolean,
    val thievery: Int = 0,
    val thieveryPerLevel: Int = 0
)