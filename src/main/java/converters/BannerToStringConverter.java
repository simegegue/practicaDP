package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Banner;

public class BannerToStringConverter implements Converter<Banner, String>{

	@Override
	public String convert(Banner banner) {
		String result;

		if (banner == null)
			result = null;
		else
			result = String.valueOf(banner.getId());

		return result;
	}
	
}
