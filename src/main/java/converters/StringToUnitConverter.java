package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.UnitRepository;

import domain.Unit;

public class StringToUnitConverter implements Converter<String, Unit>{
	
	@Autowired
	UnitRepository unitRepository;

	@Override
	public Unit convert(String text) {
		Unit result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = unitRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
