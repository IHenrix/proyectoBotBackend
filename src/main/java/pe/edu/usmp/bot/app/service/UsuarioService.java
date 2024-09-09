package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.model.Alumno;
import pe.edu.usmp.bot.app.model.Usuario;

public interface UsuarioService {
	
	public Usuario findByUsername(String username);
	
	public Alumno findAlumnoByUsername(String username);

}
