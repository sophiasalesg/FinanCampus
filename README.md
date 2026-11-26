# FinanCampus — base Android

Projeto Gradle com Kotlin, Compose, Clean Architecture e modelos/contratos da base anterior.

## Windows
1. Extraia o ZIP. Abra PowerShell na pasta que contém settings.gradle.kts.
2. Execute `powershell -ExecutionPolicy Bypass -File .\Preparar-Gradle.ps1` para obter os três arquivos oficiais do wrapper Gradle 8.9. Requer internet.
3. Android Studio > Open > selecione esta pasta (não app). Use Gradle JDK 17 e instale Android SDK 35 pelo SDK Manager se necessário. Sincronize.
4. Execute em emulador ou aparelho Android API 26+.

Alternativa: configure uma distribuição local Gradle 8.9 nas configurações do IDE.

## Escopo
Tela inicial de validação, tema e domínio. As sete telas, ViewModels, implementações de repositório, autenticação e Firestore ainda não estão implementados. Nenhum dado financeiro real deve ser inserido nesta versão. Não compilado neste ambiente (sem SDK/Gradle e acesso à rede).

## Git
Clone o repositório compartilhado primeiro e copie os arquivos para a raiz clonada, revisando conflitos. Não substitua .git. Após validar, crie uma branch e faça commit. Não publique local.properties ou credenciais.
