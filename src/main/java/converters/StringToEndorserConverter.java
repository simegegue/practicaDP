package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.EndorserRepository;

import domain.Endorser;

public class StringToEndorserConverter implements Converter<String, Endorser>{
	
	@Autowired
	EndorserRepository endorserRepository;

	@Override
	public Endorser convert(String text) {
		Endorser result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = endorserRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
