package pe.edu.usmp.bot.app.repository;


import pe.edu.usmp.bot.app.model.Persona;
import pe.edu.usmp.bot.app.model.Usuario;

public interface UsuarioRepository {

	Usuario findByUsername(String username);
	
	Persona buscarPersonaBPorUsername(String username);

}
