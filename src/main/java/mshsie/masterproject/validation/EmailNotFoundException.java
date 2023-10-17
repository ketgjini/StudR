package mshsie.masterproject.validation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email nuk u gjet")
public class EmailNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String exception) {
	        super(exception);
	 }
	
	public EmailNotFoundException(String exception, Throwable t) {
		super(exception, t);
	}

}
