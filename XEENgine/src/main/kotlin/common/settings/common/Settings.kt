package xeengine.src.main.common.settings.common

import com.badlogic.gdx.Input
import common.settings.enums.CameraEnum
import xeengine.src.main.common.settings.enums.SettingLanguageEnum

class Settings {
    companion object {
        var language: SettingLanguageEnum = SettingLanguageEnum.ENGLISH
        var camera: CameraEnum = CameraEnum.CLASSIC
        var keyForward: Int = Input.Keys.W
        var keyBackward: Int = Input.Keys.S
        var keyMoveLeft: Int = Input.Keys.A
        var keyMoveRight: Int = Input.Keys.D
        var keyRotateLeft: Int = Input.Keys.Q
        var keyRotateRight: Int = Input.Keys.E
    }
}
