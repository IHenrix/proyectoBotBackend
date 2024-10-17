package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.request.AzureRequest;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.AzureService;

@RestController
@RequestMapping("/azure")
public class AzureController {

    @Autowired
    private AzureService se;

    /*@RequestMapping(value = "speakToText", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ModelResponse<String> speakToText(@RequestParam("audio") MultipartFile audio) throws Exception{
        return se.speakToText(audio);
    }*/

    /*@RequestMapping(value = "textToSpeak", method = RequestMethod.POST)
    public ResponseEntity<byte[]> textToSpeak(@RequestBody AzureRequest datos) {
        byte[] audioBytes = se.textToSpeak(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/wav"));
        headers.setContentLength(audioBytes.length);
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }*/

    @RequestMapping(value = "speakToTextApi", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ModelResponse<String> speakToTextApi(@RequestParam("audio") MultipartFile audio){
        return se.speakToTextApi(audio);
    }

    @RequestMapping(value = "textToSpeakApi", method = RequestMethod.POST)
    public ResponseEntity<byte[]> textToSpeakApi(@RequestBody AzureRequest datos) {
        byte[] audioBytes = se.textToSpeakApi(datos);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/wav"));
        headers.setContentLength(audioBytes.length);
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }
}
