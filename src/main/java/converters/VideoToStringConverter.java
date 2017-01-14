package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Video;

public class VideoToStringConverter implements Converter<Video, String>{
	
	@Override
	public String convert(Video video) {
		String result;

		if (video == null)
			result = null;
		else
			result = String.valueOf(video.getId());

		return result;
	}

}
