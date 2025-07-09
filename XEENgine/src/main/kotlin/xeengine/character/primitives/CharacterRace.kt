package xeengine.character.primitives

import xeengine.character.defined.CharacterSkills

class CharacterRace (
    val name: String,
    val hpMod: Int,
    val thieveryBonus: Int,
    val spBonus: Int,
    val spBonusClass:  Array<CharacterClass>,
    val skills: Array<CharacterSkills>
)