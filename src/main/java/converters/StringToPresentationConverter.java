package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.PresentationRepository;

import domain.Presentation;

public class StringToPresentationConverter implements Converter<String, Presentation>{
	
	@Autowired
	PresentationRepository presentationRepository;

	@Override
	public Presentation convert(String text) {
		Presentation result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = presentationRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
