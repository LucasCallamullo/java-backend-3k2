
## Sistema de Logística de Transporte de Contenedores – Backend (TPI 2025)

La presente propuesta corresponde al Trabajo Práctico Integrador (TPI) de la asignatura Backend de Aplicaciones – Año 2025.
El objetivo general del proyecto es implementar una solución backend basada en microservicios, orientada a la gestión integral de un sistema de logística de transporte terrestre de contenedores utilizados para la construcción de viviendas.
En este escenario, el objeto de transporte es el contenedor en sí mismo, no su contenido.

[![Static Badge](https://img.shields.io/badge/Documentation-EN-blue)](https://github.com/LucasCallamullo/TPI-backend-3k2/blob/main/README.md) [![Documentation ES](https://img.shields.io/badge/Documentation-ES-green)](https://github.com/LucasCallamullo/TPI-backend-3k2/blob/main/README-ES.md)

### [ES]
[![Documentation ES](https://img.shields.io/badge/Documentation-ES-green)](https://github.com/LucasCallamullo/TPI-backend-3k2/blob/main/README-ES.md)

## 🛠️ Tecnologías Stack

### Backend
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)

### Database & ORM
![H2](https://img.shields.io/badge/H2_Database-0040CA?style=for-the-badge&logo=h2&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)

### Security
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

### Tools & DevOps
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Git Badge](https://img.shields.io/badge/git%20-%23F05033.svg?&style=for-the-badge&logo=git&logoColor=white) 
![GitHub Badge](https://img.shields.io/badge/github%20-%23121011.svg?&style=for-the-badge&logo=github&logoColor=white)

### Testing y Docs
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)


<br>
<h2> 👥 Información de Contacto </h2>
<a href="#contacto-section">Ir a Contacto ↓</a>
<br>

<h2>🐳 Despliegue con Docker</h2>

Todos los servicios están dockerizados y pueden levantarse mediante Docker Compose, incluyendo:
<ul> 
   <li>Microservicios (Clientes, Solicitudes, Logística)</li>
   <li>Gateway</li>
   <li>Keycloak</li>
   <li>Base de datos</li>
   <li>OSRM (con mapas preprocesados)</li>
   <li>Routing Service</li>
</ul>
Esto permite un entorno unificado, reproducible y listo para pruebas o despliegue.


## Arquitectura General

El sistema sigue una arquitectura moderna compuesta por múltiples microservicios independientes, cada uno responsable de un dominio específico:

### 🔹 Microservicio de Clientes

Gestión de usuarios finales, integración con Keycloak para autenticación y manejo de roles, creación y administración de clientes registrados en el sistema.

### 🔹 Microservicio de Solicitudes

Administración completa del ciclo de vida de una solicitud de transporte: creación, asignación de contenedores, estados, validaciones y tracking del proceso.

### 🔹 Microservicio de Logística

Gestión de rutas, tramos, estados logísticos y cálculo de costos estimados según distancias, consumos, tarifas y tipo de vehículo asignado.

### 🔹 Gateway API

Punto de entrada único para todas las aplicaciones frontend o clientes externos.
Encargado del enrutamiento y la comunicación hacia cada microservicio, además de validar los tokens JWT emitidos por Keycloak.

### 🔹 Servicio de Autenticación – Keycloak

El sistema utiliza Keycloak como proveedor de identidad y autenticación:
<ul> 
   <li>Gestión de usuarios</li>
   <li>Roles y permisos</li>
   <li>Emisión de tokens JWT</li>
   <li>Integración administrativa para creación automática de usuarios</li>
</ul>

### 🔹 Routing Service (OSRM)

Microservicio dedicado exclusivamente al cálculo de rutas y distancias en base a coordenadas geográficas (latitud/longitud).
Consume una instancia Docker de OSRM (Open Source Routing Machine) y soporta:
<ul> 
   <li>Ruta principal</li>
   <li>Rutas alternativas</li>
   <li>Cálculo de distancias y tiempos estimados</li>
   <li>Esto permite optimizar los costos y la logística del sistema.</li>
</ul>


### C4 Model

![](https://raw.githubusercontent.com/LucasCallamullo/TPI-backend-3k2/refs/heads/main/docs/img/c4_model.png)


### Diagramas de Relaciones de Entidades - DER

![](https://raw.githubusercontent.com/LucasCallamullo/TPI-backend-3k2/refs/heads/main/docs/img/der.png)


### Flujo de Autenticación:
![](https://raw.githubusercontent.com/LucasCallamullo/TPI-backend-3k2/refs/heads/main/docs/img/auth_flow.png)

#### Pasos Detallados del Flujo
1. **Inicio de Autenticación:** El usuario accede a la URL de autenticación en el navegador:
   ```bash
      http://localhost:8081/realms/tpi-backend/protocol/openid-connect/auth
         ?client_id=tpi-backend-client
         &response_type=code
         &redirect_uri=http://localhost:8080/api/login/oauth2/code/keycloak


2. **Interfaz de Login:** Keycloak presenta el formulario de autenticación donde el usuario ingresa sus credenciales (ej: cliente01 / 1234).

3. **Generación del Código de Autorización:** Tras validar las credenciales, Keycloak genera un código de autorización y redirige al Gateway:
   ```bash
      http://localhost:8080/api/login/oauth2/code/keycloak?code=ABC123XYZ
   
4. **Intercambio por Token JWT:** El AuthController del Gateway recibe el código y realiza una petición POST a Keycloak para intercambiarlo por un token JWT.

5. **Obtención del Token:** Keycloak responde con el JWT completo que contiene:
<ul> 
   <li>Información del usuario.</li>
   <li>Roles asignados (ROLE_CLIENTE, ROLE_ADMIN).</li>
   <li>Tiempo de expiración.</li>
   <li>Metadata de seguridad.</li>
</ul>


<br>

<h2 id="contacto-section"> 💻 Contacto Lucas Callamullo - Back-End Developer </h2>

| [![GitHub](https://img.shields.io/badge/github-%23121011.svg?&style=for-the-badge&logo=github&logoColor=white)](https://github.com/LucasCallamullo) | [![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/lucas-callamullo/) | [![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:lucas.callamullo.dev@gmail.com) |
|:-:|:-:|:-:|

| [![Portfolio](https://img.shields.io/badge/Portfolio-%23000000.svg?style=for-the-badge&logo=react&logoColor=white)](https://github.com/LucasCallamullo) | [![Youtube Badge](https://img.shields.io/badge/YouTube%20-%23FF0000.svg?&style=for-the-badge&logo=YouTube&logoColor=white)](https://www.youtube.com/@lucas_clases_python) |
|:-:|:-:|
