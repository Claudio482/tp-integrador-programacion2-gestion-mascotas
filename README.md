- Trabajo Integrador Final - Programación 2 (Gestión de Mascotas)

Este repositorio contiene el desarrollo del Trabajo Integrador Final (TIF) de la materia Programación 2 de la Tecnicatura Universitaria en Programación (UTN).

- Descripción

El proyecto consiste en el desarrollo de una aplicación de consola en Java que gestiona la relación entre dueños, mascotas y microchips, aplicando los principios de Programación Orientada a Objetos (POO), persistencia de datos con JDBC, y el patrón DAO.
Además, se implementa control transaccional (commit y rollback) para garantizar la integridad de los datos al registrar una mascota junto con su microchip.

El sistema simula la operatoria básica de una clínica veterinaria, permitiendo registrar dueños, mascotas y microchips de forma estructurada y segura.

 - Contenido del Repositorio
   Estructura General

src/
Contiene el código fuente completo del proyecto, organizado por paquetes:

gestionmascota.config: Configuración de la conexión a la base de datos (JDBC).

gestionmascota.entities: Clases de dominio (Dueno, Mascota, Microchip).

gestionmascota.dao: Clases DAO e interfaces genéricas para operaciones CRUD.

gestionmascota.service: Lógica de negocio, validaciones y control de transacciones.

gestionmascota.main: Clase principal con menú de consola interactivo.

sql/
- Contiene los scripts SQL necesarios para crear y probar la base de datos:

gestion_mascota.sql: Creación de la base de datos gestion_mascota, tablas, claves foráneas y restricciones.

02_datos_prueba.sql: Inserción de registros iniciales (dueños y mascotas de prueba).

informe/

Trabajo_Integrador_Final_Programacion2_.docx: Informe académico con objetivos, arquitectura, modelo de datos, transacciones, conclusiones.

README.md
Este archivo, que documenta el propósito, estructura y detalles del proyecto.

 -Funcionalidades Principales

Alta, búsqueda, listado y baja lógica de dueños.

Alta, búsqueda y listado de mascotas.

Asociación 1:1 entre Mascota y Microchip, con alta transaccional (rollback ante error).

Validaciones de integridad (DNI único, código de microchip único).

Persistencia mediante JDBC con MySQL.

Interfaz por consola con menú interactivo.

 - Tecnologías Utilizadas
Tecnología	Descripción
Java 	Lenguaje de programación principal
MySQL 8	Motor de base de datos relacional
JDBC	Conexión directa entre Java y MySQL
NetBeans 21	Entorno de desarrollo integrado
Git / GitHub	Control de versiones y repositorio remoto

 - Modelo de Datos

El modelo de datos relacional se compone de tres entidades principales:

Dueno 1 ─── N Mascota 1 ─── 1 Microchip


Dueno: contiene los datos personales del propietario.

Mascota: registra información de la mascota (nombre, especie, raza, fecha de nacimiento).

Microchip: contiene el código identificatorio único y la información de implantación.

- Estructura de Paquetes
gestionmascota/
 
 config/
     
     ConeccionBD.java
 
 entities/
    
     Dueno.java
   
     Mascota.java
     
     Microchip.java
  
  dao/
     
     CrudDao.java
    
     DuenoDao.java
     
     DuenoDaoMySQL.java
    
     MascotaDao.java
     
     MascotaDaoMySQL.java
    
     MicrochipDao.java
    
     MicrochipDaoMySQL.java
 
 service/
     DuenoService.java
     MascotaService.java
     ServiceException.java
  
  main/
      
      AppMain.java

 - Menú Principal (Interfaz de Consola)

     GESTION DE DUEÑOS Y MASCOTAS

1) Crear dueño
2) Listar dueños
3) Buscar dueño por DNI
4) Crear mascota + microchip
5) Listar mascotas
6) Buscar mascotas por nombre
0) Salir

 - Informe Académico

El informe incluye:

Objetivo General y Fundamentación

Arquitectura del Sistema (por capas)

Modelo de Datos (Entidad-Relación y UML)

Implementación del Patrón DAO

Control de Transacciones (commit/rollback)

Pruebas de Validación y Ejecución del Programa

Conclusiones Finales

 - Autores

Diana Falla (diana.falla.cba@gmail.com) 

Claudio Fiorito (Claudio80.cf@gmail.com) 

 - Contexto Académico

Universidad: Universidad Tecnológica Nacional (UTN)

Carrera: Tecnicatura Universitaria en Programación

Materia: Programación 2

Docente Titular: Alberto Cortez

Año: 2025

 - Enlaces

Video Demostrativo (YouTube):
[Enlace]

Software Utilizado:

Java SE 17

MySQL

NetBeans 21

GitHub
