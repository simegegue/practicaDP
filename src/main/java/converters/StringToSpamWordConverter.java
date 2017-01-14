package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.SpamWordRepository;

import domain.SpamWord;

public class StringToSpamWordConverter implements Converter<String, SpamWord>{
	
	@Autowired
	SpamWordRepository spamWordRepository;

	@Override
	public SpamWord convert(String text) {
		SpamWord result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = spamWordRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
