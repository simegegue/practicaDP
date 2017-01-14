package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.RelationDislikeRepository;

import domain.RelationDislike;

public class StringToRelationDislikeConverter implements Converter<String, RelationDislike>{
	
	@Autowired
	RelationDislikeRepository relationDislikeRepository;

	@Override
	public RelationDislike convert(String text) {
		RelationDislike result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = relationDislikeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
