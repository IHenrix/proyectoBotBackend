package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.usmp.bot.app.request.CreaModiUsuarioRequest;
import pe.edu.usmp.bot.app.response.MsgResponse;
import pe.edu.usmp.bot.app.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService se;

    @RequestMapping(value = "crearUsuario", method = RequestMethod.POST)
    public MsgResponse crearUsuario(@RequestBody CreaModiUsuarioRequest datos) {
        return se.crearUsuario(datos);
    }
    @RequestMapping(value = "editarUsuario", method = RequestMethod.POST)
    public MsgResponse editarUsuario(@RequestBody CreaModiUsuarioRequest datos){
        return se.editarUsuario(datos);
    }


}
