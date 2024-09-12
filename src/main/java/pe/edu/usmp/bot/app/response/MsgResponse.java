package pe.edu.usmp.bot.app.response;

import pe.edu.usmp.bot.app.utils.Constantes;

public class MsgResponse extends GenericResponse {
	private String mensajeTxt;
	private String icon;

	public MsgResponse() {
		super.setCod(-1);
		this.mensajeTxt =Constantes.NAME_SYSTEM;
		this.icon = "error";
	}

	public String getMensajeTxt() {
		return mensajeTxt;
	}

	public void setMensajeTxt(String mensajeTxt) {
		this.mensajeTxt = mensajeTxt;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
