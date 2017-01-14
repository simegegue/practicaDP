package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.QuantityRepository;

import domain.Quantity;

public class StringToQuantityConverter implements Converter<String, Quantity>{
	
	@Autowired
	QuantityRepository quantityRepository;

	@Override
	public Quantity convert(String text) {
		Quantity result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = quantityRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
		
	}
}
