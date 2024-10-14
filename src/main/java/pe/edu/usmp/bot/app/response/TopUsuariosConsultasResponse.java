package pe.edu.usmp.bot.app.response;

import java.util.List;

public class TopUsuariosConsultasResponse {
    private Long id;
    private String nombre;
    private Integer consultas;
    private List<TopUsuariosConsultasCategoriasResponse> lstCategoria;
    private String categorias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getConsultas() {
        return consultas;
    }

    public void setConsultas(Integer consultas) {
        this.consultas = consultas;
    }

    public List<TopUsuariosConsultasCategoriasResponse> getLstCategoria() {
        return lstCategoria;
    }

    public void setLstCategoria(List<TopUsuariosConsultasCategoriasResponse> lstCategoria) {
        this.lstCategoria = lstCategoria;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }
}
