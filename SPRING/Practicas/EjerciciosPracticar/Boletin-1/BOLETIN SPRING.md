***BOLETÍN DE EJERCICIOS SPRING***

*“Estos primeros ejercicios se realizan tras haber superado la primera fase del documento Roadmap Spring”*.

**Ejercicio 1: El Refactor del "Code Smell" (Inyección de Dependencias)**  
Tiempo estimado: 40 minutos

*Contexto Real*: Llegas a una empresa y te encuentras con un código legacy (heredado). El desarrollador anterior abusó de la Inyección por Propiedad (Field Injection). Herramientas de análisis de código profesional como SonarQube marcan esto como un error grave porque dificulta las pruebas unitarias y oculta dependencias. Un servicio con 10 campos inyectados es menos evidente visualmente que un constructor con 10 argumentos, lo que a menudo enmascara violaciones del Principio de Responsabilidad Única.

**Objetivos:**

Crea una clase ProcesadorPagos (anotada con @Service).

Crea tres clases inyectables: ValidadorTarjeta, GatewayStripe y NotificadorEmail (todas con @Component).

El Error Intencionado (Anti-patrón): Inyecta estas tres dependencias en ProcesadorPagos utilizando la anotación @Autowired directamente sobre cada variable (Field Injection).

El Refactor (La Práctica Pro): Borra los @Autowired. Convierte las variables a private final para garantizar la inmutabilidad y la seguridad en entornos multihilo.

Utiliza Lombok (@RequiredArgsConstructor) para que genere la Inyección por Constructor automáticamente por detrás.

💡 Consejo del Tutor: Al usar Inyección por Constructor, si una clase empieza a tener demasiadas dependencias (ej. más de 7 parámetros), el propio constructor gigante te avisará visualmente de que la clase está asumiendo demasiadas responsabilidades y necesita ser dividida.

**Ejercicio 2: El Carrito de Compras Mágico (Ciclo de Vida y Scopes)**  
Tiempo estimado: 45 minutos

*Contexto Real*: Un error típico de Junior es pensar que cada vez que llamas a una clase en Spring, te da un objeto nuevo. En una tienda online, un desarrollador inyectó el CarritoCompras en el CheckoutService. Cuando el cliente 'A' añadía un producto, ¡le aparecía también al cliente 'B'\!

**Objetivo:**  
Crea una clase CarritoCompras con una lista List\<String\> productos. Al arrancar, usa la anotación @PostConstruct para imprimir: "🛒 Creando un nuevo carrito".

Anota esta clase como un @Component e inyéctala en una clase TiendaService (usa la inyección por constructor del Ejercicio 1).

El Error Intencionado: Llama dos veces al método para crear una compra. Verás que en la consola el mensaje "🛒 Creando un nuevo carrito" solo sale una vez, demostrando que Spring usa el patrón Singleton por defecto.

La Solución: Usa la anotación @Scope para cambiar el comportamiento del CarritoCompras al ámbito Prototype. Ejecuta de nuevo y comprueba cómo Spring ahora crea una instancia nueva cada vez que se requiere.

**Ejercicio 3: La Trampa de las Configuraciones (Propiedades)**  
Tiempo estimado: 45 minutos

*Contexto Real*: Las aplicaciones suelen depender de APIs externas. Es tentador usar @Value para inyectar configuraciones rápidamente. Sin embargo, en aplicaciones empresariales a gran escala, @Value depende de cadenas de texto y puede causar errores en tiempo de ejecución si la propiedad está mal escrita o no existe. Además, carece de validación de tipos nativa, por lo que el estándar actual es usar @ConfigurationProperties.

**Objetivo:**

En tu archivo application.properties, añade lo siguiente:

*Properties:*

- stripe.api.url=https://api.stripe.com/v1  
- stripe.api.timeout=5000  
- stripe.api.key=sk\_test\_12345


El Anti-patrón: Crea un @Service e intenta recuperar esos tres valores usando tres variables distintas anotadas con @Value("${stripe.api...}").

La Práctica Pro: Borra eso. Crea una clase POJO llamada StripeProperties.

Usa la anotación @ConfigurationProperties(prefix \= "stripe.api") en esa clase.

Inyecta StripeProperties en tu servicio a través de su constructor y accede a los datos.

💡 Consejo del Tutor: @ConfigurationProperties valida los datos al inicio de la aplicación y te da autocompletado en el IDE, previniendo que un error tipográfico tire tu aplicación en producción meses después.

**Ejercicio 4: Doble Vida (Manejo de Entornos con @Profile)**  
Tiempo estimado: 30 minutos

*Contexto Real*: En tu ordenador no quieres enviar emails de verdad ni conectarte a la base de datos de producción; quieres que el entorno local use servicios falsos (mocks).

**Objetivo:**

Renombra application.properties a application.yml (te acostumbrarás al estándar YAML, que es mucho más legible para jerarquías).

Crea dos archivos más: application-dev.yml y application-prod.yml.

Crea una interfaz DatabaseConnector con un método conectar().

Crea dos clases que la implementen: H2LocalDatabase (anotada con @Profile("dev")) y PostgresProdDatabase (anotada con @Profile("prod")).

Inyecta DatabaseConnector en tu aplicación principal y llama a conectar().

Juega en el application.yml principal con la propiedad spring.profiles.active=dev (y luego cámbialo a prod). Observa en la consola cómo Spring inyecta una clase completamente diferente sin que toques una sola línea del código Java.

**Ejercicio 5: La Dieta de las Clases (Dominando Lombok)**  
Tiempo estimado: 30 minutos

*Contexto Real:* Es muy habitual ver código sucio de desarrolladores que usan métodos manuales infinitos para crear objetos. También es común el grave error de poner la anotación @Data en entidades de bases de datos, lo cual puede desencadenar bucles infinitos por los métodos hashCode y equals ocultos.

**Objetivo:**

Crea una clase UsuarioConfiguracion con los campos: id, nombre, email, rol, activo.

No escribas getters, setters, ni constructores. Usa el poder de Lombok.

Agrégale las anotaciones pertinentes para convertirlo en un POJO inmutable (recomendado para DTOS): usa constructores y getters, pero evita que los campos puedan reescribirse tras su creación.

Usa la anotación @Builder de Lombok.

Ve a cualquier servicio que tengas, y crea una instancia de este objeto utilizando el patrón Builder encadenado (UsuarioConfiguracion.builder().nombre("...")...build();).

