# Nazwa skryptu: RemoveAndUp.ps1

# Ścieżka do katalogu, który chcesz usunąć rekurencyjnie
$directoryPath = "./data"

# Sprawdź, czy katalog istnieje przed próbą usunięcia
if (Test-Path $directoryPath) {
    # Usuń katalog rekurencyjnie
    Remove-Item -Path $directoryPath -Recurse -Force
    Write-Host "Katalog $directoryPath został pomyślnie usunięty."

    # Usuń tylko obraz "wolo-app-api-backend"
    $imageToRemove = "wolo-app-api-backend"
    docker rmi -f $(docker images -q $imageToRemove)
    Write-Host "Obraz $imageToRemove został pomyślnie usunięty."

    # Uruchom Docker Compose
    docker-compose up -d
    Write-Host "Docker Compose został pomyślnie uruchomiony."
} else {
    Write-Host "Katalog $directoryPath nie istnieje."
}