package pe.edu.usmp.bot.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.repository.ArchivoRepository;
import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ArchivoDocumentoResponse;
import pe.edu.usmp.bot.app.response.ChatGptResponse;
import pe.edu.usmp.bot.app.response.ListModelResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.ChatBotService;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import pe.edu.usmp.bot.app.utils.BlobToPDFConverter;
import pe.edu.usmp.bot.app.utils.Constantes;

@Service
public class ChatBotServiceImpl implements ChatBotService {

	@Value("${openai.api.key}")
	private String openaiApiKey;

	@Value("${openai.api.url}")
	private String openaiApiUrl;

	@Value("${openai.api.content}")
	private String openaiApiUrlContent;

	@Autowired
	private ArchivoRepository archivoRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ModelResponse<String> enviarMensaje(EnviarMensajeRequest datos) {

		ModelResponse<String> resp = new ModelResponse<>();
		ChatGptResponse beanResponse = new ChatGptResponse();
		String result = "";
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// URL a la que se enviará la solicitud
			String openaiApiUrlContentData = openaiApiUrlContent;
			if (datos.getPrompt() != null) {
				openaiApiUrlContentData = datos.getPrompt();
			}

			// Cabeceras
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + openaiApiKey);

			// Crear el cuerpo del JSON con ObjectMapper
			Map<String, Object> requestBody = new HashMap<>();
			requestBody.put("model", "gpt-4");

			List<Map<String, String>> messages = new ArrayList<>();
			Map<String, String> systemMessage = new HashMap<>();
			systemMessage.put("role", "system");
			systemMessage.put("content", openaiApiUrlContentData + ".");

			Map<String, String> userMessage = new HashMap<>();
			userMessage.put("role", "user");
			userMessage.put("content", datos.getMensaje());

			messages.add(systemMessage);
			messages.add(userMessage);

			requestBody.put("messages", messages);
			requestBody.put("temperature", 0.7);
			requestBody.put("max_tokens", 150);
			requestBody.put("top_p", 1);
			requestBody.put("frequency_penalty", 0);
			requestBody.put("presence_penalty", 0);

			// Serializar el cuerpo del JSON
			String jsonRequest = objectMapper.writeValueAsString(requestBody);

			// Crear la entidad de la solicitud HTTP
			HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

			// Realizar la solicitud POST a la API de OpenAI
			result = restTemplate.postForObject(openaiApiUrl, requestEntity, String.class);

			// Deserializar la respuesta JSON
			beanResponse = objectMapper.readValue(result, ChatGptResponse.class);

		} catch (JsonProcessingException e) {
			// Capturar excepciones relacionadas con la serialización/deserialización de JSON
			e.printStackTrace();
			resp.setCod(0);
			resp.setMensaje("Error en el procesamiento de JSON: " + e.getMessage());
			return resp;
		} catch (ConnectException e) {
			e.printStackTrace();
			resp.setCod(0);
			resp.setMensaje("Error de conexión: " + e.getMessage());
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCod(0);
			resp.setMensaje("Error inesperado: " + e.getMessage());
			return resp;
		}

		// Configuración de la respuesta
		resp.setCod(1);
		resp.setMensaje("CHATGPT RESPONDIO SATISFACTORIAMENTE");
		resp.setModel(beanResponse.getChoices().get(0).getMessage().getContent());
		return resp;
	}

	@Override
	public ModelResponse<String> enviarMensajeConArchivo(String mensaje, MultipartFile archivo) {

		ModelResponse<String> resp = new ModelResponse<>();
		ChatGptResponse beanResponse = new ChatGptResponse();
		String result = "";
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + openaiApiKey);

			String fileContent = extraerTextoDePDF(archivo);

			List<String> textChunks = splitTextIntoChunks(fileContent, 4096);  // Límite de 4096 tokens por solicitud

			RestTemplate restTemplate = new RestTemplate();

			// Iterar sobre los fragmentos y enviar las solicitudes de forma secuencial
			for (String chunk : textChunks) {
				Map<String, Object> requestBody = new HashMap<>();
				requestBody.put("model", "gpt-4");
				requestBody.put("temperature", 0.7);
				requestBody.put("max_tokens", 1000);
				requestBody.put("top_p", 1);
				requestBody.put("frequency_penalty", 0);
				requestBody.put("presence_penalty", 0);

				List<Map<String, String>> messages = List.of(
						Map.of("role", "system", "content", "Se que no analizas documentos , pero necesito que solo revises el texto extraido de este pdf"),
						Map.of("role", "user", "content", mensaje + " Analiza " + chunk)
				);
				requestBody.put("messages", messages);

				String requestBodyJson = objectMapper.writeValueAsString(requestBody);

				HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);

				result = restTemplate.postForObject(openaiApiUrl, requestEntity, String.class);

				beanResponse = objectMapper.readValue(result, ChatGptResponse.class);

				Thread.sleep(1000);

				if (beanResponse != null && beanResponse.getChoices() != null && !beanResponse.getChoices().isEmpty()) {
					resp.setModel(beanResponse.getChoices().get(0).getMessage().getContent());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setCod(1);
		if (beanResponse != null && beanResponse.getChoices() != null && !beanResponse.getChoices().isEmpty()) {
			resp.setMensaje("CHATGPT RESPONDIÓ SATISFACTORIAMENTE");
		} else {
			resp.setMensaje("No se recibió respuesta válida de ChatGPT.");
		}

		return resp;
	}

	private String extraerTextoDePDF(MultipartFile archivo) {
		String text = "";
		try (PDDocument document = PDDocument.load(archivo.getInputStream())) {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	private List<String> splitTextIntoChunks(String text, int maxTokens) {
		List<String> chunks = new ArrayList<>();

		String[] sentences = text.split("(?<=\\.)"); // Divide por oraciones (al final de puntos)

		StringBuilder currentChunk = new StringBuilder();
		for (String sentence : sentences) {
			if (currentChunk.length() + sentence.length() > maxTokens) {
				chunks.add(currentChunk.toString());
				currentChunk = new StringBuilder();
			}
			currentChunk.append(sentence);
		}

		if (currentChunk.length() > 0) {
			chunks.add(currentChunk.toString());
		}

		return chunks;
	}

	@Override
	public ListModelResponse<ArchivoDocumentoResponse> buscarGuias(EnviarMensajeRequest datos) throws IOException {
		ListModelResponse<ArchivoDocumentoResponse> resp = new ListModelResponse<>();
		List<ArchivoDocumentoResponse> archivos = archivoRepository.listarArchivoDocumento();
		List<ArchivoDocumentoResponse> listArchivosEncontrados=new ArrayList<>();
		for (ArchivoDocumentoResponse archivo : archivos) {
			if(analizarporGuia(archivo,datos.getMensaje())){
				listArchivosEncontrados.add(archivo);
			}
		}
		if(!listArchivosEncontrados.isEmpty()){
			resp.setCod(Constantes.SUCCESS_COD);
			resp.setIcon(Constantes.ICON_SUCCESS);
			resp.setMensaje("Se han encontrado guías relacionadas a su consulta");
			resp.setList(listArchivosEncontrados);
		}
		else{
			resp.setIcon(Constantes.ICON_INFO);
			resp.setMensaje("No se han encontrado guías relacionadas a su consulta");
		}
		return resp;
	}

	public boolean analizarporGuia(ArchivoDocumentoResponse archivos,String mensaje) throws IOException {
		EnviarMensajeRequest request = new EnviarMensajeRequest();
		request.setPrompt("Tu objetivo es indicar si es VERDADERO o FALSO si una palabra o un tema parecido se encuentra como atributo en un objeto json");
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonGuia = objectMapper.writeValueAsString(archivos);
		request.setMensaje("La palabra es "+mensaje+" y debes buscarla en este json "+jsonGuia);
		ModelResponse<String> validar=enviarMensaje(request);
		if(validar.getCod()==1){
			return validar.getModel().equals("VERDADERO");
		}
		return false;
	}



}
