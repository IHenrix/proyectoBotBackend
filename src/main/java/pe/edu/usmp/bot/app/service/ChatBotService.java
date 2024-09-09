package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;

public interface ChatBotService {
	public ModelResponse<String> enviarMensaje(EnviarMensajeRequest datos);
}
