package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.PropertyRepository;

import domain.Property;

public class StringToPropertyConverter implements Converter<String, Property>{
	
	@Autowired
	PropertyRepository propertyRepository;

	@Override
	public Property convert(String text) {
		Property result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = propertyRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
