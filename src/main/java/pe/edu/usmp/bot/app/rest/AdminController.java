package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioMasivoRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AdminService;
import pe.edu.usmp.bot.app.service.ArchivoService;
import pe.edu.usmp.bot.app.utils.UtilResource;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService se;

	@Autowired
	private ArchivoService seArchivo;

	@RequestMapping(value = "listarUsuarios", method = RequestMethod.POST)
	ListModelResponse<PersonaResponse> listarUsuarios(@RequestBody ListarUsuarioRequest datos) {
		return se.listarUsuarios(datos);
	}

	@RequestMapping(value = "buscarPersona", method = RequestMethod.GET)
	public ModelResponse<PersonaResponse> buscarPersona(@RequestParam Long idPersona) {
		return se.buscarPersona(idPersona);
	}

	@RequestMapping(value = "crearUsuario", method = RequestMethod.POST)
	public MsgResponse crearUsuario(@RequestBody CreaModiUsuarioRequest datos) {
		return se.crearUsuario(datos);
	}

	@RequestMapping(value = "editarUsuario", method = RequestMethod.POST)
	public MsgResponse editarUsuario(@RequestBody CreaModiUsuarioRequest datos) {
		return se.editarUsuario(datos);
	}

	@RequestMapping(value = "eliminarUsuario/{usuarioId}", method = RequestMethod.POST)
	public MsgResponse eliminarUsuario(@PathVariable Long usuarioId) {
		return se.eliminarUsuario(usuarioId);
	}

	@RequestMapping(value = "listarArchivos", method = RequestMethod.POST)
	ListModelResponse<ArchivoResponse> listarArchivos(@RequestBody ListarArchivosRequest datos) {
		return seArchivo.listarArchivos(datos);
	}

	@RequestMapping(value = "buscarArchivo", method = RequestMethod.GET)
	ModelResponse<ArchivoResponse> buscarArchivo(@RequestParam Long idArchivo) {
		return seArchivo.buscarArchivo(idArchivo);
	}

	@RequestMapping(value = "crearArchivo", method = RequestMethod.POST, consumes = "multipart/form-data")
	public MsgResponse crearArchivo(@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion, @RequestParam("idTipoArchivo") Long idTipoArchivo,
			@RequestParam("archivo") MultipartFile archivo) {

		return seArchivo.crearArchivo(seArchivo.guardarDatosArchivo(false,null,nombre, descripcion, idTipoArchivo, archivo));
	}

	@RequestMapping(value = "editarArchivo", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public MsgResponse editarArchivo(@RequestParam("id") Long id,
			@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion,
			@RequestParam("idTipoArchivo") Long idTipoArchivo, @RequestParam(value = "archivo", required = false) MultipartFile archivo) {
		return seArchivo.editarArchivo(seArchivo.guardarDatosArchivo(true,id,nombre, descripcion, idTipoArchivo, archivo));
	}

	@RequestMapping(value = "eliminarArchivo", method = RequestMethod.GET)
	MsgResponse eliminarArchivo(@RequestParam Long idArchivo) {
		return seArchivo.eliminarArchivo(idArchivo);
	}

	@RequestMapping(value = "obtenerDocumento", method = RequestMethod.GET)
	public ModelResponse<byte[]> obtenerDocumento(@RequestParam Long idArchivo) {
		return seArchivo.obtenerDocumento(idArchivo);
	}
	@RequestMapping(value = "listaCarrera", method = RequestMethod.GET)
	public List<CodNombreResponse> listaCarrera(){
		return se.listaCarrera();
	}
	
	@RequestMapping(value = "crearUsuariosMasivo", method = RequestMethod.POST)
	public MsgResponse crearUsuariosMasivo(@RequestBody CreaModiUsuarioMasivoRequest datos) {
		return se.crearUsuariosMasivo(datos);
	}
}
