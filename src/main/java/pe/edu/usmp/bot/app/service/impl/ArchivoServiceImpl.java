package pe.edu.usmp.bot.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.usmp.bot.app.repository.ArchivoRepository;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.ArchivoService;
import pe.edu.usmp.bot.app.utils.Constantes;

import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    private ArchivoRepository repo;

    @Override
    public ListModelResponse<ArchivoResponse> listarArchivos(ListarArchivosRequest datos) {
        ListModelResponse<ArchivoResponse> resp= new ListModelResponse<>();
        List<ArchivoResponse> lista= repo.listarArchivos(datos);
        if(!lista.isEmpty()){
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado GUIAS");
            resp.setList(lista);
        }
        else{
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);;
            resp.setMensaje("No se han encontrado GUIAS");
        }
        return resp;
    }

    @Override
    public ModelResponse<ArchivoResponse> buscarArchivo(Long idArchivo) {
        ModelResponse<ArchivoResponse> resp = new ModelResponse<>();
        ArchivoResponse model= repo.buscarArchivo(idArchivo);
        if (model != null) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);;
            resp.setMensaje("Se ha encontrado archivo");
            resp.setModel(model);
        }
        else{
            resp.setMensaje("No se ha encontrado archivo");
        }
        return resp;
    }

    @Override
    public MsgResponse crearArchivo(CreaModiArchivoRequest datos) {
        MsgResponse resp = new MsgResponse();
        repo.crearArchivo(datos);
        resp.setCod(Constantes.SUCCESS_COD);
        resp.setIcon(Constantes.ICON_SUCCESS);;
        resp.setMensaje("Se ha registrado el archivo sastifactoriamente");
        return resp;
    }

    @Override
    public MsgResponse editarArchivo(CreaModiArchivoRequest datos) {
        MsgResponse resp = new MsgResponse();
        repo.editarArchivo(datos);
        resp.setCod(Constantes.SUCCESS_COD);
        resp.setIcon(Constantes.ICON_SUCCESS);;
        resp.setMensaje("Se ha modificado el archivo sastifactoriamente");

        return resp;
    }

    @Override
    public MsgResponse eliminarArchivo(Long idArchivo) {
        MsgResponse resp = new MsgResponse();
        repo.eliminarArchivo(idArchivo);
        resp.setCod(Constantes.SUCCESS_COD);
        resp.setIcon(Constantes.ICON_INFO);;
        resp.setMensaje("Se ha eliminado el archivo sastifactoriamente");
        return resp;
    }

    @Override
    public ModelResponse<byte[]> obtenerDocumento(Long idArchivo) {
        ModelResponse<byte[]> resp = new ModelResponse<>();
        byte[] model= repo.obtenerDocumento(idArchivo);
        if (model != null) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);;
            resp.setMensaje("Se ha encontrado documento");
            resp.setModel(model);
        }
        else{
            resp.setMensaje("No se ha encontrado documento");
        }
        return resp;
    }
}
