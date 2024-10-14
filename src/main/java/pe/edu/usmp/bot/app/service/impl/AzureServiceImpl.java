package pe.edu.usmp.bot.app.service.impl;

import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.AzureRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.AzureService;
import org.springframework.beans.factory.annotation.Value;
import com.microsoft.cognitiveservices.speech.*;
import pe.edu.usmp.bot.app.utils.Constantes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import java.io.File;
import java.nio.file.Files;

@Service
public class AzureServiceImpl  implements AzureService {

    @Value("${azure.speech.key}")
    private String speechKey;

    @Value("${azure.speech.region}")
    private String speechRegion;

    @Override
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
    }

    @Override
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
    }

}
