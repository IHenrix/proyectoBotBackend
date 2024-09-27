package pe.edu.usmp.bot.app.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-microsoft-custom")
public class OAuthController {

	@GetMapping("/login")
	public void redirectToMicrosoft(HttpServletResponse response) throws IOException {
		System.out.println("adfsasdfsdf");
		response.sendRedirect("/oauth2/authorization/azure");
	}

	@GetMapping("/success")
	public String success(@RequestParam(value = "code", required = false) String code,															
			@RequestParam(value = "state", required = false) String state, 
			@RequestParam(value = "error", required = false) String error,									
			@RequestParam(value = "error_description", required = false) String errorDescription) {

		if (error != null) {
			return "Error en la autorización: " + error + ". Descripción: " + errorDescription;
		}

		if (code != null) {
			return "Autorización exitosa. Code: " + code + ", State: " + state;
		}

		return "Solicitud inválida o desconocida.";
	}

}
