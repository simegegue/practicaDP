package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.TextRepository;

import domain.Text;

public class StringToTextConverter implements Converter<String, Text>{
	
	@Autowired
	TextRepository textRepository;

	@Override
	public Text convert(String text) {
		Text result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = textRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
