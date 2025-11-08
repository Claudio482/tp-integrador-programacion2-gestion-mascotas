/* ===============================================================
   01_creacion.sql
   Trabajo Final Integrador - Programación 2
   Modelo: Dueño (independiente) -> Mascota (A) -> Microchip (B 1:1)
   =============================================================== */

-- FASE 1: Crear base de datos (idempotente)
CREATE DATABASE IF NOT EXISTS gestion_mascota
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE gestion_mascota;

-- FASE 2: Bloque de idempotencia
-- Deshabilitamos FKs para poder dropear en el orden correcto
SET FOREIGN_KEY_CHECKS = 0;

-- IMPORTANTE: primero las tablas hijas, luego las padres
DROP TABLE IF EXISTS microchip;
DROP TABLE IF EXISTS mascota;
DROP TABLE IF EXISTS duenio;

SET FOREIGN_KEY_CHECKS = 1;

-- ===============================================================
-- FASE 3: Tabla DUENIO (independiente)
-- ===============================================================
CREATE TABLE duenio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    telefono VARCHAR(30),
    email VARCHAR(120) UNIQUE,
    direccion VARCHAR(100),
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,

    -- Validaciones simples
    CONSTRAINT chk_duenio_email CHECK (email IS NULL OR email LIKE '%@%'),
    CONSTRAINT chk_duenio_telefono CHECK (telefono IS NULL OR LENGTH(telefono) >= 7)
);

-- Comentario:
--  - Esta tabla NO depende de nadie.
--  - dni único para búsquedas por campo clave (lo pide la cátedra).
--  - eliminado = baja lógica (no se borra físicamente).


-- ===============================================================
-- FASE 4: Tabla MASCOTA (Clase A)
--  - Depende de DUENIO
--  - Esta es la "A" en la relación A -> B
-- ===============================================================
CREATE TABLE mascota (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nombre VARCHAR(60) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(60),
    fecha_nacimiento DATE,
    duenio_id BIGINT NOT NULL,

    -- Relaciones
    CONSTRAINT fk_mascota_duenio
        FOREIGN KEY (duenio_id)
        REFERENCES duenio(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    -- Restricciones de calidad de datos
    CONSTRAINT chk_mascota_nombre CHECK (TRIM(nombre) <> '')
);

-- Comentario:
--  - ON DELETE RESTRICT: no puedo borrar un dueño si tiene mascotas.
--  - Esto es muy defendible en el informe.


-- ===============================================================
-- FASE 5: Tabla MICROCHIP (Clase B)
--  - Depende de MASCOTA
--  - Esta es la "B" en la relación 1:1 unidireccional
--  - 1:1 garantizado con UNIQUE(mascota_id)
-- ===============================================================
CREATE TABLE microchip (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    codigo VARCHAR(25) NOT NULL UNIQUE,
    observaciones TEXT,
    veterinaria VARCHAR(120),
    fecha_implantacion DATE,          -- >>> este es el campo que faltaba <<<
    mascota_id BIGINT NOT NULL UNIQUE, -- UNIQUE = una sola B por cada A

    -- Relaciones
    CONSTRAINT fk_microchip_mascota
        FOREIGN KEY (mascota_id)
        REFERENCES mascota(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    -- Restricciones
    CONSTRAINT chk_microchip_codigo CHECK (TRIM(codigo) <> '')
);

-- Comentario:
--  - UNIQUE(mascota_id) = exactamente lo que pide el TPI para 1→1. 
--  - ON DELETE CASCADE: si (en una prueba) se borra físicamente la mascota,
--    no queda un microchip "huérfano".
--  - fecha_implantacion DATE: igual que en las pautas oficiales.
