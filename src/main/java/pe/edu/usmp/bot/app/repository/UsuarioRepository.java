package pe.edu.usmp.bot.app.repository;


import pe.edu.usmp.bot.app.model.Alumno;
import pe.edu.usmp.bot.app.model.Usuario;

public interface UsuarioRepository {

	public Usuario findByUsername(String username);
	
	public Alumno findAlumnoByUsername(String username);

}
