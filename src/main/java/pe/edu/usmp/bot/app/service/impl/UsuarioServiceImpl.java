package pe.edu.usmp.bot.app.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.usmp.bot.app.model.Persona;
import pe.edu.usmp.bot.app.model.Usuario;
import pe.edu.usmp.bot.app.repository.UsuarioRepository;
import pe.edu.usmp.bot.app.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public Usuario findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Persona buscarPersonaBPorUsername(String username) {
		return repo.buscarPersonaBPorUsername(username);
	}

}
