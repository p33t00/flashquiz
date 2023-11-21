package di

import DBDriverFactory
import TestViewModel
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import domain.LocalDataSource
import domain.data.SqlDelightLocalDataSource
import org.koin.dsl.module
import ui.screens.QuizListViewModel
import ui.screens.QuizViewModel

val appModules = module {
    single { "hello" }

    single {
        val driverFactory = DBDriverFactory(get())
        val driver = driverFactory.createDriver()
        FlashCardsDB(driver)
    }

    single<LocalDataSource> {
        SqlDelightLocalDataSource(get())
    }

    factory {
        TestViewModel(get())
    }

    factory {
        QuizListViewModel(get())
    }

    factory {
        QuizViewModel(get())
    }
}

