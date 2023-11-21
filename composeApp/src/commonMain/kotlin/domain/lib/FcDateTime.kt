package domain.lib

import domain.DateTime

expect class FcDateTime() : DateTime {
    override fun nowMedium(): String
}