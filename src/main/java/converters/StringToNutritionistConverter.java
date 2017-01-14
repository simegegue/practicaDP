package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.NutritionistRepository;

import domain.Nutritionist;

public class StringToNutritionistConverter implements Converter<String, Nutritionist>{
	
	@Autowired
	NutritionistRepository nutritionistRepository;

	@Override
	public Nutritionist convert(String text) {
		Nutritionist result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = nutritionistRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
