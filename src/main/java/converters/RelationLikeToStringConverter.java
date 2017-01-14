package converters;

import org.springframework.core.convert.converter.Converter;

import domain.RelationLike;

public class RelationLikeToStringConverter implements Converter<RelationLike, String>{
	
	@Override
	public String convert(RelationLike relationLike) {
		String result;

		if (relationLike == null)
			result = null;
		else
			result = String.valueOf(relationLike.getId());

		return result;
	}

}
