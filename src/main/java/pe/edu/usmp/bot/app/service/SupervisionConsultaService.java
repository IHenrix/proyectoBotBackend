package pe.edu.usmp.bot.app.service;

import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ListaConsultasDetalleResponse;
import pe.edu.usmp.bot.app.response.ListaSupervisionConsultasResponse;

public interface SupervisionConsultaService {

    ListModelResponse<ListaSupervisionConsultasResponse> listaConsultas(ListaSupervisionConsultasRequest datos);

    ListModelResponse<ListaConsultasDetalleResponse> listaConsultaDetalle(ListaSupervisionConsultasRequest datos);

    String exportarSupervisionConsultaPDF(ListaSupervisionConsultasRequest datos)  throws Exception;

    byte[] exportarSupervisionConsultaExcel(ListaSupervisionConsultasRequest datos)  throws Exception;

}
