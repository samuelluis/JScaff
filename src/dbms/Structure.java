package dbms;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import utils.ScriptHelper;

public class Structure {
	private General general;
	public LinkedHashMap<String, Entry<String, String>> connection;
	private HashMap<String, DataType> datatypes;
	public HashMap<String, String> dml;
	public HashMap<String, String> query;
	
	public Structure(){
		connection = new LinkedHashMap<String, Entry<String, String>>();
		datatypes = new HashMap<String, DataType>();
		dml = new HashMap<String, String>();
		query = new HashMap<String, String>();
	}
	
	public General getGeneral(){
		return general;
	}
	
	public void setGeneral(String name, String safeCaracter, String driverPath, String connection) {
		general = new General(name, safeCaracter, driverPath, connection);
	}
	
	public HashMap<String, DataType> getDataTypes(){
		return datatypes;
	}
	
	public void addDataType(String dbName, String jType, String condition){
		HashMap<String, DataType> datatypes = getDataTypes();
		if(datatypes.get(dbName)==null){
			datatypes.put(dbName, new DataType(dbName, jType, condition));
		}
		else{
			datatypes.get(dbName).addCondition(condition, jType);
		}
	}

	public void addDataType(String dbName, String jType){
		if(datatypes.get(dbName)==null){
			datatypes.put(dbName, new DataType(dbName, jType));
		}
	}
	
	public class General{
		public String name;
		public String leftSafe;
		public String rightSafe;
		public String mainDatabase;
		public String driverPath;
		public String connection;
		private General(String name, String safeCaracter, String driverPath, String connection){
			this.name = name;
			this.leftSafe = safeCaracter.split("")[1];
			if(safeCaracter.split("").length>2) this.rightSafe = safeCaracter.split("")[2];
			else this.rightSafe = this.leftSafe;
			this.driverPath = driverPath;
			this.connection = connection;
		}
		public String getConnection(HashMap<String, String> values){
			String str = connection;
			for (String key : values.keySet()) {
				str = str.replaceAll("\\["+key+"\\]", values.get(key));
			}
			return str;
		}
	}
	
	public class DataType{
		public String dbName;
		private String type;
		private LinkedHashMap<String, String> conditions;
		
		private DataType(String dbName, String jType){
			this.dbName = dbName;
			type = jType;
			conditions = new LinkedHashMap<String, String>();
		}
		
		private DataType(String dbName, String jType, String condition){
			this(dbName, jType);
			addCondition(condition, jType);
		}
		
		private void addCondition(String condition, String jType){
			conditions.put(condition, jType);
		}
		
		public String getType(String length){
			if(!conditions.isEmpty())
				for (Entry<String, String> condition : conditions.entrySet())
					if(Boolean.parseBoolean(ScriptHelper.eval(length+condition.getKey()).toString()))
						type = condition.getValue();
			return type;
		}
	}
}
