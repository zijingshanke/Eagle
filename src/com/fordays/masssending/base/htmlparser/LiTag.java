package com.fordays.masssending.base.htmlparser;

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.tags.CompositeTag;

/**
 * 基于htmlparser 的自定义扩展标签
 * <li></li>
 */
public class LiTag extends CompositeTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String mIds[] = { "li" };
	private static final String mEndTagEnders[] = { "li" };

	public LiTag() {

	}

	public static String[] getMIds() {
		return mIds;
	}

	public static String[] getMEndTagEnders() {
		return mEndTagEnders;
	}

	/**
	 * 注册调用示例(测试不成功)
	 */
	public static Parser registerLiTag(Parser parser) {
		// 上面就是定义这个tag标签继承CompositeTag
		// 然后最重要的是，在使用的时候要注册这个tag
		parser = new Parser();

		PrototypicalNodeFactory p = new PrototypicalNodeFactory();
		p.registerTag(new LiTag());
		parser.setNodeFactory(p);

		return parser;
	}

}
