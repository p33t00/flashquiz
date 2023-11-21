package domain.lib

import domain.DateTime

actual class FcDateTime: DateTime {
    actual override fun nowMedium(): String {
        return "-"
    }
}