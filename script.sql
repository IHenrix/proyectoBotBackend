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

CREATE TABLE alumno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido_paterno VARCHAR(255) NOT NULL,
    apellido_materno VARCHAR(255) NOT NULL,
    sexo VARCHAR(1) NOT NULL,
    codigo_alumno VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefono VARCHAR(255),
    carrera VARCHAR(250),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);



INSERT INTO rol (nombre, descripcion) VALUES ('ROLE_USER', 'USUARIO');

INSERT INTO usuario (username, password, enabled) VALUES ('75911772', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);
INSERT INTO usuario (username, password, enabled) VALUES ('72274736', '$2a$10$1uPuPvL1Ps1u7/rQPuI1pe/Ra.E3wxzZoElDSe.tBald1GBQrQsfu', true);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1);

INSERT INTO alumno (
    nombre, apellido_paterno, apellido_materno, sexo, codigo_alumno, carrera, email, telefono, id_usuario
) 
VALUES (
    'RICARDO ENRIQUE', 'PRADA', 'GUERRA', 'M', '2024141514', 'Ingeniería de Software', 'enrique.pdg@gmail.com', '912016161', 1
);
INSERT INTO alumno (
    nombre, apellido_paterno, apellido_materno, sexo, codigo_alumno, carrera, email, telefono, id_usuario
) 
VALUES (
    'GRACIA ANTUANETTE', 'MARCA', 'TORRES', 'F', '2019104010', 'Ingeniería de Computación y Sistemas', 'gracia.marcag@gmail.com', '913256561', 2
);


SELECT 
    *
FROM 
    alumno a
JOIN 
    usuario u ON a.id_usuario = u.id
WHERE 
    u.username = '75911772';

SELECT * FROM usuario;