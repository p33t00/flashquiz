package di

import TestViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() = startKoin {
    modules(
        appModules
    )
}

val appModules = module {
    single { "hello" }

    factory {
        TestViewModel(get())
    }
}

