package domain.lib

import domain.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

actual class FcDateTime actual constructor() : DateTime {
    actual override fun nowMedium(): String {
        return LocalDateTime.now().toString(DateTimeFormat.mediumDateTime())
    }
}