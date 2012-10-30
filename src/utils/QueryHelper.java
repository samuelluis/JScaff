package utils;

import dbms.Store;

public class QueryHelper {
	public static String enclose(String query){
		return Store.struct.getGeneral().leftSafe+query+Store.struct.getGeneral().rightSafe;
	}
}
