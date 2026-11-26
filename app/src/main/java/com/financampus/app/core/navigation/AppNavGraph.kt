package com.financampus.app.core.navigation
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.*
import com.financampus.app.presentation.home.*
import com.financampus.app.presentation.goals.*
import com.financampus.app.domain.usecase.goal.*

import com.financampus.app.domain.usecase.finance.*
import com.financampus.app.core.di.AppContainer
import com.financampus.app.domain.usecase.auth.*
import com.financampus.app.presentation.auth.login.*
import com.financampus.app.presentation.auth.register.*
import com.financampus.app.presentation.auth.recovery.*
@Composable fun AppNavGraph() {
    val container = remember { AppContainer() }
    val nav = rememberNavController()
    val start = remember { if (container.auth.currentUser() != null) Routes.HOME.name else Routes.LOGIN.name }
    val home: () -> Unit = { nav.navigate(Routes.HOME.name) { popUpTo(Routes.LOGIN.name) { inclusive = true }; launchSingleTop = true } }
    NavHost(navController = nav, startDestination = start) {
        composable(Routes.LOGIN.name) {
            val vm: LoginViewModel = viewModel(factory = viewModelFactory { initializer { LoginViewModel(LoginUseCase(container.auth)) } })
            LoginScreen(vm, home, { nav.navigate(Routes.RECOVERY.name) }, { nav.navigate(Routes.REGISTER.name) })
        }
        composable(Routes.REGISTER.name) {
            val vm: RegisterViewModel = viewModel(factory = viewModelFactory { initializer { RegisterViewModel(RegisterUseCase(container.auth)) } })
            RegisterScreen(vm, home, { nav.popBackStack() })
        }
        composable(Routes.RECOVERY.name) {
            val vm: RecoveryViewModel = viewModel(factory = viewModelFactory { initializer { RecoveryViewModel(ResetPasswordUseCase(container.auth)) } })
            RecoveryScreen(vm, { nav.popBackStack() })
        }
        composable(Routes.HOME.name) {
            val user = container.auth.currentUser()
            if (user == null) {
                LaunchedEffect(Unit) { nav.navigate(Routes.LOGIN.name) { popUpTo(Routes.HOME.name) { inclusive = true } } }
            } else {
                val vm: FinanceViewModel = viewModel(key = user.id, factory = viewModelFactory { initializer {
                    val repository = container.transactions(user.id)
                    FinanceViewModel(
                        user.id,
                        AddTransactionUseCase(repository),
                        GetTransactionsUseCase(repository),
                        UpdateTransactionUseCase(repository),
                        DeleteTransactionUseCase(repository)
                    )
                } })
                val goalsVm: GoalsViewModel = viewModel(
                    key = "goals_${user.id}",
                    factory = viewModelFactory {
                        initializer {
                            val repository = container.goals(user.id)

                            GoalsViewModel(
                                user.id,
                                AddGoalUseCase(repository),
                                GetGoalsUseCase(repository),
                                UpdateGoalUseCase(repository),
                                DeleteGoalUseCase(repository),
                                ContributeGoalUseCase(repository)
                            )
                        }
                    }
                )

                FinanceScreen(vm, goalsVm) { container.auth.logout(); nav.navigate(Routes.LOGIN.name) { popUpTo(Routes.HOME.name) { inclusive = true } } }
            }
        }
    }
}
