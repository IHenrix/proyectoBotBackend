package pe.edu.usmp.bot.app.utils;

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
}
