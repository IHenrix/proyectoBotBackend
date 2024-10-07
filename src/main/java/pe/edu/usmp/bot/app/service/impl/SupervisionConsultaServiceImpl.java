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
import pe.edu.usmp.bot.app.repository.SupervisionConsultaRepository;
import pe.edu.usmp.bot.app.request.ListaSupervisionConsultasRequest;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ListaConsultasDetalleResponse;
import pe.edu.usmp.bot.app.response.ListaSupervisionConsultasResponse;
import pe.edu.usmp.bot.app.service.SupervisionConsultaService;
import pe.edu.usmp.bot.app.utils.Constantes;
import pe.edu.usmp.bot.app.utils.UtilResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SupervisionConsultaServiceImpl implements SupervisionConsultaService {

    @Autowired
    private SupervisionConsultaRepository repo;

    @Autowired
    private Auth auth;

    @Override
    public ListModelResponse<ListaSupervisionConsultasResponse> listaConsultas(ListaSupervisionConsultasRequest datos) {
        ListModelResponse<ListaSupervisionConsultasResponse> resp = new ListModelResponse<>();
        List<ListaSupervisionConsultasResponse> lista = repo.listaConsultas(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado consultas");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            ;
            resp.setMensaje("No se han encontrado consultas");
        }
        return resp;
    }

    @Override
    public ListModelResponse<ListaConsultasDetalleResponse> listaConsultaDetalle(ListaSupervisionConsultasRequest datos) {
        ListModelResponse<ListaConsultasDetalleResponse> resp = new ListModelResponse<>();
        List<ListaConsultasDetalleResponse> lista = repo.listaConsultaDetalle(datos);
        if (!lista.isEmpty()) {
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setMensaje("Se ha encontrado detalle de consulta");
            resp.setList(lista);
        } else {
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_INFO);
            resp.setMensaje("No se han encontrado detalle de consulta");
        }
        return resp;
    }

    @Override
    public String exportarSupervisionConsultaPDF(ListaSupervisionConsultasRequest datos) throws Exception {
        List<ListaConsultasDetalleResponse> lista = repo.listaConsultaDetalle(datos);
        HashMap<String, Object> mapParametros = new HashMap<>();
        String logoBase64 = Base64.getEncoder()
                .encodeToString(IOUtils.toByteArray(getClass().getResourceAsStream("/static/images/logo.png")));
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = formatoFecha.format(fechaActual);
        mapParametros.put("logo", logoBase64);
        mapParametros.put("fechaActual", fechaFormateada + " hrs");
        mapParametros.put("usuario", auth.nombreCompleto());
        mapParametros.put("categoria", datos.getCategoriaDescripcion());
        mapParametros.put("estudiante", datos.getUsuarioNombre());
        List<Map<String, Object>> listDataMap = UtilResource.convertirDtoAMap(lista);
        InputStream jrxmlStream = new ClassPathResource("/static/SupervisionConsultas.jrxml").getInputStream();
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
    public byte[] exportarSupervisionConsultaExcel(ListaSupervisionConsultasRequest datos)  throws Exception {
        List<ListaConsultasDetalleResponse> lista = repo.listaConsultaDetalle(datos);
        HashMap<String, Object> mapParametros = new HashMap<>();
        String logoBase64 = Base64.getEncoder()
                .encodeToString(IOUtils.toByteArray(getClass().getResourceAsStream("/static/images/logo.png")));
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = formatoFecha.format(fechaActual);
        mapParametros.put("logo", logoBase64);
        mapParametros.put("fechaActual", fechaFormateada+" hrs");
        mapParametros.put("usuario", auth.nombreCompleto());
        mapParametros.put("categoria",datos.getCategoriaDescripcion());
        mapParametros.put("estudiante", datos.getUsuarioNombre());
        mapParametros.put("IS_EXCEL_EXPORT", Boolean.TRUE);
        mapParametros.put(JRParameter.IS_IGNORE_PAGINATION, true);
        List<Map<String, Object>> listDataMap = UtilResource.convertirDtoAMap(lista);
        InputStream jrxmlStream = new ClassPathResource("/static/SupervisionConsultas.jrxml").getInputStream();
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
