# Diagramas UML - Grupo10_ProyectoFinal_Spa

Este directorio contiene los diagramas UML del sistema de gestión de Spa.

## Archivos Disponibles

1. **UML_Diagrama.puml** - Diagrama completo en formato PlantUML
2. **UML_Diagrama_Simplificado.md** - Diagrama simplificado en formato Mermaid

## Cómo Visualizar los Diagramas

### Opción 1: PlantUML (Recomendado para diagrama completo)

El archivo `UML_Diagrama.puml` puede visualizarse usando:

#### Opción A: Online (Más fácil)
1. Visita http://www.plantuml.com/plantuml/uml/
2. Copia y pega el contenido de `UML_Diagrama.puml`
3. El diagrama se generará automáticamente
4. Puedes exportarlo como PNG, SVG o PDF

#### Opción B: Extensión de VS Code
1. Instala la extensión "PlantUML" en VS Code
2. Abre el archivo `UML_Diagrama.puml`
3. Presiona `Alt+D` o haz clic derecho → "Preview PlantUML"

#### Opción C: Plugin de IntelliJ IDEA / NetBeans
1. Instala el plugin PlantUML
2. Abre el archivo `.puml`
3. El diagrama se mostrará automáticamente

#### Opción D: Herramienta de línea de comandos
```bash
# Instalar PlantUML (requiere Java)
# Descargar desde: http://plantuml.com/download

# Generar imagen PNG
java -jar plantuml.jar UML_Diagrama.puml

# Generar imagen SVG
java -jar plantuml.jar -tsvg UML_Diagrama.puml
```

### Opción 2: Mermaid (Para diagrama simplificado)

El archivo `UML_Diagrama_Simplificado.md` contiene diagramas en formato Mermaid que pueden visualizarse:

#### Opción A: GitHub / GitLab
- Si subes el archivo a GitHub o GitLab, los diagramas Mermaid se renderizan automáticamente

#### Opción B: Online Editor
1. Visita https://mermaid.live/
2. Copia el código Mermaid del archivo `.md`
3. El diagrama se generará automáticamente

#### Opción C: Extensión de VS Code
1. Instala la extensión "Markdown Preview Mermaid Support"
2. Abre el archivo `.md`
3. Usa la vista previa de Markdown

## Estructura del Diagrama

### Entidades Principales

- **Cliente**: Información de los clientes del spa
- **Empleado**: Clase base para empleados
- **Masajista**: Especialización de empleado (masajistas)
- **Vendedor**: Especialización de empleado (vendedores)
- **DiaDeSpa**: Paquete de sesiones para un cliente
- **Sesion**: Sesión individual de tratamiento
- **Tratamiento**: Tipos de tratamientos ofrecidos
- **Consultorio**: Salas donde se realizan los tratamientos
- **Producto**: Productos utilizados en tratamientos o vendidos
- **Equipamiento**: Equipos disponibles en consultorios
- **Instalacion**: Instalaciones de agua y relajación

### Relaciones Clave

1. Un **Cliente** puede tener múltiples **DiaDeSpa**
2. Un **DiaDeSpa** contiene múltiples **Sesion**
3. Cada **Sesion** utiliza un **Tratamiento**, se realiza en un **Consultorio**, es atendida por un **Masajista**, y puede usar múltiples **Instalacion**
4. Un **Tratamiento** puede requerir múltiples **Producto**
5. Un **Consultorio** tiene múltiples **Equipamiento**
6. **Masajista** y **Vendedor** referencian a **Empleado**

### Arquitectura en Capas

El sistema sigue una arquitectura en capas:

1. **Capa de Presentación (Vistas)**: Interfaces Swing
2. **Capa de Control**: Controladores que manejan la lógica de negocio
3. **Capa de Entidades**: Modelo de dominio
4. **Capa de Persistencia**: Acceso a base de datos (Data Access Objects)

## Notas

- Los colores en el diagrama PlantUML ayudan a distinguir las diferentes capas:
  - Azul claro: Entidades
  - Naranja claro: Controladores
  - Verde claro: Vistas
  - Púrpura claro: Persistencia

- Las relaciones muestran cardinalidades (1, *, etc.) para indicar cuántos objetos de cada tipo pueden relacionarse.

## Autor

Grupo 10 - Proyecto Final Spa
- Altamirano Karina
- Gianfranco Antonacci Matías
- Bequis Marcos Ezequiel
