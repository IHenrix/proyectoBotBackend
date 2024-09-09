package pe.edu.usmp.bot.app.utils;
import java.text.DecimalFormat;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Pruebas {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("123456");
		System.out.println(hashedPassword);
		
		int nota = 13;
	    String mensaje = (nota > 18 && nota <= 20) ? "Excelente" :
            (nota > 15 && nota <= 18) ? "Bueno" :
            (nota <= 13 && nota >= 15) ? "Regular" :
            "Deficiente";
	    
	    System.out.println(mensaje);
	}

}
