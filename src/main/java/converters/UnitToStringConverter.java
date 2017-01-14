package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Unit;

public class UnitToStringConverter implements Converter<Unit, String>{
	
	@Override
	public String convert(Unit unit) {
		String result;

		if (unit == null)
			result = null;
		else
			result = String.valueOf(unit.getId());

		return result;
	}

}
