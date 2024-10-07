package pe.edu.usmp.bot.app.response;

public class ListaSupervisionConsultasResponse {
    private Long id_persona;
    private String nombre;
    private Long id_categoria;
    private String categoria;
    private Integer total_consultas;
    private String ultima_fecha;

    public Long getId_persona() {
        return id_persona;
    }

    public void setId_persona(Long id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getTotal_consultas() {
        return total_consultas;
    }

    public void setTotal_consultas(Integer total_consultas) {
        this.total_consultas = total_consultas;
    }

    public String getUltima_fecha() {
        return ultima_fecha;
    }

    public void setUltima_fecha(String ultima_fecha) {
        this.ultima_fecha = ultima_fecha;
    }
}
