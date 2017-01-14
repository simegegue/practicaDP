package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Ingredient;

public class IngredientToStringConverter implements Converter<Ingredient, String>{
	
	@Override
	public String convert(Ingredient ingredient) {
		String result;

		if (ingredient == null)
			result = null;
		else
			result = String.valueOf(ingredient.getId());

		return result;
	}

}
