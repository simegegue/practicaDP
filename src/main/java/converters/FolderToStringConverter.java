package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Folder;

public class FolderToStringConverter implements Converter<Folder, String>{
	
	@Override
	public String convert(Folder folder) {
		String result;

		if (folder == null)
			result = null;
		else
			result = String.valueOf(folder.getId());

		return result;
	}

}
