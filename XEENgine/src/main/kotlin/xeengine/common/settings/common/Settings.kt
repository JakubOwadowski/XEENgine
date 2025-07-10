package xeengine.common.settings.common

import com.badlogic.gdx.Input
import xeengine.common.settings.enums.SettingCameraEnum
import xeengine.common.settings.enums.SettingLanguageEnum

class Settings {
    companion object {
        var language: SettingLanguageEnum = SettingLanguageEnum.ENGLISH
        var camera: SettingCameraEnum = SettingCameraEnum.FLUID
        var keyForward: Int = Input.Keys.W
        var keyBackward: Int = Input.Keys.S
        var keyMoveLeft: Int = Input.Keys.A
        var keyMoveRight: Int = Input.Keys.D
        var keyRotateLeft: Int = Input.Keys.Q
        var keyRotateRight: Int = Input.Keys.E
    }
}
