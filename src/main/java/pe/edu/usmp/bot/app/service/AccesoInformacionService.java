package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.request.AccesoInformacionRequest;
import pe.edu.usmp.bot.app.response.*;

public interface AccesoInformacionService {
    ListModelResponse<PieCategoriasResponse> categoriasMasUsadas(AccesoInformacionRequest datos);

    ListModelResponse<TopDiasConsultasResponse> topDiasConsultas(AccesoInformacionRequest datos);

    ListModelResponse<MesesResponse> listaMesesConsulta(AccesoInformacionRequest datos);

    ListModelResponse<TopUsuariosConsultasResponse> topUsuariosConsultas(AccesoInformacionRequest datos);

    String exportarAccesoInformacionPDF(AccesoInformacionRequest datos) throws Exception;

    byte[] exportarSupervisionConsultaExcel(AccesoInformacionRequest datos) throws Exception;
}
