package pe.edu.usmp.bot.app.utils;

import java.util.Optional;

public class UtilResource {

    public static String validNullString(String data) {
        if (data == null || data.isEmpty()) {
            return "";
        }
        return data.trim();
    }

    public static int validarNullSize(String dato) {
        if (dato == null || dato.isEmpty()) {
            return 0;
        } else {
            return dato.trim().length();
        }
    }

    public static String obtenerExtensionArchivo(String nombreArchivo) {
        return Optional.ofNullable(nombreArchivo)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(nombreArchivo.lastIndexOf(".") + 1))
                .orElse("desconocido");
    }
    
    public static String contrasenaVacia(String contra) {
    	if(validNullString(contra).length()>0) {
    	   	return contra;
    	}
		return Constantes.CONTRASENA_DEFAULT;

    }
}
