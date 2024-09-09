package pe.edu.usmp.bot.app.model;

import java.util.List;

public class AuthModel {

	private Long co_usu;
	private String usuario_red;
	private String nombre_completo;
	private String nombre;
	private String paterno;
	private String materno;
	private String lib_elect;
	private String zona;
	private String sexo;
	private String telf_cel;
	private String correo;
	private Long co_modalidad;
	private String modalidad;
	// private String co_trab;
	private String grupo_planilla;
	private String area;
	private Long co_area;
	private List<String> roles;

	public Long getCo_usu() {
		return co_usu;
	}

	public void setCo_usu(Long co_usu) {
		this.co_usu = co_usu;
	}

	public String getUsuario_red() {
		return usuario_red;
	}

	public void setUsuario_red(String usuario_red) {
		this.usuario_red = usuario_red;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public String getLib_elect() {
		return lib_elect;
	}

	public void setLib_elect(String lib_elect) {
		this.lib_elect = lib_elect;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelf_cel() {
		return telf_cel;
	}

	public void setTelf_cel(String telf_cel) {
		this.telf_cel = telf_cel;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Long getCo_modalidad() {
		return co_modalidad;
	}

	public void setCo_modalidad(Long co_modalidad) {
		this.co_modalidad = co_modalidad;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getGrupo_planilla() {
		return grupo_planilla;
	}

	public void setGrupo_planilla(String grupo_planilla) {
		this.grupo_planilla = grupo_planilla;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Long getCo_area() {
		return co_area;
	}

	public void setCo_area(Long co_area) {
		this.co_area = co_area;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
