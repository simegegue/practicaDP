package converters;

import org.springframework.core.convert.converter.Converter;

import security.Authority;

public class StringToAuthorityConverter implements Converter<String,Authority>{
	
	@Override
	public Authority convert(String text){
		
		Authority result;
		try{
			result = new Authority();
			result.setAuthority(text);
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
