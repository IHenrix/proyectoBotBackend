package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.AccesoInformacionRequest;
import pe.edu.usmp.bot.app.response.MesesResponse;
import pe.edu.usmp.bot.app.response.PieCategoriasResponse;
import pe.edu.usmp.bot.app.response.TopDiasConsultasResponse;
import pe.edu.usmp.bot.app.response.TopUsuariosConsultasResponse;

import java.util.List;

public interface AccesoInformacionRepository {

    List<PieCategoriasResponse> categoriasMasUsadas(AccesoInformacionRequest datos);

    List<TopDiasConsultasResponse> topDiasConsultas(AccesoInformacionRequest datos);

    List<MesesResponse> listaMesesConsulta(AccesoInformacionRequest datos);

    List<TopUsuariosConsultasResponse> topUsuariosConsultas(AccesoInformacionRequest datos);
}
