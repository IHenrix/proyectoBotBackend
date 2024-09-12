package pe.edu.usmp.bot.app.service;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.MsgResponse;
import pe.edu.usmp.bot.app.response.PersonaResponse;

public interface AdminService {

    ListModelResponse<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos);

    MsgResponse crearUsuario(CreaModiUsuarioRequest datos);

    MsgResponse editarUsuario(CreaModiUsuarioRequest datos);

    MsgResponse eliminarUsuario(Long usuarioId);

}
