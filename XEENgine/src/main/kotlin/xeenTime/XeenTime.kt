package xeengine.src.main.XeenTime

import xeengine.src.main.constants.TimeConstants.HOURS_PER_DAY
import xeengine.src.main.constants.TimeConstants.MINUTES_PER_HOUR
import xeengine.src.main.constants.TimeConstants.NUMBER_OF_DAYS
import xeengine.src.main.constants.TimeConstants.NUMBER_OF_MONTHS
import xeengine.src.main.constants.TimeConstants.NUMBER_OF_WEEKS

class XeenTime {
    companion object {
        var time: Long = 864000000

        fun getYear(): Int  {
            return (time / (HOURS_PER_DAY * MINUTES_PER_HOUR * NUMBER_OF_MONTHS * NUMBER_OF_WEEKS * NUMBER_OF_DAYS)).toInt()
        }

        fun getMonth(): Int {
            return ((time / (HOURS_PER_DAY * MINUTES_PER_HOUR * NUMBER_OF_WEEKS * NUMBER_OF_MONTHS)).toInt())%NUMBER_OF_MONTHS + 1
        }

        fun getWeek(): Int {
            return ((time / (HOURS_PER_DAY * MINUTES_PER_HOUR * NUMBER_OF_MONTHS)).toInt())%NUMBER_OF_WEEKS + 1
        }

        fun getHours(): String {
            var hours = ((time / MINUTES_PER_HOUR) % HOURS_PER_DAY).toInt() + 1
            val minutes = (time % MINUTES_PER_HOUR).toInt()
            if (hours > 12) {
                hours -= 12
                return hours.toString() + ":" + String.format("%02d", minutes) + "PM"
            }
            return hours.toString() + ":" + String.format("%02d", minutes) + "AM"
        }

        fun getDayOfMonth(): XeenDay {
            return XeenDays.days[((time / (HOURS_PER_DAY * MINUTES_PER_HOUR)).toInt())%NUMBER_OF_DAYS]
        }
    }
}