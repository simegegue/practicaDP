package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Text;

public class TextToStringConverter implements Converter<Text, String>{
	
	@Override
	public String convert(Text text) {
		String result;

		if (text == null)
			result = null;
		else
			result = String.valueOf(text.getId());

		return result;
	}

}
