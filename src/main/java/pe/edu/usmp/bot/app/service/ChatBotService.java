package pe.edu.usmp.bot.app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ArchivoDocumentoResponse;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;

import java.io.IOException;

public interface ChatBotService {
	ModelResponse<String> enviarMensaje(EnviarMensajeRequest datos);

	ModelResponse<String> enviarMensajeConArchivo(EnviarMensajeRequest datos, MultipartFile archivo);

	ListModelResponse<ArchivoDocumentoResponse> buscarGuias(EnviarMensajeRequest datos) throws IOException;
}
