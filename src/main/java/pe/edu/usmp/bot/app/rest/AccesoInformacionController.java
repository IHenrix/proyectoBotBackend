package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.usmp.bot.app.request.AccesoInformacionRequest;
import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AccesoInformacionService;

@RestController
@RequestMapping("/acceso-informacion")
public class AccesoInformacionController {
    @Autowired
    private AccesoInformacionService se;
    @RequestMapping(value = "categoriasMasUsadas", method = RequestMethod.POST)
    public ListModelResponse<PieCategoriasResponse> categoriasMasUsadas(@RequestBody AccesoInformacionRequest datos){
        return se.categoriasMasUsadas(datos);
    }
    @RequestMapping(value = "topDiasConsultas", method = RequestMethod.POST)
    public ListModelResponse<TopDiasConsultasResponse> topDiasConsultas(@RequestBody AccesoInformacionRequest datos){
        return se.topDiasConsultas(datos);
    }
    @RequestMapping(value = "listaMesesConsulta", method = RequestMethod.POST)
    public ListModelResponse<MesesResponse> listaMesesConsulta(@RequestBody AccesoInformacionRequest datos){
        return se.listaMesesConsulta(datos);
    }
    @RequestMapping(value = "topUsuariosConsultas", method = RequestMethod.POST)
    public ListModelResponse<TopUsuariosConsultasResponse> topUsuariosConsultas(@RequestBody AccesoInformacionRequest datos){
        return se.topUsuariosConsultas(datos);
    }

    @RequestMapping(value = "exportarAccesoInformacionPDF", method = RequestMethod.POST)
    public ResponseEntity<String> exportarAccesoInformacionPDF(@RequestBody AccesoInformacionRequest datos) throws Exception {
        String reportePdf =se.exportarAccesoInformacionPDF(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Reporte.pdf");
        return ResponseEntity.ok().headers(headers).body(reportePdf);
    }

    @RequestMapping(value = "exportarAccesoInformacionExcel", method = RequestMethod.POST)
    public ResponseEntity<byte []> exportarSupervisionConsultaExcel(@RequestBody AccesoInformacionRequest datos)  throws Exception{
        byte [] response= se.exportarSupervisionConsultaExcel(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

}
