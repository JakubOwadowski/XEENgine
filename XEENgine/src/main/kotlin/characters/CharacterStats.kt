package xeengine.src.main.characters

class CharacterStats {
    var might: CharacterStat = CharacterStat()
    var intellect: CharacterStat = CharacterStat()
    var personality: CharacterStat = CharacterStat()
    var endurance: CharacterStat = CharacterStat()
    var speed: CharacterStat = CharacterStat()
    var accuracy: CharacterStat = CharacterStat()
    var luck: CharacterStat = CharacterStat()

    fun greaterThen(other: CharacterStats): Boolean {
        return might.value > other.might.value && intellect.value > other.intellect.value && personality.value > other.personality.value && endurance.value > other.endurance.value && speed.value > other.speed.value && accuracy.value > other.accuracy.value && luck.value > other.luck.value
    }
}