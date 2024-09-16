package pe.edu.usmp.bot.app.service;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.ArchivoResponse;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.response.MsgResponse;

public interface ArchivoService {

	CreaModiArchivoRequest guardarDatosArchivo(Boolean edit,String nombre, String descripcion, Long idTipoArchivo,
			MultipartFile archivo);

	ListModelResponse<ArchivoResponse> listarArchivos(ListarArchivosRequest datos);

	ModelResponse<ArchivoResponse> buscarArchivo(Long idArchivo);

	MsgResponse crearArchivo(CreaModiArchivoRequest datos);

	MsgResponse editarArchivo(CreaModiArchivoRequest datos);

	MsgResponse eliminarArchivo(Long idArchivo);

	ModelResponse<byte[]> obtenerDocumento(Long idArchivo);
}
