package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Administrator;

public class AdministratorToStringConverter implements Converter<Administrator, String>{

	@Override
	public String convert(Administrator admin) {
		String result;

		if (admin == null)
			result = null;
		else
			result = String.valueOf(admin.getId());

		return result;
	}
}
