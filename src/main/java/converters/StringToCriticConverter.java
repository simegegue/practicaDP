package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CriticRepository;
import domain.Critic;

public class StringToCriticConverter implements Converter<String, Critic>{
	@Autowired
	CriticRepository criticRepository;

	@Override
	public Critic convert(String text) {
		Critic result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = criticRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
