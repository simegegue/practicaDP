package converters;

import org.springframework.core.convert.converter.Converter;

import domain.RelationDislike;

public class RelationDislikeToStringConverter implements Converter<RelationDislike, String>{
	
	@Override
	public String convert(RelationDislike relationDislike) {
		String result;

		if (relationDislike == null)
			result = null;
		else
			result = String.valueOf(relationDislike.getId());

		return result;
	}

}
