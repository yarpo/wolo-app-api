$directoryPath = "./data"
if (Test-Path $directoryPath) {
    Remove-Item -Path $directoryPath -Recurse -Force
    Write-Host "Catalog $directoryPath removed."
    $imageToRemove = "wolo-app-api-backend"
    docker rmi -f $(docker images -q $imageToRemove)
    Write-Host "Image $imageToRemove removed."
} else {
    Write-Host "Catalog $directoryPath not exist."
}
    docker-compose --env-file .env --profile standalone down
    docker-compose --env-file .env --profile standalone up