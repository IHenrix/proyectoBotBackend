package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.request.ListarUsuarioRequest;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.MsgResponse;
import pe.edu.usmp.bot.app.response.PersonaResponse;
import pe.edu.usmp.bot.app.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService se;

    @RequestMapping(value = "listarUsuarios", method = RequestMethod.POST)
    ListModelResponse<PersonaResponse> listarUsuarios(@RequestBody ListarUsuarioRequest datos){
        return se.listarUsuarios(datos);
    }

    @RequestMapping(value = "crearUsuario", method = RequestMethod.POST)
    public MsgResponse crearUsuario(@RequestBody CreaModiUsuarioRequest datos) {
        return se.crearUsuario(datos);
    }
    @RequestMapping(value = "editarUsuario", method = RequestMethod.POST)
    public MsgResponse editarUsuario(@RequestBody CreaModiUsuarioRequest datos){
        return se.editarUsuario(datos);
    }
    @RequestMapping(value = "eliminarUsuario", method = RequestMethod.POST)
    public MsgResponse eliminarUsuario(@RequestParam Long usuarioId){
        return se.eliminarUsuario(usuarioId);
    }


}
