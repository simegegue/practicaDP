package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.VideoRepository;

import domain.Video;

public class StringToVideoConverter implements Converter<String, Video>{
	
	@Autowired
	VideoRepository videoRepository;

	@Override
	public Video convert(String text) {
		Video result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = videoRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
