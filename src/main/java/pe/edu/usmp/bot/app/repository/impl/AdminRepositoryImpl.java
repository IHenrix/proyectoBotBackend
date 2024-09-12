package pe.edu.usmp.bot.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import pe.edu.usmp.bot.app.repository.AdminRepository;
import pe.edu.usmp.bot.app.request.CreaModiRolRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.PersonaResponse;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepositoryImpl extends JdbcDaoSupport implements AdminRepository {

    @Autowired
    private ApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DataSource(DataSource setDataSource) {
        setDataSource(setDataSource);
        this.jdbcTemplate = context.getBean("db_desa", JdbcTemplate.class);
    }


    @Override
    public List<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos) {
        StringBuilder sql = new StringBuilder("SELECT p.nombre, p.apellido_paterno, p.apellido_materno, p.sexo, p.codigo, p.email, p.telefono, p.carrera " +
                "FROM persona p INNER JOIN usuario u ON p.id_usuario = u.id WHERE EXISTS (SELECT 1 FROM usuario_rol ur WHERE ur.usuario_id = u.id)");

        List<Object> params = new ArrayList<>();

        if (datos.getNombre() != null && !datos.getNombre().isEmpty()) {
            sql.append(" AND p.nombre LIKE ?");
            params.add("%" + datos.getNombre() + "%");
        }

        if (datos.getApellidoPaterno() != null && !datos.getApellidoPaterno().isEmpty()) {
            sql.append(" AND p.apellido_paterno LIKE ?");
            params.add("%" + datos.getApellidoPaterno() + "%");
        }

        if (datos.getApellidoMaterno() != null && !datos.getApellidoMaterno().isEmpty()) {
            sql.append(" AND p.apellido_materno LIKE ?");
            params.add("%" + datos.getApellidoMaterno() + "%");
        }

        if (datos.getUsuario() != null && !datos.getUsuario().isEmpty()) {
            sql.append(" AND u.username LIKE ?");
            params.add("%" + datos.getUsuario() + "%");
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(PersonaResponse.class));
    }

    @Override
    public void crearUsuario(CreaModiUsuarioRequest datos) {
        String sqlUsuario = "INSERT INTO usuario (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlUsuario, datos.getUsuario(), datos.getPassword(), true);
        Long usuarioId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        String sqlPersona = "INSERT INTO persona (nombre, apellido_paterno, apellido_materno, sexo, codigo, email, telefono, carrera, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlPersona, datos.getNombre(), datos.getApellidoPaterno(), datos.getApellidoMaterno(),
                datos.getSexo(), datos.getCodigo(), datos.getEmail(), datos.getTelefono(),
                datos.getCarrera(), usuarioId);

        // Insertar roles en la tabla `usuario_rol`
        String sqlUsuarioRol = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (?, ?)";
        for (CreaModiRolRequest rol : datos.getRoles()) {
            jdbcTemplate.update(sqlUsuarioRol, usuarioId, rol.getId());
        }
    }

    @Override
    public void editarUsuario(CreaModiUsuarioRequest datos) {
        String sqlUpdateUsuario = "UPDATE usuario SET username = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sqlUpdateUsuario, datos.getUsuario(), datos.getPassword(), Long.parseLong(datos.getId()));

        String sqlUpdateAlumno = "UPDATE persona SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, sexo = ?, " +
                "codigo= ?, email = ?, telefono = ?, carrera = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sqlUpdateAlumno, datos.getNombre(), datos.getApellidoPaterno(),
                datos.getApellidoMaterno(), datos.getSexo(), datos.getCodigo(),
                datos.getEmail(), datos.getTelefono(), datos.getCarrera(), Long.parseLong(datos.getId()));

        String sqlDeleteRoles = "DELETE FROM usuario_rol WHERE usuario_id = ?";
        jdbcTemplate.update(sqlDeleteRoles, Long.parseLong(datos.getId()));

        String sqlInsertRol = "INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (?, ?)";
        for (CreaModiRolRequest rol : datos.getRoles()) {
            jdbcTemplate.update(sqlInsertRol, Long.parseLong(datos.getId()), rol.getId());
        }
    }

    @Override
    public boolean esUsernameUsado(String username) {
        try {
            String sql = "SELECT COUNT(*) FROM usuario WHERE username = ?";
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        // Eliminar roles del usuario en la tabla usuario_rol
        String sqlDeleteRoles = "DELETE FROM usuario_rol WHERE usuario_id = ?";
        jdbcTemplate.update(sqlDeleteRoles, usuarioId);

        // Eliminar la información de la persona asociada al usuario
        String sqlDeletePersona = "DELETE FROM persona WHERE id_usuario = ?";
        jdbcTemplate.update(sqlDeletePersona, usuarioId);

        // Eliminar el usuario en la tabla usuario
        String sqlDeleteUsuario = "DELETE FROM usuario WHERE id = ?";
        jdbcTemplate.update(sqlDeleteUsuario, usuarioId);
    }


}
