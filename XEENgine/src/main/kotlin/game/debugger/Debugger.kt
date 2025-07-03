package xeengine.src.main.game.debugger

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import player.common.Player
import xeengine.src.main.XeenTime.XeenTime
import xeengine.src.main.common.constants.global.GlobalDebugConstants.DEBUG_OFFSET_X
import xeengine.src.main.common.constants.global.GlobalDebugConstants.DEBUG_OFFSET_Y
import xeengine.src.main.common.constants.global.GlobalDebugConstants.DEBUG_TEXT_BORDER_COLOR
import xeengine.src.main.common.constants.global.GlobalDebugConstants.DEBUG_TEXT_COLOR
import xeengine.src.main.common.globals.Globals

class Debugger {
    private var debugFont: BitmapFont = BitmapFont()

    init {
        debugFont.color = DEBUG_TEXT_COLOR
    }

    fun drawDebug(spriteBatch: SpriteBatch, player: Player) {
        spriteBatch.begin()
        var debugText = "XEENgine v${Globals.get().version}\n" +
                "Player position:" +
                "X: ${player.getXZPosition().x}, " +
                "Z: ${player.getXZPosition().z}\n" +
                "Current time: ${XeenTime.time}" +
                "(Year: ${XeenTime.getYear()}, " +
                "Month: ${XeenTime.getMonth()}, " +
                "Week: ${XeenTime.getWeek()}, " +
                "Day: ${XeenTime.getDayOfMonth().number} - ${XeenTime.getDayOfMonth().name} ${XeenTime.getHours()})\n" +
                "Map: ${Globals.get().map.name}"

        val xPosition = DEBUG_OFFSET_X
        val yPosition = Gdx.graphics.height - DEBUG_OFFSET_Y

        debugFont.color = DEBUG_TEXT_BORDER_COLOR
        debugFont.draw(spriteBatch, debugText, xPosition - 1, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition + 1, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition - 1)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition + 1)

        debugFont.color = DEBUG_TEXT_COLOR
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition)
        spriteBatch.end()
    }

    fun dispose() {
        debugFont.dispose()
    }
}