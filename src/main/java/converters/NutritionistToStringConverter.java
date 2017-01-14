package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Nutritionist;

public class NutritionistToStringConverter implements Converter<Nutritionist, String>{
	
	@Override
	public String convert(Nutritionist nutritionist) {
		String result;

		if (nutritionist == null)
			result = null;
		else
			result = String.valueOf(nutritionist.getId());

		return result;
	}

}
