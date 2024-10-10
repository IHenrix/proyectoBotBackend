package pe.edu.usmp.bot.app.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import pe.edu.usmp.bot.app.repository.AccesoInformacionRepository;
import pe.edu.usmp.bot.app.request.AccesoInformacionRequest;
import pe.edu.usmp.bot.app.response.*;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccesoInformacionRepositoryImpl extends JdbcDaoSupport implements AccesoInformacionRepository {

    @Autowired
    private ApplicationContext context;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void DataSource(DataSource setDataSource) {
        setDataSource(setDataSource);
        this.jdbcTemplate = context.getBean("db_desa", JdbcTemplate.class);
    }

    @Override
    public List<PieCategoriasResponse> categoriasMasUsadas(AccesoInformacionRequest datos) {
        String sql = "SELECT ca.id, ca.nombre as name, COUNT(*) as y , false as sliced\n" +
                "FROM consultas c\n" +
                "INNER JOIN categoria ca ON c.id_categoria = ca.id\n" +
                "WHERE c.fecha_creacion BETWEEN '"+datos.getInicio()+" 00:00:00' AND '"+datos.getFin()+" 23:59:59'" +
                " GROUP BY ca.id, ca.nombre ORDER BY Y DESC";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(PieCategoriasResponse.class));
    }

    @Override
    public List<TopDiasConsultasResponse> topDiasConsultas(AccesoInformacionRequest datos) {
        String sql ="SELECT DATE_FORMAT(fecha_creacion, '%d/%m/%Y') AS dias, COUNT(*) AS consultas\n" +
                "FROM consultas\n" +
                "WHERE fecha_creacion BETWEEN '"+datos.getInicio()+" 00:00:00' AND '"+datos.getFin()+" 23:59:59'\n" +
                "GROUP BY dias\n" +
                "ORDER BY consultas DESC\n" +
                "LIMIT 10";
        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(TopDiasConsultasResponse.class));
    }

    @Override
    public List<MesesResponse> listaMesesConsulta(AccesoInformacionRequest datos) {
       String sql="SELECT \n" +
               "    MONTH(fecha_creacion) AS nro,\n" +
               "    CONCAT(DATE_FORMAT(fecha_creacion, '%M'), ' ', YEAR(fecha_creacion)) AS mes,\n" +
               "    COUNT(*) AS consultas\n" +
               "FROM consultas\n" +
               "WHERE fecha_creacion BETWEEN '"+datos.getInicio()+" 00:00:00' AND '"+datos.getFin()+" 23:59:59'\n" +
               "GROUP BY nro, mes\n" +
               "ORDER BY nro\n" +
               "LIMIT 12";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(MesesResponse.class));
    }

    @Override
    public List<TopUsuariosConsultasResponse> topUsuariosConsultas(AccesoInformacionRequest datos) {
       String sqlUsuario="SELECT p.id , CONCAT(p.apellido_paterno, ' ', p.apellido_materno,' ',p.nombre) AS nombre,\n" +
               "       COUNT(*) AS consultas\n" +
               "FROM consultas c\n" +
               "         INNER JOIN persona p ON c.id_persona = p.id\n" +
               "WHERE c.fecha_creacion BETWEEN '"+datos.getInicio()+" 00:00:00' AND '"+datos.getFin()+" 23:59:59'\n" +
               "GROUP BY id_persona ORDER BY consultas desc LIMIT 5";

        List<TopUsuariosConsultasResponse> lstUsuario=jdbcTemplate.query(sqlUsuario,
                BeanPropertyRowMapper.newInstance(TopUsuariosConsultasResponse.class));
        if(!lstUsuario.isEmpty()){
            for(TopUsuariosConsultasResponse usu:lstUsuario){
                String sqlCategorias="SELECT \n" +
                        "       ca.id AS id_categoria,\n" +
                        "       ca.nombre AS categoria,\n" +
                        "       COUNT(*) AS consultas\n" +
                        "FROM consultas c\n" +
                        "         INNER JOIN persona p ON c.id_persona = p.id\n" +
                        "         INNER JOIN categoria ca ON c.id_categoria = ca.id\n" +
                        "WHERE p.id="+usu.getId()+" AND  c.fecha_creacion BETWEEN '"+datos.getInicio()+" 00:00:00' AND '"+datos.getFin()+" 23:59:59'\n" +
                        "GROUP BY id_categoria, categoria\n" +
                        "ORDER BY consultas ASC";
                List<TopUsuariosConsultasCategoriasResponse> lstCategoria=jdbcTemplate.query(sqlCategorias,
                        BeanPropertyRowMapper.newInstance(TopUsuariosConsultasCategoriasResponse.class));
                usu.setLstCategoria(lstCategoria);
            }
        }
        return lstUsuario;
    }
}
