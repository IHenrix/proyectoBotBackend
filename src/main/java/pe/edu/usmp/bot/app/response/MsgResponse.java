package pe.edu.usmp.bot.app.response;

import pe.edu.usmp.bot.app.utils.Constantes;

public class MsgResponse extends GenericResponse {
	private String mensaje_txt;
	private String icon;

	public MsgResponse() {
		super.setCod(-1);
		this.mensaje_txt =Constantes.NAME_SYSTEM;
		this.icon = "error";
	}

	public String getMensaje_txt() {
		return mensaje_txt;
	}

	public void setMensaje_txt(String mensaje_txt) {
		this.mensaje_txt = mensaje_txt;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
