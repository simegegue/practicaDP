package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.ReviewRepository;
import domain.Review;

public class StringToReviewConverter implements Converter<String, Review>{
	
	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public Review convert(String text) {
		Review result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = reviewRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
