package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.RelationLikeRepository;

import domain.RelationLike;

public class StringToRelationLikeConverter implements Converter<String, RelationLike>{
	
	@Autowired
	RelationLikeRepository relationLikeRepository;

	@Override
	public RelationLike convert(String text) {
		RelationLike result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = relationLikeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
