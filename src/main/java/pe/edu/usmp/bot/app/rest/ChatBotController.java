package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ArchivoDocumentoResponse;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.ChatBotService;

import java.io.IOException;


@RestController
@RequestMapping("chatbot")
public class ChatBotController {

	@Autowired
	private ChatBotService se;

	@RequestMapping(value = "enviarMensaje", method = RequestMethod.POST)
	public ModelResponse<String> enviarMensaje(@RequestBody EnviarMensajeRequest datos) {
		return se.enviarMensaje(datos);
	}

	@RequestMapping(value = "enviarMensajeConArchivo", method = RequestMethod.POST)
	public ModelResponse<String> enviarMensajeConArchivo(@RequestParam("mensaje")  String mensaje,@RequestParam("prompt") String prompt,@RequestParam("archivo") MultipartFile archivo){
		EnviarMensajeRequest request= new EnviarMensajeRequest();
		request.setMensaje(mensaje);
		request.setPrompt(prompt);
		return se.enviarMensajeConArchivo(request,archivo);
	}
	@RequestMapping(value = "buscarGuias", method = RequestMethod.POST)
	public ListModelResponse<ArchivoDocumentoResponse> buscarGuias(@RequestBody EnviarMensajeRequest datos) throws IOException {
		return se.buscarGuias(datos);
	}
}
