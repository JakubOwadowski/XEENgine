package xeengine.src.main.characters

import visual.Map
import xeengine.src.main.utils.Coordinates

class Character {
    var name: String = ""
    var sex: CharacterSex = CharacterSex.Female
    var race: CharacterRace = CharacterRace.Human
    var characterClass: CharacterClass = CharacterClasses.Knight
    var stats = CharacterStats()
    var armourClassBonus: Int = 0
    var level: Int = 0
    var birthDay: Int = 0
    var birthYear: Int = 0
    var temporaryAge: Int = 0
    //skills
    //awards
    //spells
    var lloydMap: Map? = null
    var lloydCoordinates: Coordinates = Coordinates(0, 0)
    //currentspell
    //quickoption
    //items
    //  weapons
    //  armour
    //  accesories
    //  misc
    //resistances
    //conditions
    var hp: CharacterStat = CharacterStat()
    var sp: CharacterStat = CharacterStat()
}