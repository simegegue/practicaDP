package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Cook;

public class CookToStringConverter implements Converter<Cook, String>{
	
	@Override
	public String convert(Cook cook) {
		String result;

		if (cook == null)
			result = null;
		else
			result = String.valueOf(cook.getId());

		return result;
	}

}
