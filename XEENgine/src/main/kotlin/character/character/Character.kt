package xeengine.src.main.character.character

import visual.Map
import xeengine.src.main.character.defined.CharacterClasses
import xeengine.src.main.character.defined.CharacterRaces
import xeengine.src.main.character.defined.CharacterSexes
import xeengine.src.main.character.defined.CharacterSkills
import xeengine.src.main.character.defined.CharacterStats
import xeengine.src.main.character.primitives.CharacterClass
import xeengine.src.main.character.primitives.CharacterRace
import xeengine.src.main.character.primitives.CharacterStat
import xeengine.src.main.utils.Coordinates

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