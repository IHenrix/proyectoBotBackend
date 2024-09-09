package pe.edu.usmp.bot.app.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pe.edu.usmp.bot.app.model.AuthModel;

@Service
public class Auth {
	@Autowired(required = false)
	private TokenStore tokenstore;

	public AuthModel usuario() {
		String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("Authorization").replace("Bearer ", "").trim();

		Map<String, Object> details = tokenstore.readAccessToken(token).getAdditionalInformation();

		JSONObject jsonObj = new JSONObject(details);

		AuthModel usuario = new AuthModel();
		usuario.setCo_usu(jsonObj.getLong("co_usu"));
		usuario.setUsuario_red(getOrNull(jsonObj, "usuario_red"));
		usuario.setNombre_completo(getOrNull(jsonObj, "nombre_completo"));
		usuario.setNombre(getOrNull(jsonObj, "nombre"));
		usuario.setPaterno(getOrNull(jsonObj, "paterno"));
		usuario.setMaterno(getOrNull(jsonObj, "materno"));
		usuario.setLib_elect(getOrNull(jsonObj, "lib_elect"));
		usuario.setZona(getOrNull(jsonObj, "zona"));
		usuario.setSexo(getOrNull(jsonObj, "sexo"));
		usuario.setTelf_cel(getOrNull(jsonObj, "telf_cel"));
		usuario.setCorreo(getOrNull(jsonObj, "correo"));
		usuario.setCo_modalidad(jsonObj.getLong("co_modalidad"));
		usuario.setModalidad(getOrNull(jsonObj, "modalidad"));
		// usuario.setCo_trab(getOrNull(jsonObj, "co_trab"));
		usuario.setGrupo_planilla(getOrNull(jsonObj, "grupo_planilla"));
		usuario.setArea(getOrNull(jsonObj, "area"));
		usuario.setCo_area(jsonObj.getLong("co_area"));
		JSONArray roles = new JSONArray(getOrNull(jsonObj, "authorities"));
		if (roles.length() > 0) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < roles.length(); i++) {
				list.add(roles.optString(i));
			}
			usuario.setRoles(list);
		}
		return usuario;

	}

	private static String getOrNull(JSONObject jsonObj, String key) {
		return jsonObj.optString(key, null);
	}

	public Map<String, Object> getDetail(OAuth2Authentication authentication) {
		OAuth2AuthenticationDetails oauth2authenticationdetails = (OAuth2AuthenticationDetails) authentication
				.getDetails();
		Map<String, Object> details = tokenstore.readAccessToken(oauth2authenticationdetails.getTokenValue())
				.getAdditionalInformation();
		return details;
	}

	public Long co_usu() {
		return usuario().getCo_usu();
	}

	public String zona() {
		return usuario().getZona();
	}

	public String usuario_red() {
		return usuario().getUsuario_red();
	}

	public String nombre_completo() {
		return usuario().getNombre_completo();
	}

	public String lib_elect() {
		return usuario().getLib_elect();
	}

	public Long co_area() {
		return usuario().getCo_area();
	}

	public List<String> roles() {
		return usuario().getRoles();
	}
}
