package pe.edu.usmp.bot.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import pe.edu.usmp.bot.app.model.Usuario;
import pe.edu.usmp.bot.app.service.UsuarioService;
import pe.edu.usmp.bot.app.utils.UtilResource;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UsuarioService usuarioService;

//    private final UserDetailsService userDetailsService;
//
//    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = UtilResource.validNullString(authentication.getName()).toLowerCase();
		String password = authentication.getCredentials().toString();
		try {
			if (!username.endsWith("@usmp.pe")) {
				throw new BadCredentialsException("Solo se permiten correos electrónicos con dominio @usmp.pe");
			}
			Usuario usuario = usuarioService.findByUsername(username);
			if (usuario == null) {
				throw new UsernameNotFoundException("El correo " + username + " no se encuentra autorizado para acceder al Sistema");
			}
			if (usuario.getRoles().isEmpty()) {
				throw new UsernameNotFoundException("El correo " + username + " no cuenta con perfiles");
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (!passwordEncoder.matches(password, usuario.getPassword())) {
				throw new BadCredentialsException("El usuario o contraseña no coincide");
			}
			// UserDetails user = userDetailsService.loadUserByUsername(username);
			UserDetails user = new MyUserDetails(usuario);
			return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException(ex.getMessage());
		} catch (UsernameNotFoundException ex) {
			throw new UsernameNotFoundException(ex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
