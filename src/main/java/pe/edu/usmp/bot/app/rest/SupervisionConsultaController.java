package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ListaConsultasDetalleResponse;
import pe.edu.usmp.bot.app.response.ListaSupervisionConsultasResponse;
import pe.edu.usmp.bot.app.service.SupervisionConsultaService;

@RestController
@RequestMapping("consultas")
public class SupervisionConsultaController {

    @Autowired
    private SupervisionConsultaService se;

    @RequestMapping(value = "listaConsultas", method = RequestMethod.POST)
    public ListModelResponse<ListaSupervisionConsultasResponse> listaConsultas(@RequestBody ListaSupervisionConsultasRequest datos){
        return se.listaConsultas(datos);
    }
    @RequestMapping(value = "listaConsultaDetalle", method = RequestMethod.POST)
    public ListModelResponse<ListaConsultasDetalleResponse> listaConsultaDetalle(@RequestBody ListaSupervisionConsultasRequest datos){
        return se.listaConsultaDetalle(datos);
    }
    @RequestMapping(value = "exportarSupervisionConsultaPDF", method = RequestMethod.POST)
    public ResponseEntity<String> exportarSupervisionConsultaPDF(@RequestBody ListaSupervisionConsultasRequest datos) throws Exception {
        String reportePdf = se.exportarSupervisionConsultaPDF(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=SupervisionConsulta.pdf");
        return ResponseEntity.ok().headers(headers).body(reportePdf);
    }
    @RequestMapping(value = "exportarSupervisionConsultaExcel", method = RequestMethod.POST)
    public ResponseEntity<byte []> exportarSupervisionConsultaExcel(@RequestBody ListaSupervisionConsultasRequest datos)  throws Exception{
        byte [] response= se.exportarSupervisionConsultaExcel(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SupervisionConsulta.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
