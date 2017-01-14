package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Fee;

public class FeeToStringConverter implements Converter<Fee, String>{
	
	@Override
	public String convert(Fee fee) {
		String result;

		if (fee == null)
			result = null;
		else
			result = String.valueOf(fee.getId());

		return result;
	}

}
