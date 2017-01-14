package converters;

import org.springframework.core.convert.converter.Converter;

import domain.SocialIdentity;

public class SocialIdentityToStringConverter implements Converter<SocialIdentity, String>{
	
	@Override
	public String convert(SocialIdentity socialIdentity) {
		String result;

		if (socialIdentity == null)
			result = null;
		else
			result = String.valueOf(socialIdentity.getId());

		return result;
	}

}
