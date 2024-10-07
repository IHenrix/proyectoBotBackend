package pe.edu.usmp.bot.app.repository;

import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.CodNombreResponse;
import pe.edu.usmp.bot.app.response.ListaConsultasDetalleResponse;
import pe.edu.usmp.bot.app.response.ListaSupervisionConsultasResponse;

import java.util.List;

public interface SupervisionConsultaRepository {

    List<ListaSupervisionConsultasResponse> listaConsultas(ListaSupervisionConsultasRequest datos);

    List<ListaConsultasDetalleResponse> listaConsultaDetalle(ListaSupervisionConsultasRequest datos);
}
