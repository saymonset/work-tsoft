// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 22/08/2006 16:16:56
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) fieldsfirst nonlb space 
// Source File Name:   Config.java

package com.indeval.portaldali.presentation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static String fileName = "fechaTag.ini";
	private static Properties prop = null;

	static{
		if (prop == null)
			load();
	}
	
	
	
	public static void load() {
		try {
			prop = new Properties();
			InputStream in = Config.class.getResourceAsStream(fileName);
			prop.load(in);
			in.close();
		}
		catch (IOException io) {
			io.printStackTrace();
		}
	}

	
	public static String getProperty(String name) {
		if (prop == null)
			load();
		return prop.getProperty(name);
	}

	public static void setProperty(String name, String value) {
		prop.setProperty(name, value);
	}




}