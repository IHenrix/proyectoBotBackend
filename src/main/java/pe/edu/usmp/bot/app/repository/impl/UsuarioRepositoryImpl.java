package pe.edu.usmp.bot.app.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import pe.edu.usmp.bot.app.model.Alumno;
import pe.edu.usmp.bot.app.model.Rol;
import pe.edu.usmp.bot.app.model.Usuario;
import pe.edu.usmp.bot.app.repository.UsuarioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

@Repository
public class UsuarioRepositoryImpl extends JdbcDaoSupport implements UsuarioRepository {

	@Autowired
	private ApplicationContext context;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void DataSource(DataSource setDataSource) {
		setDataSource(setDataSource);
		this.jdbcTemplate = context.getBean("db_desa", JdbcTemplate.class);
	}

	@SuppressWarnings("deprecation")
	public Usuario findByUsername(String username) {
		String sql = "SELECT * FROM usuario WHERE username = ?";
		Usuario usuario = null;
		try {
			usuario = jdbcTemplate.queryForObject(sql, new Object[] { username }, this::mapUsuario);
			;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return usuario;
	}

	private Usuario mapUsuario(ResultSet rs, int rowNum) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getLong("id"));
		usuario.setUsername(rs.getString("username"));
		usuario.setPassword(rs.getString("password"));
		usuario.setEnabled(rs.getBoolean("enabled"));

		// Obtener los roles del usuario
		usuario.setRoles(findRolesByUsuarioId(usuario.getId()));

		return usuario;
	}

	@SuppressWarnings("deprecation")
	private List<Rol> findRolesByUsuarioId(Long usuarioId) {
		String sql = "SELECT r.* FROM rol r " + "INNER JOIN usuario_rol ur ON r.id = ur.rol_id "
				+ "WHERE ur.usuario_id = ?";
		return jdbcTemplate.query(sql, new Object[] { usuarioId }, this::mapRol);
	}

	private Rol mapRol(ResultSet rs, int rowNum) throws SQLException {
		Rol rol = new Rol();
		rol.setId(rs.getLong("id"));
		rol.setNombre(rs.getString("nombre"));
		rol.setDescripcion(rs.getString("descripcion"));
		return rol;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Alumno findAlumnoByUsername(String username) {
		String sql = "SELECT a.* FROM alumno a " + "JOIN usuario u ON a.id_usuario = u.id " + "WHERE u.username = ?";

		return jdbcTemplate.queryForObject(sql, new Object[] { username }, this::mapAlumno);
	}

	private Alumno mapAlumno(ResultSet rs, int rowNum) throws SQLException {
		Alumno alumno = new Alumno();
		alumno.setId(rs.getLong("id"));
		alumno.setNombre(rs.getString("nombre"));
		alumno.setApellidoPaterno(rs.getString("apellido_paterno"));
		alumno.setApellidoMaterno(rs.getString("apellido_materno"));
		alumno.setSexo(rs.getString("sexo"));
		alumno.setCodigoAlumno(rs.getString("codigo_alumno"));
		alumno.setEmail(rs.getString("email"));
		alumno.setTelefono(rs.getString("telefono"));
		alumno.setCarrera(rs.getString("carrera"));
		return alumno;
	}

}
