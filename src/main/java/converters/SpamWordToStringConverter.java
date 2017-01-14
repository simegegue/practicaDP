package converters;

import org.springframework.core.convert.converter.Converter;

import domain.SpamWord;

public class SpamWordToStringConverter implements Converter<SpamWord, String>{
	
	@Override
	public String convert(SpamWord spamWord) {
		String result;

		if (spamWord == null)
			result = null;
		else
			result = String.valueOf(spamWord.getId());

		return result;
	}

}
