package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.ChatBotService;


@RestController
@RequestMapping("chatbot")
public class ChatBotController {

	@Autowired
	private ChatBotService se;

	@RequestMapping(value = "enviarMensaje", method = RequestMethod.POST)
	public ModelResponse<String> enviarMensaje(@RequestBody EnviarMensajeRequest datos) {
		return se.enviarMensaje(datos);
	}
	

}
