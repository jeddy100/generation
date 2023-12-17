@echo off
setlocal

rem Définir les variables nécessaires
set "javaCmd=java"
set "jarPath=C:\Users\Jeddy\IdeaProjects\GenerationCode\generation\out\artifacts\generation_jar\generation.jar"

rem Définir les paramètres par défaut
set "database=generation"
set "output=C:\\"
set "user=postgres"
set "password=1234"
set "port=5432"
set "host=localhost"

rem Ajouter une variable pour spécifier le langage
set /p language="Entrez le langage (java/dotnet): "

rem Vérifier le langage et définir les variables en conséquence
if /i "%language%"=="java" (
    set "typefile=.java"
    set "template=class-template.ftl"
) else if /i "%language%"=="dotnet" (
    set "typefile=.cs"
    set "template=Net-template.ftl"
) else (
    echo Langage non pris en charge.
    exit /b 1
)

rem Ajouter des paramètres en ligne de commande pour définir les champs
set /p database="Entrez le nom de la base de données [%database%]: "
set /p output="Entrez le chemin de sortie [%output%]: "
set /p user="Entrez le nom d'utilisateur [%user%]: "
set /p password="Entrez le mot de passe [%password%]: "
set /p port="Entrez le port [%port%]: "
set /p host="Entrez l'hôte [%host%]: "

rem Appeler le fichier JAR
%javaCmd% -jar %jarPath% %typefile% %template% %database% %user% %password% %host% %port% %output%

endlocal

