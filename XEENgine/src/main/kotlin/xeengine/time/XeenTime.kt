package xeengine.time

import xeengine.common.constants.time.TimeConstants.TIME_HOURS_PER_DAY
import xeengine.common.constants.time.TimeConstants.TIME_MINUTES_PER_HOUR
import xeengine.common.constants.time.TimeConstants.TIME_NUMBER_OF_DAYS
import xeengine.common.constants.time.TimeConstants.TIME_NUMBER_OF_MONTHS
import xeengine.common.constants.time.TimeConstants.TIME_NUMBER_OF_WEEKS

class XeenTime {
    companion object {
        var time: Long = 864000000

        fun getYear(): Int  {
            val daysInAYear = TIME_HOURS_PER_DAY *
                    TIME_MINUTES_PER_HOUR *
                    TIME_NUMBER_OF_MONTHS *
                    TIME_NUMBER_OF_WEEKS *
                    TIME_NUMBER_OF_DAYS

            val year = (time / daysInAYear).toInt()

            return year
        }

        fun getMonth(): Int {
            val daysInAMonth = TIME_HOURS_PER_DAY *
                    TIME_MINUTES_PER_HOUR *
                    TIME_NUMBER_OF_WEEKS *
                    TIME_NUMBER_OF_MONTHS

            val month = ((time / daysInAMonth).toInt()) % TIME_NUMBER_OF_MONTHS + 1

            return month
        }

        fun getWeek(): Int {
            val daysInWeek = TIME_HOURS_PER_DAY *
                    TIME_MINUTES_PER_HOUR *
                    TIME_NUMBER_OF_MONTHS

            return ((time / daysInWeek).toInt()) % TIME_NUMBER_OF_WEEKS + 1
        }

        fun getHours(): String {
            var result = ""

            var hours = ((time / TIME_MINUTES_PER_HOUR) % TIME_HOURS_PER_DAY).toInt() + 1
            val minutes = (time % TIME_MINUTES_PER_HOUR).toInt()

            if (hours > 12) {
                hours -= 12
                result = hours.toString() + ":" + String.format("%02d", minutes) + "PM"
            }
            result = hours.toString() + ":" + String.format("%02d", minutes) + "AM"

            return result
        }

        fun getDayOfMonth(): XeenDay {
            return XeenDays.days[((time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR)).toInt())%TIME_NUMBER_OF_DAYS]
        }
    }
}