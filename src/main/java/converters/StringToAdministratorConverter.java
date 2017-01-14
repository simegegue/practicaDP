package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.AdministratorRepository;

import domain.Administrator;

public class StringToAdministratorConverter implements Converter<String, Administrator>{

	@Autowired
	AdministratorRepository administratorRepository;

	@Override
	public Administrator convert(String text) {
		Administrator result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = administratorRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
	
}
