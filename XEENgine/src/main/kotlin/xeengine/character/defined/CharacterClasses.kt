package xeengine.character.defined

import xeengine.character.primitives.CharacterClass
import xeengine.utils.LanguageUtil

object CharacterClasses {
    val Knight = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_KNIGHT_NAME"),
        CharacterStats().apply {
            this.might.value = 15
        },
        10,
        5,
        arrayOf(CharacterSkills.ArmsMaster),
        false
    )
    val Paladin = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_PALADIN_NAME"),
        CharacterStats().apply {
            this.might.value = 13
            this.personality.value = 13
            this.endurance.value = 13
        },
        8,
        6,
        arrayOf(CharacterSkills.Crusader),
        true
    )
    val Archer = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_ARCHER_NAME"),
        CharacterStats().apply {
            this.intellect.value = 13
            this.accuracy.value = 13
        },
        7,
        6,
        emptyArray(),
        true
    )
    val Cleric = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_CLERIC_NAME"),
        CharacterStats().apply {
            this.personality.value = 13
        },
        5,
        7,
        emptyArray(),
        true
    )
    val Sorcerer = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_SORCERER_NAME"),
        CharacterStats().apply {
            this.intellect.value = 13
        },
        4,
        8,
        arrayOf(CharacterSkills.Cartographer),
        true
    )
    val Robber = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_ROBBER_NAME"),
        CharacterStats().apply {
            this.luck.value = 13
        },
        8,
        6,
        arrayOf(CharacterSkills.Thievery),
        false,
        30,
        2
    )
    val Ninja = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_NINJA_NAME"),
        CharacterStats().apply {
            this.speed.value = 13
            this.accuracy.value = 13
        },
        7,
        5,
        arrayOf(CharacterSkills.Thievery),
        false,
        30,
        2
    )
    val Barbarian = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_BARBARIAN_NAME"),
        CharacterStats().apply {
            this.endurance.value = 15
        },
        12,
        4,
        emptyArray(),
        false
    )
    val Druid = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_DRUID_NAME"),
        CharacterStats().apply {
            this.intellect.value = 15
            this.personality.value = 15
        },
        6,
        7,
        arrayOf(CharacterSkills.DirectionSense),
        true
    )
    val Ranger = CharacterClass(
        LanguageUtil.get("CHARACTER_CLASS_RANGER_NAME"),
        CharacterStats().apply {
            this.intellect.value = 12
            this.personality.value = 12
            this.endurance.value = 12
            this.speed.value = 12
        },
        9,
        6,
        arrayOf(CharacterSkills.Pathfinder),
        true
    )
}