""" 
    Este archivo es solo para modificar y crear nuevos proyectos con maven

# NOTE Sirve para iniciar un nuevo proyecto al crear una nueva carpeta y movernos a la misma

mvn archetype:generate `
  "-DgroupId=com.ejemplo.alquileres" `
  "-DartifactId=alquiler-csv" `
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



# NOTE LOMBOK DEPENDENCY
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.32</version>
    <scope>provided</scope>
</dependency>



# NOTE
CREAR UN NUEVO PROYECTO

mvn archetype:generate `
  "-DgroupId=com.ejemplo" `
  "-DartifactId=lista-propia" `
  "-DarchetypeGroupId=org.apache.maven.archetypes" `
  "-DarchetypeArtifactId=maven-archetype-quickstart" `
  "-DinteractiveMode=false"

""" 




