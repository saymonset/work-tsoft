package com.indeval.portalinternacional.presentation.derechos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTransferUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileTransferUtil.class);
	
	public static List<String> readFile(InputStream file){
		List<String> rows = null;
		String row = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(file));
		
		try {
			rows = new ArrayList<String>();
			row = in.readLine();		
			while(StringUtils.isNotBlank(row)){
				rows.add(row);
				row = in.readLine();
			}		
			if(in != null){
				in.close();
			}
		} catch (IOException e) {			
			LOG.error(e.getMessage());
		}
		
		return rows;
	}		 
}
