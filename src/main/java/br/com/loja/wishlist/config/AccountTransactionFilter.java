package br.com.loja.wishlist.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.com.loja.wishlist.exception.FailedLoginException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AccountTransactionFilter implements Filter {
	
	@Value("${account.validadeSession.url}")
	private String url;
	@Value("${account.validadeSession.activated}")
	private Boolean activated;
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) req;
		String token = httpRequest.getHeader("Authorization");
		if (activated == false || skipFilter(httpRequest.getRequestURI()) || callAccount(token)) {
			chain.doFilter(req, res);
		} else {
			throw new FailedLoginException("login.failed");
		}

	}

	private Boolean skipFilter(String url) {

		List<String> urlToSkip = new ArrayList<>();
		urlToSkip.add("swagger");
		urlToSkip.add("api-docs");
		urlToSkip.add("/csrf");
		return urlToSkip.stream()
			.filter(u -> url.indexOf(u) >= 0 || url.length() <2)
			.findFirst()
			.isPresent();

	}

	public Boolean callAccount (String token) {

	    if (token == null) {
	    	throw new FailedLoginException("login.failed");
	    }
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("token", token.replace("Bearer ", ""));
	    RestTemplate restTemplate = new RestTemplate();
	    return HttpStatus.OK.equals(restTemplate.postForEntity( url, params, String.class ).getStatusCode());

	}
}