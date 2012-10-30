package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ReflectionHelper {
	
	public static String[] getFiles(String packageName) {
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    assert loader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = null;
		try {resources = loader.getResources(path);} catch (IOException e) {}
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<String> files = new ArrayList<String>();
	    for (File directory : dirs) {
	        try {files.addAll(findFiles(directory, packageName));} catch (Exception e) {}
	    }
	    return files.toArray(new String[files.size()]);
	}
	
	private static List<String> findFiles(File directory, String packageName) throws Exception {
	    List<String> xmls = new ArrayList<String>();
	    if (!directory.exists()) {
	        return xmls;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            xmls.addAll(findFiles(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".xml")) {
	            xmls.add(file.getName().substring(0, file.getName().length() - 4));
	        }
	    }
	    return xmls;
	}
	
}