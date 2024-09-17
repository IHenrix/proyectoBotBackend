package pe.edu.usmp.bot.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import pe.edu.usmp.bot.app.repository.ArchivoRepository;
import pe.edu.usmp.bot.app.request.CreaModiArchivoRequest;
import pe.edu.usmp.bot.app.request.ListarArchivosRequest;
import pe.edu.usmp.bot.app.response.ArchivoDocumentoResponse;
import pe.edu.usmp.bot.app.response.ArchivoResponse;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("deprecation")
public class ArchivoRepositoryImpl extends JdbcDaoSupport implements ArchivoRepository {
    @Autowired
    private ApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DataSource(DataSource setDataSource) {
        setDataSource(setDataSource);
        this.jdbcTemplate = context.getBean("db_desa", JdbcTemplate.class);
    }
	@Override
    public List<ArchivoResponse> listarArchivos(ListarArchivosRequest datos) {
        StringBuilder sql = new StringBuilder("SELECT id,nombre,descripcion,tipo,id_tipo_archivo FROM archivo WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (datos.getIdTipoArchivo() != null) {
            sql.append(" AND id_tipo_archivo = ?");
            params.add(datos.getIdTipoArchivo());
        }
        sql.append(" ORDER BY fecha_creacion ASC");
        return jdbcTemplate.query(sql.toString(), params.toArray(), BeanPropertyRowMapper.newInstance(ArchivoResponse.class));
    }

    @Override
    public ArchivoResponse buscarArchivo(Long idArchivo) {
        try {
            String sql = "SELECT id,nombre,nombre_archivo,descripcion,tipo,id_tipo_archivo FROM archivo WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{idArchivo}, BeanPropertyRowMapper.newInstance(ArchivoResponse.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public void crearArchivo(CreaModiArchivoRequest datos) {
        String sql = "INSERT INTO archivo (nombre,nombre_archivo, documento, descripcion, tipo, id_tipo_archivo) VALUES (?, ?, ?, ?, ?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, datos.getNombre());
            ps.setString(2, datos.getNombreArchivo());
            ps.setBytes(3, datos.getDocumento());
            ps.setString(4, datos.getDescripcion());
            ps.setString(5, datos.getTipo());
            ps.setLong(6, datos.getIdTipoArchivo());
            return ps;
        });
    }

    @Override
    public void editarArchivo(CreaModiArchivoRequest archivo) {
       if(archivo.getDocumento()!=null) {
           String sql = "UPDATE archivo SET nombre = ?,nombre_archivo = ?, documento = ?, descripcion = ?, tipo = ?, id_tipo_archivo = ? WHERE id = ?";
           jdbcTemplate.update(connection -> {
               PreparedStatement ps = connection.prepareStatement(sql);
               ps.setString(1, archivo.getNombre());
               ps.setString(2, archivo.getNombreArchivo());
               ps.setBytes(3, archivo.getDocumento()); // Campo BLOB
               ps.setString(4, archivo.getDescripcion());
               ps.setString(5, archivo.getTipo());
               ps.setLong(6, archivo.getIdTipoArchivo());
               ps.setLong(7, archivo.getId());
               return ps;
           });
       }
       else{
           String sql = "UPDATE archivo SET nombre = ? , descripcion = ?, id_tipo_archivo = ? WHERE id = ?";
           jdbcTemplate.update(connection -> {
               PreparedStatement ps = connection.prepareStatement(sql);
               ps.setString(1, archivo.getNombre());
               ps.setString(2, archivo.getDescripcion());
               ps.setLong(3, archivo.getIdTipoArchivo());
               ps.setLong(4, archivo.getId());
               return ps;
           });
       }
    }
    @Override
    public void eliminarArchivo(Long idArchivo) {
        String sql = "DELETE FROM archivo WHERE id = ?";
        jdbcTemplate.update(sql, idArchivo);
    }

    @Override
    public byte[] obtenerDocumento(Long idArchivo) {
        try {
            String sql = "SELECT documento FROM archivo WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{idArchivo}, byte[].class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ArchivoDocumentoResponse> listarArchivoDocumento() {
        String sql = "SELECT id,nombre,nombre_archivo,descripcion,tipo,id_tipo_archivo FROM archivo";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ArchivoDocumentoResponse archivo = new ArchivoDocumentoResponse();
            archivo.setId(rs.getInt("id"));
            archivo.setNombre(rs.getString("nombre"));
            archivo.setNombre_archivo(rs.getString("nombre_archivo"));
            archivo.setDescripcion(rs.getString("descripcion"));
            archivo.setTipo(rs.getString("tipo"));
            return archivo;
        });
    }
}
