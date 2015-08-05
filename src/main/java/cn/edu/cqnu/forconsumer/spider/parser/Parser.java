package cn.edu.cqnu.forconsumer.spider.parser;

import java.util.HashSet;

public interface Parser {
	public HashSet<String> parseCatSeed(String strContent);
	public int parseProductPageSeed(String strContent);
	public HashSet<String> parseProductSeed(String strContent);
}
