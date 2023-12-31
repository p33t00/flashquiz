package di

import DBDriverFactory
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import domain.LocalDataSource
import domain.data.SqlDelightLocalDataSource
import org.koin.dsl.module
import ui.screens.CreateQuizViewModel
import ui.screens.LoginViewModel
import ui.screens.QuizListViewModel
import ui.screens.QuizStatsViewModel
import ui.screens.QuizViewModel
import ui.screens.SignupViewModel

val appModules = module {
    single {
        val driverFactory = DBDriverFactory(get())
        val driver = driverFactory.createDriver()
        FlashCardsDB(driver)
    }

    single<LocalDataSource> {
        SqlDelightLocalDataSource(get())
    }

    factory {
        QuizListViewModel(get())
    }

    factory {
        QuizViewModel(get())
    }

    factory { (quizId: Int) ->
        QuizStatsViewModel(quizId, get())
    }

    factory {(quizId: Int) ->
        CreateQuizViewModel(get(), quizId)
    }

    factory {
        LoginViewModel()
    }

    factory {
        SignupViewModel()
    }
}

