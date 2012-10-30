package utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptHelper {
	public static Object eval(String expression){
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("js"); 
	    try {
			return engine.eval(expression);
		} catch (ScriptException e) {
			return null;
		}
	}
}
