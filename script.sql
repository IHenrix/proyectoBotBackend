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
CREATE TABLE carrera (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
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
    id_carrera INT,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_carrera) REFERENCES carrera(id)

);

INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_USER', 'ALUMNO');
INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_DOCENTE', 'DOCENTE');
INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_ADMIN', 'ADMINISTRADOR');

INSERT INTO usuario (username, password, enabled) VALUES ('75911772', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);
INSERT INTO usuario (username, password, enabled) VALUES ('72274736', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);
INSERT INTO usuario (username, password, enabled) VALUES ('75249647', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);
INSERT INTO usuario (username, password, enabled) VALUES ('75911773', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (3, 3);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (4, 1);

INSERT INTO carrera (nombre) VALUES ('Ingeniería de Computación y Sistemas');
INSERT INTO carrera (nombre) VALUES ('Ingeniería de Software');

INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'RICARDO ENRIQUE', 'PRADA', 'GUERRA', 'M', '2024141514', 2, 'enrique.pdg@gmail.com', '912016161', 1
);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'GRACIA ANTUANETTE', 'MARCA', 'TORRES', 'F', '2019104010', 1, 'gracia.marcag@gmail.com', '913256561', 2
);

INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'ALEXANDRA STEFANNY', 'PALOMINO', 'RUIZ', 'F', '2019110010', 1, 'apolominorg@gmail.com', '913256561', 3
);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'ROBERT D JESÚS', 'PAREDES', 'GARCIA', 'M', '2020104010', 1, 'generico@gmail.com', '913256561', 4
);

CREATE TABLE tipo_archivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);
INSERT INTO tipo_archivo (nombre) VALUES ('GUÍA');

CREATE TABLE archivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nombre_archivo TEXT NOT NULL,
    documento LONGBLOB NOT NULL,
    descripcion VARCHAR(255) NULL,
    tipo VARCHAR(255) NULL,
	id_tipo_archivo INT NOT NULL,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    FOREIGN KEY (id_tipo_archivo) REFERENCES tipo_archivo(id)
);

