package pe.edu.usmp.bot.app.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.edu.usmp.bot.app.config.Auth;
import pe.edu.usmp.bot.app.repository.AccesoInformacionRepository;
import pe.edu.usmp.bot.app.request.AccesoInformacionRequest;
import pe.edu.usmp.bot.app.response.*;
import pe.edu.usmp.bot.app.service.AccesoInformacionService;
import pe.edu.usmp.bot.app.utils.Constantes;
import pe.edu.usmp.bot.app.utils.UtilResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AccesoInformacionServiceImpl implements AccesoInformacionService {

    @Autowired
    private AccesoInformacionRepository repo;

    @Autowired
    private Auth auth;

    @Override
    public ListModelResponse<PieCategoriasResponse> categoriasMasUsadas(AccesoInformacionRequest datos) {
        ListModelResponse<PieCategoriasResponse> resp = new ListModelResponse<>();
        List<PieCategoriasResponse> lista = repo.categoriasMasUsadas(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado resultados");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            ;
            resp.setMensaje("No se han encontrado resultados");
        }
        return resp;
    }

    @Override
    public ListModelResponse<TopDiasConsultasResponse> topDiasConsultas(AccesoInformacionRequest datos) {
        ListModelResponse<TopDiasConsultasResponse> resp = new ListModelResponse<>();
        List<TopDiasConsultasResponse> lista = repo.topDiasConsultas(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado resultados");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            ;
            resp.setMensaje("No se han encontrado resultados");
        }
        return resp;
    }

    @Override
    public ListModelResponse<MesesResponse> listaMesesConsulta(AccesoInformacionRequest datos) {
        ListModelResponse<MesesResponse> resp = new ListModelResponse<>();
        List<MesesResponse> lista = repo.listaMesesConsulta(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado resultados");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            ;
            resp.setMensaje("No se han encontrado resultados");
        }
        return resp;
    }

    @Override
    public ListModelResponse<TopUsuariosConsultasResponse> topUsuariosConsultas(AccesoInformacionRequest datos) {
        ListModelResponse<TopUsuariosConsultasResponse> resp = new ListModelResponse<>();
        List<TopUsuariosConsultasResponse> lista = repo.topUsuariosConsultas(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado resultados");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            ;
            resp.setMensaje("No se han encontrado resultados");
        }
        return resp;
    }
    public static String convertirListaATexto(List<TopUsuariosConsultasCategoriasResponse> lista) {
        StringBuilder sb = new StringBuilder();
        for (TopUsuariosConsultasCategoriasResponse item : lista) {
            sb.append("Categoría: ").append(item.getCategoria()).append("\n");
            sb.append("Consultas: ").append(item.getConsultas()).append("\n");
            sb.append("\n"); // Agrega un salto de línea entre cada elemento
        }
        return sb.toString();
    }

    public List<TopUsuariosConsultasResponse> listaTopUsuariosCategorias(List<TopUsuariosConsultasResponse> data){
        for (TopUsuariosConsultasResponse item : data) {
            if(!item.getLstCategoria().isEmpty()){
                StringBuilder sb = new StringBuilder();
                for (TopUsuariosConsultasCategoriasResponse item1 : item.getLstCategoria()) {
                    sb.append("Categoría: ").append(item1.getCategoria()).append("\n");
                    sb.append("Consultas: ").append(item1.getConsultas()).append("\n");
                    sb.append("\n");
                }
                item.setCategorias(sb.toString());
            }
        }
        return data;
    }

    @Override
    public String exportarAccesoInformacionPDF(AccesoInformacionRequest datos) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<>();
        String logoBase64 = Base64.getEncoder()
                .encodeToString(IOUtils.toByteArray(getClass().getResourceAsStream("/static/images/logo.png")));
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = formatoFecha.format(fechaActual);
        mapParametros.put("logo", logoBase64);
        mapParametros.put("fechaActual", fechaFormateada + " hrs");
        mapParametros.put("usuario", auth.nombreCompleto());
        mapParametros.put("titulo", "titulo");
        DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter nuevoFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicioConvertida = LocalDate.parse(datos.getInicio(), formatoOriginal);
        LocalDate fechaFinConvertida = LocalDate.parse(datos.getFin(), formatoOriginal);
        String fechaInicioFormateada = fechaInicioConvertida.format(nuevoFormato);
        String fechaFinFormateada = fechaFinConvertida.format(nuevoFormato);
        mapParametros.put("fechaInicio", fechaInicioFormateada);
        mapParametros.put("fechaFin", fechaFinFormateada);
        List<Map<String, Object>> listDataMap = new ArrayList<>();
        String archivoReporte="";
        switch (datos.getReporte()){
            case "1":
                listDataMap = UtilResource.convertirDtoAMap(repo.categoriasMasUsadas(datos));
                archivoReporte="ReporteCategoriasMasUsadas";
            break;
            case "2":
                listDataMap = UtilResource.convertirDtoAMap(repo.topDiasConsultas(datos));
                archivoReporte="ReporteDiasMasConsultados";
                break;
            case "3":
                listDataMap = UtilResource.convertirDtoAMap(repo.listaMesesConsulta(datos));
                archivoReporte="ReporteConsultaPorMes";
                break;
            case "4":
                listDataMap = UtilResource.convertirDtoAMap(listaTopUsuariosCategorias(repo.topUsuariosConsultas(datos)));
                archivoReporte="UsuariosConMasConsultas";
                break;
        }
        InputStream jrxmlStream = new ClassPathResource("/static/"+archivoReporte+".jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDataMap);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapParametros, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();
        String base64PDF = Base64.getEncoder().encodeToString(pdfBytes);
        return base64PDF;
    }

    @Override
    public byte[] exportarSupervisionConsultaExcel(AccesoInformacionRequest datos) throws Exception {
        HashMap<String, Object> mapParametros = new HashMap<>();
        String logoBase64 = Base64.getEncoder()
                .encodeToString(IOUtils.toByteArray(getClass().getResourceAsStream("/static/images/logo.png")));
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = formatoFecha.format(fechaActual);
        mapParametros.put("logo", logoBase64);
        mapParametros.put("fechaActual", fechaFormateada + " hrs");
        mapParametros.put("usuario", auth.nombreCompleto());
        mapParametros.put("titulo", "titulo");
        DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter nuevoFormato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicioConvertida = LocalDate.parse(datos.getInicio(), formatoOriginal);
        LocalDate fechaFinConvertida = LocalDate.parse(datos.getFin(), formatoOriginal);
        String fechaInicioFormateada = fechaInicioConvertida.format(nuevoFormato);
        String fechaFinFormateada = fechaFinConvertida.format(nuevoFormato);
        mapParametros.put("fechaInicio", fechaInicioFormateada);
        mapParametros.put("fechaFin", fechaFinFormateada);

        List<Map<String, Object>> listDataMap = new ArrayList<>();

        String archivoReporte="";
        switch (datos.getReporte()){
            case "1":
                listDataMap = UtilResource.convertirDtoAMap(repo.categoriasMasUsadas(datos));
                archivoReporte="ReporteCategoriasMasUsadas";
                break;
            case "2":
                listDataMap = UtilResource.convertirDtoAMap(repo.topDiasConsultas(datos));
                archivoReporte="ReporteDiasMasConsultados";
                break;
            case "3":
                listDataMap = UtilResource.convertirDtoAMap(repo.listaMesesConsulta(datos));
                archivoReporte="ReporteConsultaPorMes";
                break;
            case "4":
                listDataMap = UtilResource.convertirDtoAMap(listaTopUsuariosCategorias(repo.topUsuariosConsultas(datos)));
                archivoReporte="UsuariosConMasConsultas";
                break;
        }
        InputStream jrxmlStream = new ClassPathResource("/static/"+archivoReporte+".jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listDataMap);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapParametros, dataSource);
        JRXlsxExporter exporter = new JRXlsxExporter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        return byteArrayOutputStream.toByteArray();
    }
}
