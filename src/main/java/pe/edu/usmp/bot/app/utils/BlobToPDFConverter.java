package pe.edu.usmp.bot.app.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BlobToPDFConverter {
    public static void guardarBlobComoPDF(byte[] documentoBlob, String nombreArchivo) throws IOException {
        try (OutputStream out = new FileOutputStream(nombreArchivo)) {
            out.write(documentoBlob);
        }
    }
}
