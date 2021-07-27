package br.com.loja.wishlist.exception;

import br.com.loja.wishlist.util.Translator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
@Log4j2
public class BusinessRunTimeException extends RuntimeException {
	
	private static final long serialVersionUID = 4985586388411859974L;

	public BusinessRunTimeException(String message, Throwable e) {
		super(Translator.toLocale(message));
		log.error("RunTime Exception: " + e, e);
	}

	public BusinessRunTimeException(String message) {
		super(Translator.toLocale(message));
		log.error("BusinessError: " + message );
	}
	
}
