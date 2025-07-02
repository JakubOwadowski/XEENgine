package xeengine.src.main.XeenTime

import xeengine.src.main.common.constants.time.TimeConstants.TIME_HOURS_PER_DAY
import xeengine.src.main.common.constants.time.TimeConstants.TIME_MINUTES_PER_HOUR
import xeengine.src.main.common.constants.time.TimeConstants.TIME_NUMBER_OF_DAYS
import xeengine.src.main.common.constants.time.TimeConstants.TIME_NUMBER_OF_MONTHS
import xeengine.src.main.common.constants.time.TimeConstants.TIME_NUMBER_OF_WEEKS

class XeenTime {
    companion object {
        var time: Long = 864000000

        fun getYear(): Int  {
            return (time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR * TIME_NUMBER_OF_MONTHS * TIME_NUMBER_OF_WEEKS * TIME_NUMBER_OF_DAYS)).toInt()
        }

        fun getMonth(): Int {
            return ((time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR * TIME_NUMBER_OF_WEEKS * TIME_NUMBER_OF_MONTHS)).toInt())%TIME_NUMBER_OF_MONTHS + 1
        }

        fun getWeek(): Int {
            return ((time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR * TIME_NUMBER_OF_MONTHS)).toInt())%TIME_NUMBER_OF_WEEKS + 1
        }

        fun getHours(): String {
            var hours = ((time / TIME_MINUTES_PER_HOUR) % TIME_HOURS_PER_DAY).toInt() + 1
            val minutes = (time % TIME_MINUTES_PER_HOUR).toInt()
            if (hours > 12) {
                hours -= 12
                return hours.toString() + ":" + String.format("%02d", minutes) + "PM"
            }
            return hours.toString() + ":" + String.format("%02d", minutes) + "AM"
        }

        fun getDayOfMonth(): XeenDay {
            return XeenDays.days[((time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR)).toInt())%TIME_NUMBER_OF_DAYS]
        }
    }
}