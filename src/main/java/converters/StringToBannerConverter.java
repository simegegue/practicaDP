package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.BannerRepository;
import domain.Banner;

public class StringToBannerConverter implements Converter<String, Banner>{

	@Autowired
	BannerRepository bannerRepository;

	@Override
	public Banner convert(String text) {
		Banner result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = bannerRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
	
}
