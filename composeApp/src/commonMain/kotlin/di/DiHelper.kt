package di

import DBDriverFactory
import TestViewModel
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import org.koin.dsl.module

val appModules = module {
    single { "hello" }

    single {
        val driverFactory = DBDriverFactory(get())
        val driver = driverFactory.createDriver()
        FlashCardsDB(driver)
    }

    factory {
        TestViewModel(get())
    }
}

