package converters;

import org.springframework.core.convert.converter.Converter;

import domain.MonthlyBill;

public class MonthlyBillToStringConverter implements Converter<MonthlyBill, String>{
	
	@Override
	public String convert(MonthlyBill monthlyBill) {
		String result;

		if (monthlyBill == null)
			result = null;
		else
			result = String.valueOf(monthlyBill.getId());

		return result;
	}

}
