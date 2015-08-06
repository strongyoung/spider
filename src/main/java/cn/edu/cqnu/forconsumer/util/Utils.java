package cn.edu.cqnu.forconsumer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String getIdByLink(String link){
		Pattern pattern = Pattern.compile(Constant.REGEX_JD_PRODUCT_ID);
		Matcher matcher = pattern.matcher(link);
		if(matcher.find()){
			return matcher.group();
		}
		return null;
	}
}
