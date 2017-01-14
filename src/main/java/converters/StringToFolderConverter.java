package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.FolderRepository;

import domain.Folder;

public class StringToFolderConverter implements Converter<String, Folder>{
	
	@Autowired
	FolderRepository folderRepository;

	@Override
	public Folder convert(String text) {
		Folder result;
		int id;

		try {
			if(StringUtils.isEmpty(text)){
				result=null;
			}else{
				id = Integer.valueOf(text);
				result = folderRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
