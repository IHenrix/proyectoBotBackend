package pe.edu.usmp.bot.app.repository;


import pe.edu.usmp.bot.app.model.Persona;
import pe.edu.usmp.bot.app.model.Usuario;
import pe.edu.usmp.bot.app.request.ConsultaRequest;

public interface UsuarioRepository {

	Usuario findByUsername(String username);
	
	Persona buscarPersonaBPorUsername(String username);

	void guardarConsulta(ConsultaRequest datos);

}
