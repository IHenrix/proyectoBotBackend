package pe.edu.usmp.bot.app.request;

public class CreaModiArchivoRequest {
    private Long id;
    private String nombre;
    private byte[] documento;
    private String descripcion;
    private String tipo;
    private Long idTipoArchivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdTipoArchivo() {
        return idTipoArchivo;
    }

    public void setIdTipoArchivo(Long idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }
}
