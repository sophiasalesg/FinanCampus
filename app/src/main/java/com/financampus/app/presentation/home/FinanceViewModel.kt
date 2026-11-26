package com.financampus.app.presentation.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financampus.app.domain.model.*
import com.financampus.app.domain.usecase.finance.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import java.time.LocalDate
import java.util.UUID

class FinanceViewModel(
    private val userId: String,
    private val add: AddTransactionUseCase,
    private val get: GetTransactionsUseCase,
    private val update: UpdateTransactionUseCase,
    private val remove: DeleteTransactionUseCase
) : ViewModel() {

    var items by mutableStateOf<List<Transaction>>(emptyList())
        private set

    var busy by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    private val calculate = CalculateSummaryUseCase()

    fun summary(items: List<Transaction>) = calculate(items)

    init {
        refresh()
    }

    fun refresh() {
        if (busy) return
        busy = true
        error = ""

        viewModelScope.launch {
            try {
                items = get()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Nao foi possivel carregar as movimentacoes."
            } finally {
                busy = false
            }
        }
    }

    fun save(
        type: TransactionType,
        value: String,
        category: String,
        date: String,
        description: String,
        done: () -> Unit,
        editingId: String? = null
    ) {
        if (busy) return
        error = ""

        val cents = runCatching {
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

        val day = runCatching {
            LocalDate.parse(date)
        }.getOrNull()

        if (
            cents == null ||
            cents <= 0 ||
            cents > 100000000000L ||
            category.isBlank() ||
            category.length > 100 ||
            description.trim().length > 500 ||
            day == null ||
            day > LocalDate.now()
        ) {
            error = "Confira o valor, a categoria, a descricao e a data."
            return
        }

        val transaction = Transaction(
            editingId ?: UUID.randomUUID().toString(),
            userId,
            type,
            cents,
            category,
            description.trim(),
            day
        )

        busy = true

        viewModelScope.launch {
            try {
                if (editingId == null) {
                    add(transaction)
                } else {
                    update(transaction)
                }

                items = (
                    items.filterNot { it.id == transaction.id } +
                    transaction
                ).sortedByDescending { it.date }

                done()
                reloadAfterWrite()

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Nao foi possivel confirmar o salvamento."
            } finally {
                busy = false
            }
        }
    }

    fun delete(id: String) {
        if (busy) return
        busy = true
        error = ""

        viewModelScope.launch {
            try {
                remove(id)
                items = items.filterNot { it.id == id }
                reloadAfterWrite()

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                error = "Nao foi possivel excluir a movimentacao."
            } finally {
                busy = false
            }
        }
    }

    private suspend fun reloadAfterWrite() {
        try {
            items = get()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            error = "Operacao concluida. Atualize os dados."
        }
    }

    fun clearError() {
        error = ""
    }
}