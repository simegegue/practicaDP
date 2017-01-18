package converters;

import org.springframework.core.convert.converter.Converter;


import domain.Review;


public class ReviewToStringConverter implements Converter<Review, String>{
	@Override
	public String convert(Review review) {
		String result;

		if (review == null)
			result = null;
		else
			result = String.valueOf(review.getId());

		return result;
	}
}
