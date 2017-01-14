package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.StepRepository;

import domain.Step;

public class StringToStepConverter implements Converter<String, Step>{
	
	@Autowired
	StepRepository stepRepository;

	@Override
	public Step convert(String text) {
		Step result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = stepRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
