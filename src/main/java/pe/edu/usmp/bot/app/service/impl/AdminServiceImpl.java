package pe.edu.usmp.bot.app.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.usmp.bot.app.repository.AdminRepository;
import pe.edu.usmp.bot.app.request.CreaModiRolRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.response.MsgResponse;
import pe.edu.usmp.bot.app.service.AdminService;
import pe.edu.usmp.bot.app.utils.Constantes;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repo;

    @Override
    public MsgResponse crearUsuario(CreaModiUsuarioRequest datos) {
        MsgResponse resp = new MsgResponse();
        repo.crearUsuario(datos);
        resp.setCod(Constantes.SUCCESS_COD);
        resp.setIcon(Constantes.ICON_SUCCESS);;
        resp.setMensaje("Se ha registrado al usuario sastifactoriamente");
        return resp;
    }

    @Override
    public MsgResponse editarUsuario(CreaModiUsuarioRequest datos) {
        MsgResponse resp = new MsgResponse();
        if(repo.esUsernameUsado(datos.getUsuario())){
            resp.setIcon(Constantes.ICON_INFO);;
            resp.setMensaje("El usuario "+datos.getUsuario()+" ya existe");
        }
        else{
            repo.editarUsuario(datos);
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);;
            resp.setMensaje("Se ha modificado al usuario sastifactoriamente");
        }
        return resp;
    }
}
