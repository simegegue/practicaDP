package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Quantity;

public class QuantityToStringConverter implements Converter<Quantity, String>{
	
	@Override
	public String convert(Quantity quantity) {
		String result;

		if (quantity == null)
			result = null;
		else
			result = String.valueOf(quantity.getId());

		return result;
	}

}
