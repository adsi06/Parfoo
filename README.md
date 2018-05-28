# Parfoo
Programa para toma de decisiones de cripto-monedas

# Instrucciones
1. Ejecutar Front-End
* Verificar que se tenga instalado nodeJS y SailsJS:
```console
node -v
sails -v
```
  * En caso de que no, se pueden seguir las instrucciones de instalación de [SailsJS](https://sailsjs.com/get-started)
* Estar en la carpeta `./GUI`
* Si es la primera vez que se ejecuta esta acción, asegurarse de ejecutar `npm install`, con o sin sudo, dependiendo el caso
* Ejecutar el comando `sails lift`
* Abrir en un navegador la ip [http://localhost:1337](http://localhost:1337)
2. Ejecutar Back-End
* Abrir otra una terminal
* Estar en la carpeta `./Parfoo/`
* Ejecutar los comandos MVN:
```console
mvn compile
mvn test
mvn package
```
* Ir a la carpeta `./Parfoo/target`
* Ejecutar el comando `java -jar Parfoo-0.0.1-SNAPSHOT.jar`
* Ver la variación de los datos en el navegador

# Gestor de dependencias
[Dependabot](https://dependabot.com)
