package mshsie.masterproject.validation;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Perdoruesi nuk u gjet")
public class UserNotFoundException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String exception) {
	        super(exception);
	 }
	
	public UserNotFoundException(String exception, Throwable t) {
		super(exception, t);
	}

}
