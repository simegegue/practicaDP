package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CampaignRepository;

import domain.Campaign;

public class StringToCampaignConverter implements Converter<String, Campaign>{

	@Autowired
	CampaignRepository campaignRepository;

	@Override
	public Campaign convert(String text) {
		Campaign result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = campaignRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
	
}
