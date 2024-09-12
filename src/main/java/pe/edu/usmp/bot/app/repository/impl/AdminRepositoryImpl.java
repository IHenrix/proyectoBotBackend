package pe.edu.usmp.bot.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import pe.edu.usmp.bot.app.repository.AdminRepository;
import pe.edu.usmp.bot.app.request.CreaModiRolRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;

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
    public void crearUsuario(CreaModiUsuarioRequest datos) {
        String sqlUsuario = "INSERT INTO usuario (username, password, enabled) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlUsuario, datos.getUsuario(), datos.getPassword(), true);
        Long usuarioId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        String sqlAlumno = "INSERT INTO alumno (nombre, apellido_paterno, apellido_materno, sexo, codigo_alumno, email, telefono, carrera, id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlAlumno, datos.getNombre(), datos.getApellidoPaterno(), datos.getApellidoMaterno(),
                datos.getSexo(), datos.getCodigoAlumno(), datos.getEmail(), datos.getTelefono(),
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

        String sqlUpdateAlumno = "UPDATE alumno SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, sexo = ?, " +
                "codigo_alumno = ?, email = ?, telefono = ?, carrera = ? WHERE id_usuario = ?";
        jdbcTemplate.update(sqlUpdateAlumno, datos.getNombre(), datos.getApellidoPaterno(),
                datos.getApellidoMaterno(), datos.getSexo(), datos.getCodigoAlumno(),
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


}
