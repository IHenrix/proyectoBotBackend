package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.ArchivoResponse;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.response.MsgResponse;

public interface ArchivoService {

    ListModelResponse<ArchivoResponse> listarArchivos(ListarArchivosRequest datos);

    ModelResponse<ArchivoResponse> buscarArchivo(Long idArchivo);

    MsgResponse crearArchivo(CreaModiArchivoRequest datos);

    MsgResponse editarArchivo(CreaModiArchivoRequest datos);

    MsgResponse eliminarArchivo(Long idArchivo);
}
