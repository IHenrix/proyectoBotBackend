package pe.edu.usmp.bot.app.service;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.response.MsgResponse;

public interface AdminService {

    MsgResponse crearUsuario(CreaModiUsuarioRequest datos);

    MsgResponse editarUsuario(CreaModiUsuarioRequest datos);


}
