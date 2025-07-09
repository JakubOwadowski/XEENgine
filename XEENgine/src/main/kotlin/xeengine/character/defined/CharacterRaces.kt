package xeengine.character.defined

import xeengine.character.primitives.CharacterRace
import xeengine.utils.LanguageUtil

object CharacterRaces {
    val Human = CharacterRace(
        LanguageUtil.get("CHARACTER_RACE_HUMAN_NAME"),
        0,
        0,
        0,
        arrayOf(),
        arrayOf(CharacterSkills.Swimmer)
    )
    val Elf = CharacterRace(
        LanguageUtil.get("CHARACTER_RACE_ELF_NAME"),
        -2,
        10,
        2,
        arrayOf(CharacterClasses.Sorcerer),
        arrayOf()
    )
    val Dwarf = CharacterRace(
        LanguageUtil.get("CHARACTER_RACE_DWARF_NAME"),
        1,
        5,
        -1,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf(CharacterSkills.SpotSecretDoors)
    )
    val Gnome = CharacterRace(
        LanguageUtil.get("CHARACTER_RACE_GNOME_NAME"),
        -1,
        10,
        1,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf(CharacterSkills.DangerSense)
    )
    val HalfOrc = CharacterRace(
        LanguageUtil.get("CHARACTER_RACE_HALF_ORC_NAME"),
        2,
        -10,
        -2,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf()
    )
}