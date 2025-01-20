package xeengine.src.main.character.defined

import xeengine.src.main.character.primitives.CharacterRace
import xeengine.src.main.utils.LanguageUtil

object CharacterRaces {
    val Human = CharacterRace(
        LanguageUtil.get("character-race-human-name"),
        0,
        0,
        0,
        arrayOf(),
        arrayOf(CharacterSkills.Swimmer)
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
        arrayOf(CharacterSkills.SpotSecretDoors)
    )
    val Gnome = CharacterRace(
        LanguageUtil.get("character-race-gnome-name"),
        -1,
        10,
        1,
        arrayOf(CharacterClasses.Sorcerer, CharacterClasses.Cleric),
        arrayOf(CharacterSkills.DangerSense)
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