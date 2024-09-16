package pe.edu.usmp.bot.app.model;

public class Guia {
	private Long id;
    private String descripcion;
    private byte[] recurso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getRecurso() {
        return recurso;
    }

    public void setRecurso(byte[] recurso) {
        this.recurso = recurso;
    }
}
