package pe.edu.usmp.bot.app.request;

public class EnviarMensajeRequest {
	
	private String mensaje;

	private String prompt;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
