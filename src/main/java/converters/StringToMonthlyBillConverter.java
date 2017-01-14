package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.MonthlyBillRepository;

import domain.MonthlyBill;

public class StringToMonthlyBillConverter implements Converter<String, MonthlyBill>{
	
	@Autowired
	MonthlyBillRepository monthlyBillRepository;

	@Override
	public MonthlyBill convert(String text) {
		MonthlyBill result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = monthlyBillRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
