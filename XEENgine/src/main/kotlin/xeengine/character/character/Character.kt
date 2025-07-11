package xeengine.character.character

import xeengine.visual.primitives.GameMap
import xeengine.character.defined.CharacterClasses
import xeengine.character.defined.CharacterRaces
import xeengine.character.defined.CharacterSexes
import xeengine.character.defined.CharacterSkills
import xeengine.character.defined.CharacterStats
import xeengine.character.primitives.CharacterClass
import xeengine.character.primitives.CharacterRace
import xeengine.character.primitives.CharacterStat
import xeengine.utils.Coordinates

class Character {
    var name: String = ""
    var sex: CharacterSexes = CharacterSexes.Female
    var race: CharacterRace = CharacterRaces.Human
    var characterClass: CharacterClass = CharacterClasses.Knight
    var stats = CharacterStats()
    var armourClassBonus: Int = 0
    var level: Int = 0
    var birthDay: Int = 0
    var birthYear: Int = 0
    var temporaryAge: Int = 0
    var skills: MutableList<CharacterSkills> = ArrayList()
    //awards
    //spells
    var lloydMap: GameMap? = null
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