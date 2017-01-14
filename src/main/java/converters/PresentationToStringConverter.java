package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Presentation;

public class PresentationToStringConverter implements Converter<Presentation, String>{
	
	@Override
	public String convert(Presentation presentation) {
		String result;

		if (presentation == null)
			result = null;
		else
			result = String.valueOf(presentation.getId());

		return result;
	}

}
