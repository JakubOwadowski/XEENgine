package xeengine.src.main.characters

import kotlin.reflect.KClass

class CharacterRace (val name: String, val hpMod: Int, val thieveryBonus: Int, val spBonus: Int, val spBonusClass:  Array<CharacterClass>, val skills: Array<CharacterSkill>)