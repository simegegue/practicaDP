package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Property;

public class PropertyToStringConverter implements Converter<Property, String>{
	
	@Override
	public String convert(Property property) {
		String result;

		if (property == null)
			result = null;
		else
			result = String.valueOf(property.getId());

		return result;
	}

}
