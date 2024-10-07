package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.CodNombreResponse;
import pe.edu.usmp.bot.app.response.PersonaResponse;
import pe.edu.usmp.bot.app.response.RolesResponse;

import java.util.List;

public interface AdminRepository {

    List<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos);

    PersonaResponse buscarPersona(Long idPersona);

    void crearUsuario(CreaModiUsuarioRequest datos);
    
    void editarUsuario(CreaModiUsuarioRequest datos);

    boolean esUsernameUsado(String username,Long usuario);

    void eliminarUsuario(Long usuarioId);

    List<CodNombreResponse> listaCarrera();
    
    List<RolesResponse> listarRoles(Long usuario);
    
    Long obtenerIdCarreraSiExiste(String nombreCarrera);

    List<CodNombreResponse> listarEstudiantes();

    List<CodNombreResponse> listarCategoria();


}
