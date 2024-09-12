package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AdminService;
import pe.edu.usmp.bot.app.service.ArchivoService;
import pe.edu.usmp.bot.app.utils.UtilResource;

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
    public ModelResponse<PersonaResponse> buscarPersona(@RequestParam Long idPersona){
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
    @RequestMapping(value = "eliminarUsuario", method = RequestMethod.POST)
    public MsgResponse eliminarUsuario(@RequestParam Long usuarioId) {
        return se.eliminarUsuario(usuarioId);
    }
    @RequestMapping(value = "listarArchivos", method = RequestMethod.POST)
    ListModelResponse<ArchivoResponse> listarArchivos(@RequestBody ListarArchivosRequest datos){
        return seArchivo.listarArchivos(datos);
    }
    @RequestMapping(value = "buscarArchivo", method = RequestMethod.GET)
    ModelResponse<ArchivoResponse> buscarArchivo(@RequestParam Long idArchivo){
        return seArchivo.buscarArchivo(idArchivo);
    }
    @RequestMapping(value = "crearArchivo", method = RequestMethod.POST, consumes = "multipart/form-data")
    public MsgResponse crearArchivo(
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("idTipoArchivo") Long idTipoArchivo,
            @RequestParam("archivo") MultipartFile archivo) {

        byte[] documento;
        try {
            documento = archivo.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo", e);
        }
        String extension = UtilResource.obtenerExtensionArchivo(archivo.getOriginalFilename());

        CreaModiArchivoRequest datos = new CreaModiArchivoRequest();
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setTipo(extension);
        datos.setIdTipoArchivo(idTipoArchivo);
        datos.setDocumento(documento);
        return seArchivo.crearArchivo(datos);
    }
    @RequestMapping(value = "editarArchivo", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public MsgResponse editarArchivo(
            @RequestParam("id") Long id,  // Se pasa el ID del archivo
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("idTipoArchivo") Long idTipoArchivo,
            @RequestParam("archivo") MultipartFile archivo) {
        // Convertir el MultipartFile a byte[]
        byte[] documento;
        try {
            documento = archivo.getBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo", e);
        }
        String extension = UtilResource.obtenerExtensionArchivo(archivo.getOriginalFilename());

        CreaModiArchivoRequest datos = new CreaModiArchivoRequest();
        datos.setId(id);
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setTipo(extension);
        datos.setIdTipoArchivo(idTipoArchivo);
        datos.setDocumento(documento);
        return seArchivo.editarArchivo(datos);
    }

    @RequestMapping(value = "eliminarArchivo", method = RequestMethod.GET)
    MsgResponse eliminarArchivo(@RequestParam Long idArchivo){
        return seArchivo.eliminarArchivo(idArchivo);
    }

    @RequestMapping(value = "obtenerDocumento", method = RequestMethod.POST)
    public ResponseEntity<String> obtenerDocumento(@RequestParam Long idArchivo){
       return obtenerDocumento(idArchivo);
    }

}
