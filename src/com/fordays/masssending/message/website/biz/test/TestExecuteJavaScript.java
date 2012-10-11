package com.fordays.masssending.message.website.biz.test;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TestExecuteJavaScript {
	public static void main(String[] args) {
		// test1();
		// test2();
		test3();
	}

	public static void test3() {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			String script = "var randStr=Math.floor(Math.random()*1000000);println(randStr);";
//			script += " println('1'); ";
//			script += " return randStr; ";
//			script += "}";

//			System.out.println(script);
//			Compilable compilable = (Compilable) engine;
//			CompiledScript compiled = compilable.compile(script);
//			Object compileObj = compiled.eval();
//			System.out.println("compileObj:" + compileObj);

			// // 执行脚本
			Object obj = engine.eval(script);
			// Invocable inv = (Invocable) engine;
			// // 执行方法并传递参数
			// Object obj = inv.invokeFunction("");
			// 打印结果
			System.out.println(obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		// http://sso.online.sh.cn/passport/action/IdentifyCode?f=&lt;script
		// language=JavaScript&gt;document.write(Math.floor(Math.random()*1000000))&lt;/script&gt;
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			String script = "println(Math.floor(Math.random()*1000000))";
			Compilable compilable = (Compilable) engine;
			CompiledScript compiled = compilable.compile(script);
			compiled.eval();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test1() {
		try {
			// 创建脚本引擎管理器
			ScriptEngineManager manager = new ScriptEngineManager();

			// 创建一个处理JavaScript的脚本引擎
			ScriptEngine engine = manager.getEngineByName("JavaScript");

			String script = "var email=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]"
					+ "+(\\.[a-zA-Z0-9_-]+)+$/;";
			script += "var ip = /^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])"
					+ "(\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])){3}$/;";

			script += "if(email.test(str)){println('it is an email')}"
					+ "else if(ip.test(str)){println('it is an ip address')}"
					+ "else{println('I don\\'t know')}";

			engine.put("str", "email@address.tony");

			Compilable compilable = (Compilable) engine;
			CompiledScript compiled = compilable.compile(script);
			compiled.eval();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
