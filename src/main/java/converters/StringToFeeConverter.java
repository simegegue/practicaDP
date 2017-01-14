package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.FeeRepository;

import domain.Fee;

public class StringToFeeConverter implements Converter<String, Fee>{
	
	@Autowired
	FeeRepository feeRepository;

	@Override
	public Fee convert(String text) {
		Fee result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = feeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
