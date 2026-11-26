package com.financampus.app.presentation.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
open class AuthViewModel: ViewModel() {
    var busy by mutableStateOf(false); private set
    var message by mutableStateOf(""); private set
    var success by mutableStateOf(false); private set
    protected fun run(validation: String?, operation: suspend () -> Unit) {
        if (busy) return
        message = validation.orEmpty()
        if (validation != null) return
        busy = true
        viewModelScope.launch {
            try { operation(); success = true }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) { message = when(e) {
                is FirebaseNetworkException -> "Verifique sua conexão."
                is FirebaseAuthWeakPasswordException -> "Escolha uma senha mais forte."
                is FirebaseAuthUserCollisionException -> "Não foi possível criar a conta. Tente entrar ou recuperar a senha."
                else -> "Não foi possível concluir a operação. Confira os dados e tente novamente."
            } } finally { busy = false }
        }
    }
    protected fun validEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}
