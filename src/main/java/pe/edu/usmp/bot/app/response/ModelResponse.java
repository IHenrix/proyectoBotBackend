package pe.edu.usmp.bot.app.response;

import pe.edu.usmp.bot.app.utils.Constantes;

public class ModelResponse<T> extends GenericResponse {
	private String mensajeTxt;
	private String icon;
	private T model;

	public ModelResponse() {
		super.setCod(-1);
		this.mensajeTxt = Constantes.NAME_SYSTEM;
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

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
	
	
}
