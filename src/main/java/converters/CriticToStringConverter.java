package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Critic;

public class CriticToStringConverter implements Converter<Critic, String> {
	@Override
	public String convert(Critic critic) {
		String result;

		if (critic == null)
			result = null;
		else
			result = String.valueOf(critic.getId());

		return result;
	}

}
