package pe.edu.usmp.bot.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.edu.usmp.bot.app.request.EnviarMensajeRequest;
import pe.edu.usmp.bot.app.response.ChatGptResponse;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.ChatBotService;

import java.net.ConnectException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@Service
public class ChatBotServiceImpl implements ChatBotService {

	@Value("${openai.api.key}")
	private String openaiApiKey;

	@Value("${openai.api.url}")
	private String openaiApiUrl;

	@Value("${openai.api.content}")
	private String openaiApiUrlContent;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ModelResponse<String> enviarMensaje(EnviarMensajeRequest datos) {

		ModelResponse<String> resp = new ModelResponse<String>();

		ChatGptResponse beanResponse = new ChatGptResponse();
		String jsonRequest = null;
		String result = "";
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			// Header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + openaiApiKey);

			String requestBody = "{" +
					   "\"model\": \"gpt-3.5-turbo\"," +
					   "\"messages\": [{" +
					   "\"role\": \"system\", \"content\": \""+openaiApiUrlContent+".\"}," +
					   "{\"role\": \"user\", \"content\": \"" + datos.getMensaje() + "\"}]," +
					   "\"max_tokens\": 1000" +
					   "}";
			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
			result = restTemplate.postForObject(openaiApiUrl, requestEntity, String.class);

			beanResponse = objectMapper.readValue(result, ChatGptResponse.class);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setCod(1);
		resp.setMensaje("CHATPGT RESPONDIO SATISFACTORIAMENTE");
		resp.setModel(beanResponse.getChoices().get(0).getMessage().getContent());
		return resp;
	}

}
