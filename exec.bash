# Inicializar Docker (ver como)
cd ./GUI
# Inicializar en segundo plano Sails Js
sails lift &
open http://localhost:1337
cd ../Tablero
# Hacer todo maven o ejecutar un jar
mvn compile
mvn test
mvn package
mvn deploy
# Ejecutar jar
