package com.financampus.app.presentation.goals

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financampus.app.domain.model.FinancialGoal
import com.financampus.app.domain.usecase.goal.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class GoalsViewModel(
    private val userId: String,
    private val add: AddGoalUseCase,
    private val get: GetGoalsUseCase,
    private val update: UpdateGoalUseCase,
    private val remove: DeleteGoalUseCase,
    private val contribute: ContributeGoalUseCase
) : ViewModel() {

    var goals by mutableStateOf<List<FinancialGoal>>(emptyList())
        private set

    var busy by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    init {
        refresh()
    }

    fun clearError() {
        error = ""
    }

    private fun parseMoney(value: String): Long? =
        runCatching {
            require(
                value.trim().matches(
                    Regex("[0-9]+([,.][0-9]{1,2})?")
                )
            )

            value.trim()
                .replace(',', '.')
                .toBigDecimal()
                .movePointRight(2)
                .longValueExact()
        }.getOrNull()

    fun refresh() {
        if (busy) return

        busy = true
        error = ""

        viewModelScope.launch {
            try {
                goals = get()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Nao foi possivel carregar as metas."
            } finally {
                busy = false
            }
        }
    }

    private fun execute(
        operation: suspend () -> Unit,
        done: () -> Unit = {}
    ) {
        if (busy) return

        busy = true
        error = ""

        viewModelScope.launch {
            try {
                operation()
                done()

                try {
                    goals = get()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    error = "Operacao concluida. Atualize os dados."
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Nao foi possivel concluir a operacao."
            } finally {
                busy = false
            }
        }
    }

    fun save(
        title: String,
        description: String,
        target: String,
        deadline: String,
        editing: FinancialGoal?,
        done: () -> Unit
    ) {
        if (busy) return

        val cents = parseMoney(target)

        val date = runCatching {
            LocalDate.parse(deadline)
        }.getOrNull()

        val saved = editing?.savedInCents ?: 0L

        if (
            title.isBlank() ||
            title.trim().length > 100 ||
            description.trim().length > 500 ||
            cents == null ||
            cents !in 1L..100000000000L ||
            cents < saved ||
            date == null ||
            date.year !in 1..9999 ||
            (editing == null && date < LocalDate.now())
        ) {
            error = "Confira o titulo, valor e prazo da meta."
            return
        }

        val goal = FinancialGoal(
            id = editing?.id ?: UUID.randomUUID().toString(),
            userId = userId,
            title = title.trim(),
            description = description.trim(),
            targetInCents = cents,
            savedInCents = saved,
            deadline = date,
            createdAt = editing?.createdAt ?: LocalDate.now()
        )

        execute(
            operation = {
                if (editing == null) add(goal)
                else update(goal)
            },
            done = done
        )
    }

    fun delete(id: String, done: () -> Unit) {
        execute({ remove(id) }, done)
    }

    fun changeContribution(
        goal: FinancialGoal,
        value: String,
        withdraw: Boolean,
        done: () -> Unit
    ) {
        if (busy) return

        val cents = parseMoney(value)

        if (cents == null || cents <= 0L ||
            cents > 100000000000L
        ) {
            error = "Informe um valor positivo."
            return
        }

        val delta = if (withdraw) -cents else cents
        val updated = goal.savedInCents + delta

        if (updated !in 0L..goal.targetInCents) {
            error = "Valor fora do limite disponivel da meta."
            return
        }

        execute(
            { contribute(goal.id, delta) },
            done
        )
    }
}