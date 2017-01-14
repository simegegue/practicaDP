package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.MessageRepository;

import domain.Message;

public class StringToMessageConverter implements Converter<String, Message>{
	
	@Autowired
	MessageRepository messageRepository;

	@Override
	public Message convert(String text) {
		Message result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = messageRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
