@echo off
setlocal

rem Définir les variables nécessaires
set "javaCmd=java"
set "jarPath=C:\Users\Jeddy\IdeaProjects\GenerationCode\generation\out\artifacts\generation_jar\generation.jar"

rem Définir les paramètres
set "database=generation"
set "typefilejava=.java"
set "typefileNet=.cs"

set "param3=valeur3"
set "templatejava=class-template.ftl"
set "templateNet=Net-template.ftl"
set "output=C:\\"
rem Appeler le fichier JAR
%javaCmd% -jar %jarPath% %typefileNet% %templateNet% %database% %output%

endlocal
