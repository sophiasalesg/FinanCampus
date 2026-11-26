$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$base = 'https://raw.githubusercontent.com/gradle/gradle/v8.9.0'
$files = @('gradlew','gradlew.bat','gradle/wrapper/gradle-wrapper.jar')
foreach ($file in $files) {
    Invoke-WebRequest -UseBasicParsing -Uri "$base/$file" -OutFile $file
}
Write-Host 'Wrapper instalado. Abra a pasta no Android Studio e sincronize o Gradle.'
