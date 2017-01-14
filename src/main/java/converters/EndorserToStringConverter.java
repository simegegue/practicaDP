package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Endorser;

public class EndorserToStringConverter implements Converter<Endorser, String>{
	
	@Override
	public String convert(Endorser endorser) {
		String result;

		if (endorser == null)
			result = null;
		else
			result = String.valueOf(endorser.getId());

		return result;
	}

}
