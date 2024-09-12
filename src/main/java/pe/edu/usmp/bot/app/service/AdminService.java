package pe.edu.usmp.bot.app.service;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.response.MsgResponse;

public interface AdminService {

    public MsgResponse crearUsuario(CreaModiUsuarioRequest datos);
}
