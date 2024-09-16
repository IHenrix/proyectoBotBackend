package pe.edu.usmp.bot.app.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.usmp.bot.app.repository.AdminRepository;
import pe.edu.usmp.bot.app.request.CreaModiRolRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AdminService;
import pe.edu.usmp.bot.app.utils.Constantes;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repo;

    @Override
    public ListModelResponse<PersonaResponse> listarUsuarios(ListarUsuarioRequest datos) {
        ListModelResponse<PersonaResponse> resp= new ListModelResponse<>();
        List<PersonaResponse> lista= repo.listarUsuarios(datos);
        if(!lista.isEmpty()){
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado usuarios");
            resp.setList(lista);
        }
        else{
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);;
            resp.setMensaje("No se han encontrado usuarios");
        }
        return resp;
    }

    @Override
    public ModelResponse<PersonaResponse> buscarPersona(Long idPersona) {
        ModelResponse<PersonaResponse> resp = new ModelResponse<>();
        PersonaResponse model= repo.buscarPersona(idPersona);
        if (model != null) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);;
            resp.setMensaje("Se ha encontrado al usuario");
            resp.setModel(model);
        }
        else{
            resp.setMensaje("No se ha encontrado al usuario");
        }
        return resp;
    }

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

    @Override
    public MsgResponse eliminarUsuario(Long usuarioId) {
        MsgResponse resp = new MsgResponse();
        repo.eliminarUsuario(usuarioId);
        resp.setCod(Constantes.SUCCESS_COD);
        resp.setIcon(Constantes.ICON_INFO);;
        resp.setMensaje("Se ha eliminado al usuario sastifactoriamente");
        return resp;
    }

    @Override
    public List<CodNombreResponse> listaCarrera() {
        return repo.listaCarrera();
    }
}
