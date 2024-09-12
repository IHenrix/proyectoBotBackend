package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;

public interface AdminRepository {

    void crearUsuario(CreaModiUsuarioRequest datos);

    void editarUsuario(CreaModiUsuarioRequest datos);

    boolean esUsernameUsado(String username);
}
