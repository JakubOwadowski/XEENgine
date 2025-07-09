package xeengine.game.debugger

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable
import xeengine.player.common.Player
import xeengine.time.XeenTime
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_BORDER_WIDTH
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_OFFSET_X
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_OFFSET_Y
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_TEXT_BORDER_COLOR
import xeengine.common.constants.global.GlobalDebugConstants.DEBUG_TEXT_COLOR
import xeengine.common.globals.Globals

class Debugger: Disposable {
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
        debugFont.draw(spriteBatch, debugText, xPosition - DEBUG_BORDER_WIDTH, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition + DEBUG_BORDER_WIDTH, yPosition)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition - DEBUG_BORDER_WIDTH)
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition + DEBUG_BORDER_WIDTH)

        debugFont.color = DEBUG_TEXT_COLOR
        debugFont.draw(spriteBatch, debugText, xPosition, yPosition)
        spriteBatch.end()
    }

    override fun dispose() {
        debugFont.dispose()
    }
}