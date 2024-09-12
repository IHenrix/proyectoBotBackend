package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;

public interface AdminRepository {

    public void crearUsuario(CreaModiUsuarioRequest datos);

}
