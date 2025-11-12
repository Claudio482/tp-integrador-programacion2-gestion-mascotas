/* ===============================================================
   02_datos_prueba.sql
   Datos mínimos para probar DAOs y menú
   =============================================================== */
USE gestion_mascota;

-- Dueños
INSERT INTO duenio (dni, nombre, apellido, telefono, email, direccion)
VALUES
('30111222', 'María', 'González', '3493400111', 'maria.gonzalez@example.com', 'Sunchales 123'),
('28999888', 'Claudio', 'Fiorito', '3493400222', 'claudio.fiorito@example.com', 'Barrio Moreno 456');

-- Mascotas
INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id)
VALUES
(FALSE, 'Luna', 'Perro', 'Caniche', '2022-05-10', 1),
(FALSE, 'Michi', 'Gato', 'Siames', '2023-01-15', 2);

-- Microchips
INSERT INTO microchip (eliminado, codigo, observaciones, veterinaria, fecha_implantacion, mascota_id)
VALUES
(FALSE, 'CHIP-0001', 'Implantado sin complicaciones', 'Vet Sunchales', '2024-03-01', 1),
(FALSE, 'CHIP-0002', 'Control anual pendiente', 'Clínica Animal', '2024-06-12', 2);

SELECT * FROM microchip;
SELECT * FROM duenio;
SELECT * FROM mascota;