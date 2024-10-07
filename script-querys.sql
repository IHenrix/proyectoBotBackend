----------LISTA POR CATEGORIA
SELECT p.id AS id_persona, CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS nombre,
       ca.id AS id_categoria,
       ca.nombre AS categoria,
       COUNT(*) AS total_consultas,
       DATE_FORMAT(MAX(c.fecha_creacion), '%d/%m/%Y %H:%i:%s') AS ultima_fecha
FROM consultas c
         INNER JOIN persona p ON c.id_persona = p.id
         INNER JOIN categoria ca ON c.id_categoria = ca.id
WHERE p.id=3 AND ca.id=1
GROUP BY id_persona,nombre,id_categoria, categoria
ORDER BY nombre ASC;


--LISTA POR CATEGORIA DETALLE

SELECT CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS nombre,
       ca.nombre AS categoria,
       c.msj_persona pregunta,
       c.msj_bot respuesta,
       DATE_FORMAT(c.fecha_creacion, '%d/%m/%Y %H:%i:%s') AS fecha
FROM consultas c
         INNER JOIN persona p ON c.id_persona = p.id
         INNER JOIN categoria ca ON c.id_categoria = ca.id
WHERE p.id=3 AND ca.id=1
ORDER BY fecha,pregunta ASC;

--LISTA DE ESTUDIANTES

SELECT p.id codigo,
       CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS cod
FROM persona p
         INNER JOIN usuario u ON p.id_usuario=u.id
         INNER JOIN usuario_rol ur ON u.id=ur.usuario_id
WHERE ur.rol_id=1
ORDER BY nombre ASC;

--LISTA DE CATEGORIAS
SELECT id cod,nombre FROM categoria ORDER BY id asc;
