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
            val ticksPerMonth = TIME_MINUTES_PER_HOUR *
                    TIME_HOURS_PER_DAY *
                    TIME_NUMBER_OF_DAYS *
                    TIME_NUMBER_OF_WEEKS

            return ((time / ticksPerMonth).toInt()) % TIME_NUMBER_OF_MONTHS + 1
        }

        fun getWeek(): Int {
            val ticksPerWeek = TIME_MINUTES_PER_HOUR *
                    TIME_HOURS_PER_DAY *
                    TIME_NUMBER_OF_DAYS

            return ((time / ticksPerWeek).toInt()) % TIME_NUMBER_OF_WEEKS + 1
        }

        fun getHours(): String {
            val hour24 = ((time / TIME_MINUTES_PER_HOUR) % TIME_HOURS_PER_DAY).toInt()
            val minutes = (time % TIME_MINUTES_PER_HOUR).toInt()

            val isPM = hour24 >= 12
            val hour12 = when {
                hour24 == 0  -> 12           // midnight  → 12:xx AM
                hour24 <= 12 -> hour24       // 1–12      → 1–12
                else         -> hour24 - 12  // 13–23     → 1–11 PM
            }

            return "$hour12:${String.format("%02d", minutes)}${if (isPM) "PM" else "AM"}"
        }

        fun getDayOfMonth(): XeenDay {
            return XeenDays.days[((time / (TIME_HOURS_PER_DAY * TIME_MINUTES_PER_HOUR)).toInt())%TIME_NUMBER_OF_DAYS]
        }
    }
}