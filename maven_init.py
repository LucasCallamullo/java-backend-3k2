""" 
    Este archivo es solo para modificar y crear nuevos proyectos con maven

# NOTE Sirve para iniciar un nuevo proyecto al crear una nueva carpeta y movernos a la misma

mvn archetype:generate `
  "-DgroupId=museo.arte" `
  "-DartifactId=museo-arte" `
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
  "-DgroupId=parcial-juegos" `
  "-DartifactId=juegos-uno" `
  "-DarchetypeGroupId=org.apache.maven.archetypes" `
  "-DarchetypeArtifactId=maven-archetype-quickstart" `
  "-DinteractiveMode=false"



# NOTE para hacer push a git

git add .
git commit -m "Pre enunciado base"
git push origin main

""" 




# NOTE para h2 Embebido

""" 
<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- JPA (Jakarta Persistence API) -->
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- Hibernate (implementación de JPA) -->
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>6.4.4.Final</version>
</dependency>

"""



# NOTE POM:XML NUEVO MODERNO LINDO BONITO PRECIOSO
"""
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>

  <groupId>museo.arte</groupId>
  <artifactId>museo-arte</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencyManagement>
    <dependencies>
      <!-- JUnit BOM (gestiona todas las versiones de JUnit 5) -->
      <dependency>
        <groupId>org.junit</groupId>
        <artifactId>junit-bom</artifactId>
        <version>5.11.0</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <!-- JUnit 5 -->
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <scope>test</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.32</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <!-- Necesario para correr JUnit 5 -->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>
    </plugins>
  </build>
</project>
"""



appContext = {
  
    "path": "resources/files/",
    "JuegoService": JuegoService(),
    "GeneroService": GeneroService(),
    "scanner": Scanner(),
    
    
}



options = {
    1: Actions()
    
}