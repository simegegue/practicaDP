package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.SocialActorRepository;

import domain.SocialActor;

public class StringToSocialActorConverter implements Converter<String, SocialActor>{
	
	@Autowired
	SocialActorRepository socialActorRepository;

	@Override
	public SocialActor convert(String text) {
		SocialActor result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = socialActorRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
