package ru.fusionsoft.dbgit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.slf4j.LoggerFactory;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ru.fusionsoft.dbgit.command.RequestCmd;
import ru.fusionsoft.dbgit.core.DBGitPath;
import ru.fusionsoft.dbgit.utils.ConsoleWriter;
import ru.fusionsoft.dbgit.utils.LoggerUtil;


/**
 * Hello world!
 *
 */
public class App 
{
	public static void executeDbGitCommand(String[] args) throws Exception {
		RequestCmd cmd = RequestCmd.getInctance();
		
		CommandLine cmdLine = cmd.parseCommand(args);
		
		if (cmdLine.hasOption('h')) {
			cmd.printHelpAboutCommand(cmd.getCommand());
			return ;
		}
		
		cmd.getCurrentCommand().execute(cmdLine);
	}
	
	private static void configureLogback() throws JoranException, IOException {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator jc = new JoranConfigurator();
		jc.setContext(context);
		context.reset();
		
		// overriding the log directory property programmatically
		String logDirProperty = ".";// ... get alternative log directory location
				
		context.putProperty("LOG_DIR", logDirProperty);		
		//System.out.rintln(CProperties.getProperty("log_root_level"));
		context.putProperty("log_root_level", "debug");
		
		
		ClassLoader classLoader = App.class.getClassLoader();
		File file = new File(classLoader.getResource("logback.xml").getFile());
		FileInputStream fis = new FileInputStream(file);
		jc.doConfigure(fis);
		
		
		/*
		// this assumes that the logback.xml file is in the root of the bundle.
		URL logbackConfigFileUrl = new URL("logback.xml"); 
		jc.doConfigure(logbackConfigFileUrl.openStream());
		*/
	}

	
    public static void main( String[] args ) throws Exception
    {    	
    	/*
    	 * TODO delete
    	ConsoleWriter.println( "я русский");
    	System.out.println("я русский");
        */
        //configureLogback();
               
        try {
        	executeDbGitCommand(args);
        	
        } catch (Exception e) {
        	ConsoleWriter.printlnRed("Error execute dbgit: "+e.getMessage());
        	LoggerUtil.getGlobalLogger().error(e.getMessage(), e);
        } finally {
        	DBGitPath.clearTempDir();
		}
        
    }
    
}
