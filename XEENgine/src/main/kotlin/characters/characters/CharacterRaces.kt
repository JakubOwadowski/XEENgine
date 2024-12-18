package xeengine.src.main.characters.characters

import xeengine.src.main.characters.CharacterClass
import xeengine.src.main.characters.CharacterClasses
import xeengine.src.main.characters.CharacterRace
import xeengine.src.main.characters.CharacterSkill
import xeengine.src.main.characters.CharacterStats
import xeengine.src.main.utils.LanguageUtil

object CharacterRaces {
    val Human = CharacterRace(
        LanguageUtil.get("character-race-human-name"),
        0,
        0,
        0,
        arrayOf(),
        arrayOf(CharacterSkill.Swimmer)
    )
    val Elf = CharacterRace(
        LanguageUtil.get("character-race-elf-name"),
        -2,
        10,
        2,
        arrayOf(CharacterClasses.Sorcerer),
        arrayOf()
    )
    val Dwarf = CharacterRace(
        LanguageUtil.get("character-race-dwarf-name"),
        1,
        5,
        -1,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf(CharacterSkill.SpotSecretDoors)
    )
    val Gnome = CharacterRace(
        LanguageUtil.get("character-race-gnome-name"),
        -1,
        10,
        1,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf(CharacterSkill.DangerSense)
    )
    val HalfOrc = CharacterRace(
        LanguageUtil.get("character-race-half-orc-name"),
        2,
        -10,
        -2,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf()
    )
}