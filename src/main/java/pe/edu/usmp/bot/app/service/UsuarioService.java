package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.model.Persona;
import pe.edu.usmp.bot.app.model.Usuario;

public interface UsuarioService {
	
	Usuario findByUsername(String username);
	
	Persona buscarPersonaBPorUsername(String username);

}
