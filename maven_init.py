""" 
    Este archivo es solo para modificar y crear nuevos proyectos con maven

# NOTE Sirve para iniciar un nuevo proyecto al crear una nueva carpeta y movernos a la misma

mvn archetype:generate `
  "-DgroupId=listas.menu" `
  "-DartifactId=listas-menu" `
  "-DarchetypeGroupId=org.apache.maven.archetypes" `
  "-DarchetypeArtifactId=maven-archetype-quickstart" `
  "-DinteractiveMode=false"


# NOTE Configura el archivo pom.xl para utilizar mvn java:exec
# NOTE (Mirar bien el path que ponemos en mainClass)
# PLUGIN MAVEN

<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>3.5.0</version>
  <configuration>
    <mainClass>com.ejemplo.App</mainClass>
  </configuration>
</plugin>



# NOTE LOMBOK DEPENDENCY   - o version 1.18.30
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.32</version>
    <scope>provided</scope>
</dependency>



# NOTE
CREAR UN NUEVO PROYECTO

mvn archetype:generate `
  "-DgroupId=cafeteria" `
  "-DartifactId=cafeteria-menuMap-csv" `
  "-DarchetypeGroupId=org.apache.maven.archetypes" `
  "-DarchetypeArtifactId=maven-archetype-quickstart" `
  "-DinteractiveMode=false"



# NOTE para hacer push a git

git add .
git commit -m "Agregado Menu de opciones generico."
git push origin main

""" 




