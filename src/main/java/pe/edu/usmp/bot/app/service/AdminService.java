package pe.edu.usmp.bot.app.service;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.*;

public interface AdminService {

    ListModelResponse<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos);

    ModelResponse<PersonaResponse> buscarPersona(Long idPersona);

    MsgResponse crearUsuario(CreaModiUsuarioRequest datos);

    MsgResponse editarUsuario(CreaModiUsuarioRequest datos);

    MsgResponse eliminarUsuario(Long usuarioId);

}
