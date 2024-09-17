package pe.edu.usmp.bot.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.usmp.bot.app.repository.ArchivoRepository;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.ArchivoService;
import pe.edu.usmp.bot.app.utils.Constantes;
import pe.edu.usmp.bot.app.utils.UtilResource;

import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {

	@Autowired
	private ArchivoRepository repo;

	@Override
	public CreaModiArchivoRequest guardarDatosArchivo(Boolean edit,Long id,String nombre, String descripcion, Long idTipoArchivo,
			MultipartFile archivo) {

		String nombreArchivo = "";
		String extension = "";

		byte[] documento=null;
		try {
			documento = archivo.getBytes();
			nombreArchivo = archivo.getOriginalFilename();
			extension = UtilResource.obtenerExtensionArchivo(archivo.getOriginalFilename());
		} catch (Exception e) {
			if(!edit){
				throw new RuntimeException("Error al procesar el archivo", e);
			}
		}

		CreaModiArchivoRequest datos = new CreaModiArchivoRequest();
		datos.setNombre(nombre);
		datos.setDescripcion(descripcion);
		datos.setTipo(extension);
		datos.setIdTipoArchivo(idTipoArchivo);
		datos.setDocumento(documento);
		datos.setNombreArchivo(nombreArchivo);
		datos.setId(id);
		return datos;
	}

	@Override
	public ListModelResponse<ArchivoResponse> listarArchivos(ListarArchivosRequest datos) {
		ListModelResponse<ArchivoResponse> resp = new ListModelResponse<>();
		List<ArchivoResponse> lista = repo.listarArchivos(datos);
		if (!lista.isEmpty()) {
			resp.setCod(Constantes.SUCCESS_COD);
			resp.setIcon(Constantes.ICON_SUCCESS);
			resp.setMensaje("Se ha encontrado GUIAS");
			resp.setList(lista);
		} else {
			resp.setCod(Constantes.NULL_COD);
			resp.setIcon(Constantes.ICON_INFO);
			;
			resp.setMensaje("No se han encontrado GUIAS");
		}
		return resp;
	}

	@Override
	public ModelResponse<ArchivoResponse> buscarArchivo(Long idArchivo) {
		ModelResponse<ArchivoResponse> resp = new ModelResponse<>();
		ArchivoResponse model = repo.buscarArchivo(idArchivo);
		if (model != null) {
			resp.setCod(Constantes.SUCCESS_COD);
			resp.setIcon(Constantes.ICON_SUCCESS);
			;
			resp.setMensaje("Se ha encontrado archivo");
			resp.setModel(model);
		} else {
			resp.setMensaje("No se ha encontrado archivo");
		}
		return resp;
	}

	@Override
	public MsgResponse crearArchivo(CreaModiArchivoRequest datos) {
		MsgResponse resp = new MsgResponse();
		repo.crearArchivo(datos);
		resp.setCod(Constantes.SUCCESS_COD);
		resp.setIcon(Constantes.ICON_SUCCESS);
		resp.setMensaje("Se ha subido la guía satifactoriamente");
		return resp;
	}

	@Override
	public MsgResponse editarArchivo(CreaModiArchivoRequest datos) {
		MsgResponse resp = new MsgResponse();
		repo.editarArchivo(datos);
		resp.setCod(Constantes.SUCCESS_COD);
		resp.setIcon(Constantes.ICON_SUCCESS);
		resp.setMensaje("Se ha modificado la guía satifactoriamente");
		return resp;
	}

	@Override
	public MsgResponse eliminarArchivo(Long idArchivo) {
		MsgResponse resp = new MsgResponse();
		repo.eliminarArchivo(idArchivo);
		resp.setCod(Constantes.SUCCESS_COD);
		resp.setIcon(Constantes.ICON_INFO);
		;
		resp.setMensaje("Se ha eliminado la guía satifactoriamente");
		return resp;
	}

	@Override
	public ModelResponse<byte[]> obtenerDocumento(Long idArchivo) {
		ModelResponse<byte[]> resp = new ModelResponse<>();
		byte[] model = repo.obtenerDocumento(idArchivo);
		if (model != null) {
			resp.setCod(Constantes.SUCCESS_COD);
			resp.setIcon(Constantes.ICON_SUCCESS);
			;
			resp.setMensaje("Se ha encontrado documento");
			resp.setModel(model);
		} else {
			resp.setMensaje("No se ha encontrado documento");
		}
		return resp;
	}

}
