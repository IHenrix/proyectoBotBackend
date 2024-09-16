package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.CodNombreResponse;
import pe.edu.usmp.bot.app.response.PersonaResponse;
import java.util.List;

public interface AdminRepository {

    List<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos);

    PersonaResponse buscarPersona(Long idPersona);

    void crearUsuario(CreaModiUsuarioRequest datos);

    void editarUsuario(CreaModiUsuarioRequest datos);

    boolean esUsernameUsado(String username);

    void eliminarUsuario(Long usuarioId);

    List<CodNombreResponse> listaCarrera();
}
