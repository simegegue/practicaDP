package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Campaign;

public class CampaignToStringConverter implements Converter<Campaign, String>{

	@Override
	public String convert(Campaign campaign) {
		String result;

		if (campaign == null)
			result = null;
		else
			result = String.valueOf(campaign.getId());

		return result;
	}
	
}
