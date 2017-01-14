package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.SocialIdentityRepository;

import domain.SocialIdentity;

public class StringToSocialIdentityConverter implements Converter<String, SocialIdentity>{
	
	@Autowired
	SocialIdentityRepository socialIdentityRepository;

	@Override
	public SocialIdentity convert(String text) {
		SocialIdentity result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = socialIdentityRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
