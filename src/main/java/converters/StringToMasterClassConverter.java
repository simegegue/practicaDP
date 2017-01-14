package converters;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MasterClass;

import repositories.MasterClassRepository;

@Component
@Transactional
public class StringToMasterClassConverter implements Converter<String, MasterClass>{

	@Autowired
	MasterClassRepository masterClassRepository;

	@Override
	public MasterClass convert(String text) {
		MasterClass result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = masterClassRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
