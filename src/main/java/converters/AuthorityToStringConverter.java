package converters;

import org.springframework.core.convert.converter.Converter;

import security.Authority;


public class AuthorityToStringConverter implements Converter<Authority, String>{
	
	@Override
	public String convert(Authority authority) {
		String result;

		if (authority == null){
			result = null;
		}else{
			result = String.valueOf(authority.getAuthority());
		}
		return result;
	}

}
