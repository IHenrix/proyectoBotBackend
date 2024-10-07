package pe.edu.usmp.bot.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import pe.edu.usmp.bot.app.repository.SupervisionConsultaRepository;
import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.ListaConsultasDetalleResponse;
import pe.edu.usmp.bot.app.response.ListaSupervisionConsultasResponse;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("deprecation")
public class SupervisionConsultaRepositoryImpl  extends JdbcDaoSupport implements SupervisionConsultaRepository {

    @Autowired
    private ApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DataSource(DataSource setDataSource) {
        setDataSource(setDataSource);
        this.jdbcTemplate = context.getBean("db_desa", JdbcTemplate.class);
    }
    @Override
    public List<ListaSupervisionConsultasResponse> listaConsultas(ListaSupervisionConsultasRequest datos) {
        StringBuilder sql = new StringBuilder(
        "SELECT p.id AS id_persona, CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS nombre,\n" +
                "\t   ca.id AS id_categoria,\n" +
                "       ca.nombre AS categoria,\n" +
                "       COUNT(*) AS total_consultas,\n" +
                "       DATE_FORMAT(MAX(c.fecha_creacion), '%d/%m/%Y %H:%i:%s') AS ultima_fecha\n" +
                "FROM consultas c\n" +
                "         INNER JOIN persona p ON c.id_persona = p.id\n" +
                "         INNER JOIN categoria ca ON c.id_categoria = ca.id WHERE 1=1 ");

        List<Object> params = new ArrayList<>();
        if (datos.getCategoria() != null && !datos.getCategoria().isEmpty()) {
            sql.append(" AND ca.id = ?");
            params.add(datos.getCategoria());
        }

        if (datos.getUsuario() != null && !datos.getUsuario().isEmpty()) {
            sql.append(" AND p.id = ?");
            params.add(datos.getUsuario());
        }
        sql.append(" GROUP BY id_persona,nombre,id_categoria, categoria ORDER BY nombre ASC");

        return jdbcTemplate.query(sql.toString(), params.toArray(),
                BeanPropertyRowMapper.newInstance(ListaSupervisionConsultasResponse.class));
    }

    @Override
    public List<ListaConsultasDetalleResponse> listaConsultaDetalle(ListaSupervisionConsultasRequest datos) {
        StringBuilder sql = new StringBuilder("SELECT CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS nombre,\n" +
                "       ca.nombre AS categoria,\n" +
                "       c.msj_persona pregunta,\n" +
                "       c.msj_bot respuesta,\n" +
                "       DATE_FORMAT(c.fecha_creacion, '%d/%m/%Y %H:%i:%s') AS fecha\n" +
                "FROM consultas c\n" +
                "         INNER JOIN persona p ON c.id_persona = p.id\n" +
                "         INNER JOIN categoria ca ON c.id_categoria = ca.id WHERE 1=1 ");

        List<Object> params = new ArrayList<>();
        if (datos.getCategoria() != null && !datos.getCategoria().isEmpty()) {
            sql.append(" AND ca.id = ?");
            params.add(datos.getCategoria());
        }

        if (datos.getUsuario() != null && !datos.getUsuario().isEmpty()) {
            sql.append(" AND p.id = ?");
            params.add(datos.getUsuario());
        }
        sql.append(" ORDER BY fecha,pregunta ASC");
        System.out.println(sql.toString());
        return jdbcTemplate.query(sql.toString(), params.toArray(),
                BeanPropertyRowMapper.newInstance(ListaConsultasDetalleResponse.class));
    }
}
