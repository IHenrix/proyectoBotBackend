package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.PersonaResponse;
import java.util.List;

public interface AdminRepository {

    List<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos);

    void crearUsuario(CreaModiUsuarioRequest datos);

    void editarUsuario(CreaModiUsuarioRequest datos);

    boolean esUsernameUsado(String username);

    void eliminarUsuario(Long usuarioId);
}
