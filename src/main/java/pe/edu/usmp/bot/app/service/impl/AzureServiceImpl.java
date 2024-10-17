package pe.edu.usmp.bot.app.service.impl;

//import com.microsoft.cognitiveservices.speech.*;
//import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.AzureRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.AzureService;
import org.springframework.beans.factory.annotation.Value;
import pe.edu.usmp.bot.app.utils.Constantes;

//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.concurrent.CountDownLatch;

import java.io.File;
import java.nio.file.Files;

@Service
public class AzureServiceImpl  implements AzureService {

    @Value("${azure.speech.key}")
    private String speechKey;

    @Value("${azure.speech.region}")
    private String speechRegion;

    @Autowired
    private RestTemplate restTemplate;

    private final String tokenEndpoint = "https://eastus.api.cognitive.microsoft.com/sts/v1.0/issueToken";
    private final String speechEndpoint = "https://eastus.stt.speech.microsoft.com/speech/recognition/conversation/cognitiveservices/v1?language=es-ES";
    private final String ttsEndpoint = "https://eastus.tts.speech.microsoft.com/cognitiveservices/v1";

   /* @Override
    public ModelResponse<String> speakToText(MultipartFile audio) {
        ModelResponse<String> resp = new ModelResponse<>();
        File tempFile = null;

        try {
            tempFile = Files.createTempFile("audio", ".wav").toFile();
            audio.transferTo(tempFile);

            SpeechConfig config = SpeechConfig.fromSubscription(speechKey, speechRegion);
            config.setSpeechRecognitionLanguage("es-ES");

            AudioConfig audioConfig = AudioConfig.fromWavFileInput(tempFile.getAbsolutePath());
            SpeechRecognizer recognizer = new SpeechRecognizer(config, audioConfig);

            final StringBuilder recognizedText = new StringBuilder();
            CountDownLatch latch = new CountDownLatch(1);
            recognizer.recognized.addEventListener((s, e) -> {
                if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                    recognizedText.append(e.getResult().getText());
                }
            });
            recognizer.sessionStopped.addEventListener((s, e) -> {
                recognizer.stopContinuousRecognitionAsync();
                latch.countDown();
            });
            recognizer.canceled.addEventListener((s, e) -> {
                System.err.println("Recognition canceled: " + e.getErrorDetails());
                latch.countDown();
            });
            recognizer.startContinuousRecognitionAsync().get();
            latch.await();
            resp.setMensaje("Se ha traducido el audio a texto exitosamente");
            resp.setCod(Constantes.SUCCESS_COD);
            resp.setIcon(Constantes.ICON_SUCCESS);
            resp.setModel(recognizedText.toString());
        } catch (Exception ex) {
            resp.setMensaje("Error al procesar el archivo de audio: " + ex.getMessage());
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_ERROR);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }

        return resp;
    }*/

    /*@Override
    public byte[] textToSpeak(AzureRequest datos) {
        byte[] audio =null;
        File tempFile = null;
        try {
            SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
            speechConfig.setSpeechSynthesisVoiceName("es-ES-HelenaNeural");
            tempFile = File.createTempFile("speech_output", ".wav");
            AudioConfig audioConfig = AudioConfig.fromWavFileOutput(tempFile.getAbsolutePath());
            SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig, audioConfig);
            SpeechSynthesisResult result = synthesizer.SpeakText(datos.getMensaje());
            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                audio=readFileToByteArray(tempFile);
            } else {
                throw new Exception("Error al sintetizar el audio: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
        return audio;
    }
    private byte[] readFileToByteArray(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }*/

    @Override
    public ModelResponse<String> speakToTextApi(MultipartFile audio) {
        ModelResponse<String> resp = new ModelResponse<>();
        File tempFile = null;
        try {
            tempFile = Files.createTempFile("audio", ".wav").toFile();
            audio.transferTo(tempFile);
            String accessToken = getAzureAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.valueOf("audio/wav"));
            byte[] audioBytes = Files.readAllBytes(tempFile.toPath());
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(audioBytes, headers);
            ResponseEntity<String> response = restTemplate.exchange(speechEndpoint, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonResponse = new JSONObject(response.getBody());
                String recognitionStatus = jsonResponse.getString("RecognitionStatus");

                if ("Success".equals(recognitionStatus)) {
                    String recognizedText = jsonResponse.getString("DisplayText");

                    resp.setMensaje("Se ha traducido el audio a texto exitosamente");
                    resp.setCod(Constantes.SUCCESS_COD);
                    resp.setIcon(Constantes.ICON_SUCCESS);
                    resp.setModel(recognizedText);
                } else {
                    resp.setMensaje("El reconocimiento no fue exitoso. Estado: " + recognitionStatus);
                    resp.setCod(Constantes.NULL_COD);
                    resp.setIcon(Constantes.ICON_ERROR);
                }
            } else {
                resp.setMensaje("Error al procesar el archivo de audio: " + response.getBody());
                resp.setCod(Constantes.NULL_COD);
                resp.setIcon(Constantes.ICON_ERROR);
            }
        } catch (Exception ex) {
            resp.setMensaje("Error al procesar el archivo de audio: " + ex.getMessage());
            resp.setCod(Constantes.NULL_COD);
            resp.setIcon(Constantes.ICON_ERROR);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }

        return resp;
    }
    @Override
    public byte[] textToSpeakApi(AzureRequest datos) {
        byte[] audio = null;
        try {
            String accessToken = getAzureAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.set("Content-Type", "application/ssml+xml");
            headers.set("X-Microsoft-OutputFormat", "riff-16khz-16bit-mono-pcm");
            String ssmlRequest = "<speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xml:lang='es-ES'>" +
                    "<voice name='es-ES-HelenaNeural'>" + datos.getMensaje() + "</voice></speak>";

            HttpEntity<String> requestEntity = new HttpEntity<>(ssmlRequest, headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(ttsEndpoint, HttpMethod.POST, requestEntity, byte[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                audio = response.getBody();
            } else {
                throw new Exception("Error al sintetizar el audio: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return audio;
    }


    private String getAzureAccessToken() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Ocp-Apim-Subscription-Key", speechKey);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("No se pudo obtener el token de Azure");
        }
    }
    
}
