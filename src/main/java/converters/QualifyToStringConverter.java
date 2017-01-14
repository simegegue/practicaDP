package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Qualify;

public class QualifyToStringConverter implements Converter<Qualify, String>{
	
	@Override
	public String convert(Qualify qualify) {
		String result;

		if (qualify == null)
			result = null;
		else
			result = String.valueOf(qualify.getId());

		return result;
	}

}
