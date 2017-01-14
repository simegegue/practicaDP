package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CookRepository;

import domain.Cook;

public class StringToCookConverter implements Converter<String, Cook>{
	
	@Autowired
	CookRepository cookRepository;

	@Override
	public Cook convert(String text) {
		Cook result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = cookRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
