package xeengine.src.main.characters

import xeengine.src.main.utils.LanguageUtil

object CharacterClasses {
    val Knight = CharacterClass(
        LanguageUtil.get("character-class-knight-name"),
        CharacterStats().apply {
            this.might.value = 15
        },
        10,
        5,
        arrayOf(CharacterSkill.ArmsMaster),
        false
    )
    val Paladin = CharacterClass(
        LanguageUtil.get("character-class-paladin-name"),
        CharacterStats().apply {
            this.might.value = 13
            this.personality.value = 13
            this.endurance.value = 13
        },
        8,
        6,
        arrayOf(CharacterSkill.Crusader),
        true
    )
    val Archer = CharacterClass(
        LanguageUtil.get("character-class-archer-name"),
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
        LanguageUtil.get("character-class-cleric-name"),
        CharacterStats().apply {
            this.personality.value = 13
        },
        5,
        7,
        emptyArray(),
        true
    )
    val Sorcerer = CharacterClass(
        LanguageUtil.get("character-class-sorcerer-name"),
        CharacterStats().apply {
            this.intellect.value = 13
        },
        4,
        8,
        arrayOf(CharacterSkill.Cartographer),
        true
    )
    val Robber = CharacterClass(
        LanguageUtil.get("character-class-robber-name"),
        CharacterStats().apply {
            this.luck.value = 13
        },
        8,
        6,
        arrayOf(CharacterSkill.Thievery),
        false,
        30,
        2
    )
    val Ninja = CharacterClass(
        LanguageUtil.get("character-class-ninja-name"),
        CharacterStats().apply {
            this.speed.value = 13
            this.accuracy.value = 13
        },
        7,
        5,
        arrayOf(CharacterSkill.Thievery),
        false,
        30,
        2
    )
    val Barbarian = CharacterClass(
        LanguageUtil.get("character-class-barbarian-name"),
        CharacterStats().apply {
            this.endurance.value = 15
        },
        12,
        4,
        emptyArray(),
        false
    )
    val Druid = CharacterClass(
        LanguageUtil.get("character-class-druid-name"),
        CharacterStats().apply {
            this.intellect.value = 15
            this.personality.value = 15
        },
        6,
        7,
        arrayOf(CharacterSkill.DirectionSense),
        true
    )
    val Ranger = CharacterClass(
        LanguageUtil.get("character-class-ranger-name"),
        CharacterStats().apply {
            this.intellect.value = 12
            this.personality.value = 12
            this.endurance.value = 12
            this.speed.value = 12
        },
        9,
        6,
        arrayOf(CharacterSkill.Pathfinder),
        true
    )
}