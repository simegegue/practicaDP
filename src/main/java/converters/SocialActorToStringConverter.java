package converters;

import org.springframework.core.convert.converter.Converter;

import domain.SocialActor;

public class SocialActorToStringConverter implements Converter<SocialActor, String>{
	
	@Override
	public String convert(SocialActor socialActor) {
		String result;

		if (socialActor == null)
			result = null;
		else
			result = String.valueOf(socialActor.getId());

		return result;
	}

}
