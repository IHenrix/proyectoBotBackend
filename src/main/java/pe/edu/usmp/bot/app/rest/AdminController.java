package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AdminService;
import pe.edu.usmp.bot.app.service.ArchivoService;

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
    @RequestMapping(value = "buscarPersona", method = RequestMethod.POST)
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
    @RequestMapping(value = "buscarArchivo", method = RequestMethod.POST)
    ModelResponse<ArchivoResponse> buscarArchivo(@RequestParam Long idArchivo){
        return seArchivo.buscarArchivo(idArchivo);
    }
    @RequestMapping(value = "crearArchivo", method = RequestMethod.POST)
    MsgResponse crearArchivo(@RequestBody CreaModiArchivoRequest datos){
        return seArchivo.crearArchivo(datos);
    }
    @RequestMapping(value = "editarArchivo", method = RequestMethod.POST)
    MsgResponse editarArchivo(@RequestBody CreaModiArchivoRequest datos){
        return seArchivo.editarArchivo(datos);
    }
    @RequestMapping(value = "eliminarArchivo", method = RequestMethod.POST)
    MsgResponse eliminarArchivo(@RequestParam Long idArchivo){
        return seArchivo.eliminarArchivo(idArchivo);
    }

}
