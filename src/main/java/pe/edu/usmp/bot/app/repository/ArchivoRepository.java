package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.ArchivoResponse;

import java.util.List;

public interface ArchivoRepository {

    List<ArchivoResponse> listarArchivos(ListarArchivosRequest datos);

    ArchivoResponse buscarArchivo(Long idArchivo);

    void crearArchivo(CreaModiArchivoRequest datos);

    void editarArchivo(CreaModiArchivoRequest datos);

    void eliminarArchivo(Long idArchivo);

    byte[] obtenerDocumento(Long idArchivo);
}
