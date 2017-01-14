package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.QualifyRepository;

import domain.Qualify;

public class StringToQualifyConverter implements Converter<String, Qualify>{
	
	@Autowired
	QualifyRepository qualifyRepository;

	@Override
	public Qualify convert(String text) {
		Qualify result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = qualifyRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
