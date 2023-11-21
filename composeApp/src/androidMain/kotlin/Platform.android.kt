import android.os.Build
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat


class AndroidPlatform : Platform {
//    val a = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
//    val ct = System.currentTimeMillis()
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
