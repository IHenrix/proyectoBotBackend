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
    codigo VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    telefono VARCHAR(255),
	login_email BOOLEAN DEFAULT FALSE,
    id_carrera INT NULL,
    id_usuario INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_carrera) REFERENCES carrera(id)

);

INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_USER', 'ALUMNO');
INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_DOCENTE', 'DOCENTE/ADMINISTRADOR');

INSERT INTO usuario (username, password, enabled) VALUES ('enrique_prada@usmp.pe', '$2a$10$FVgYU0sJZBnySdeRYmUEQu3m6XOZwa7B1YdsbHwqwB.mXnt9h.UJO', true);
INSERT INTO usuario (username, password, enabled) VALUES ('gracia_marca@usmp.pe', '$2a$10$FVgYU0sJZBnySdeRYmUEQu3m6XOZwa7B1YdsbHwqwB.mXnt9h.UJO', true);
INSERT INTO usuario (username, password, enabled) VALUES ('alexandra_palomino2@usmp.pe', '$2a$10$FVgYU0sJZBnySdeRYmUEQu3m6XOZwa7B1YdsbHwqwB.mXnt9h.UJO', true);
INSERT INTO usuario (username, password, enabled) VALUES ('roberto_paredes@usmp.pe', '$2a$10$FVgYU0sJZBnySdeRYmUEQu3m6XOZwa7B1YdsbHwqwB.mXnt9h.UJO', true);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (2, 2);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (3, 2);
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (4, 1);

INSERT INTO carrera (nombre) VALUES ('Ingeniería de Computación y Sistemas');
INSERT INTO carrera (nombre) VALUES ('Ingeniería de Software');

INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'RICARDO ENRIQUE', 'PRADA', 'GUERRA', 'M', '2024141514', 2, null, '912016161', 1
);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'GRACIA ANTUANETTE', 'MARCA', 'TORRES', 'F', null, 1, null, '913256561', 2
);

INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'ALEXANDRA STEFANNY', 'PALOMINO', 'RUIZ', 'F', null, 1, null, '913256561', 3
);
INSERT INTO persona (
    nombre, apellido_paterno, apellido_materno, sexo, codigo, id_carrera, email, telefono, id_usuario
) 
VALUES (
    'ROBERT D JESÚS', 'PAREDES', 'GARCIA', 'M', '2020104010', 1, null, '913256561', 4
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

CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

INSERT INTO categoria (nombre) 
VALUES (
    'CONSULTA SOBRE MI PROYECTO'
);

INSERT INTO categoria (nombre) 
VALUES (
    'GUIÁS ACADÉMICAS'
);

INSERT INTO categoria (nombre) 
VALUES (
    'CONSULTA DE RECURSOS BIBLIOGRÁFICOS'
);

INSERT INTO categoria (nombre) 
VALUES (
    'RETROALIMENTACIÓN DE DOCUMENTOS'
);
CREATE TABLE consultas (
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_categoria INT NOT NULL,
	msj_bot TEXT NULL,
	msj_persona TEXT NULL,
	id_persona INT NOT NULL,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    openai BOOLEAN DEFAULT TRUE,
	FOREIGN KEY (id_categoria) REFERENCES categoria(id),
	FOREIGN KEY (id_persona) REFERENCES persona(id)
);
