package ru.fusionsoft.dbgit.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import ru.fusionsoft.dbgit.utils.MaskFilter;

/**
 * Class for with ignore database objects from .dbignore file
 * DBGitIgnore is Singleton
 * 
 * @author mikle
 *
 */
public class DBGitIgnore {
	private static DBGitIgnore ignore = null;
	
	private Map<String, MaskFilter> filters = new HashMap<>();
	
	private DBGitIgnore() throws ExceptionDBGit {
		// load file DBIgnore
		loadFileDBIgnore();		
	}
	
	protected void loadFileDBIgnore() throws ExceptionDBGit {
		try{				
			File file = new File(DBGitPath.getFullPath(DBGitPath.DB_IGNORE_FILE));
			
			if (!file.exists()) return ;
			
			BufferedReader br = new BufferedReader(new FileReader(file));			
			for(String line; (line = br.readLine()) != null; ) {
				MaskFilter mask = new MaskFilter(line);
				filters.put(line, mask);
			}
			    
			br.close();		    
	    } catch(Exception e) {
	    	throw new ExceptionDBGit(e);
	    }
	}
	
	public static DBGitIgnore getInctance()  throws ExceptionDBGit {
		if (ignore == null) {
			ignore = new DBGitIgnore();
		}
		return ignore;
	}
	
	public boolean matchOne(String exp) {
		for (MaskFilter mask : filters.values()) {
			if (mask.match(exp)) return true;
		}
		return false;
	}
}