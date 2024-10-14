package pe.edu.usmp.bot.app.service;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.AzureRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;

public interface AzureService {

    ModelResponse<String> speakToText(MultipartFile audio)  throws Exception;
    byte[] textToSpeak(AzureRequest datos);
}
