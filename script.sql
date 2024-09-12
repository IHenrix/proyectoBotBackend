DROP DATABASE IF EXISTS DB_PROYECTO;
-- Crear la base de datos
CREATE DATABASE DB_PROYECTO;
-- Seleccionar la base de datos recién creada
USE DB_PROYECTO;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

-- Tabla de roles
CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion  VARCHAR(255) NOT NULL,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE usuario_rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    rol_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido_paterno VARCHAR(255) NOT NULL,
    apellido_materno VARCHAR(255) NOT NULL,
    sexo VARCHAR(1) NOT NULL,
    codigo VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefono VARCHAR(255),
    carrera VARCHAR(250),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_USER', 'ALUMNO');
INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_DOCENTE', 'DOCENTE');
INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_ADMIN', 'ADMINISTRADOR');

INSERT INTO usuario (username, password, enabled) VALUES ('75911772', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);
INSERT INTO usuario (username, password, enabled) VALUES ('72274736', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (2, 1);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, carrera, email, telefono, id_usuario
) 
VALUES (
    'RICARDO ENRIQUE', 'PRADA', 'GUERRA', 'M', '2024141514', 'Ingeniería de Software', 'enrique.pdg@gmail.com', '912016161', 1
);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, carrera, email, telefono, id_usuario
) 
VALUES (
    'GRACIA ANTUANETTE', 'MARCA', 'TORRES', 'F', '2019104010', 'Ingeniería de Computación y Sistemas', 'gracia.marcag@gmail.com', '913256561', 2
);
CREATE TABLE tipo_archivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);
INSERT INTO tipo_archivo (nombre) VALUES ('GUÍA');

CREATE TABLE archivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    documento BLOB NOT NULL,
    descripcion VARCHAR(255) NULL,
    tipo VARCHAR(255) NULL,
	id_tipo_archivo INT NOT NULL,
    FOREIGN KEY (id_tipo_archivo) REFERENCES tipo_archivo(id)
);


-- SCRIPTS
SELECT 
    *
FROM 
    persona a
JOIN 
    usuario u ON a.id_usuario = u.id
WHERE 
    u.username = '75911772';

SELECT p.nombre, p.apellido_paterno, p.apellido_materno, p.sexo, p.codigo, p.email, 
p.telefono, p.carrera 
FROM persona p 
INNER JOIN usuario u ON p.id_usuario = u.id 
WHERE EXISTS (SELECT 1 FROM usuario_rol ur WHERE ur.usuario_id = u.id);


