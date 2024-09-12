package pe.edu.usmp.bot.app.model;

public class Archivo {
    private int id;
    private String nombre;
    private byte[] documento;
    private String descripcion;
    private String tipo;
    private int idTipoArchivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
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

    public int getIdTipoArchivo() {
        return idTipoArchivo;
    }

    public void setIdTipoArchivo(int idTipoArchivo) {
        this.idTipoArchivo = idTipoArchivo;
    }
}
