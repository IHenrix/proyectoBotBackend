package pe.edu.usmp.bot.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.usmp.bot.app.response.ModelResponse;
import pe.edu.usmp.bot.app.service.AzureService;

@RestController
@RequestMapping("/azure")
public class AzureController {

    @Autowired
    private AzureService se;

    @RequestMapping(value = "speakToText", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ModelResponse<String> speakToText(@RequestParam("audio") MultipartFile audio) throws Exception{
        return se.speakToText(audio);
    }
}
