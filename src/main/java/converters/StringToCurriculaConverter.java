package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CurriculaRepository;
import domain.Curricula;

public class StringToCurriculaConverter implements Converter<String, Curricula>{
	
	@Autowired
	CurriculaRepository curriculaRepository;

	@Override
	public Curricula convert(String text) {
		Curricula result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = curriculaRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
