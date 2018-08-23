package de.rbgs.srcb.Main;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * A formatter for the game's log files
 *
 * @author Robbi Blechdose
 * 
 */
public class LogFormatter extends Formatter
{
    private final Date date = new Date();
    
    @Override
    public String format(LogRecord record)
    {
        date.setTime(record.getMillis());
	//Copied from SimpleFormatter
	String throwable = "";
        if (record.getThrown() != null)
	{
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
	}
	    
	return"[" + date + " | " + record.getLoggerName() + " " + record.getSourceMethodName() + "] "
		+ record.getLevel() + ": " +
		formatMessage(record) +
		throwable +
		System.getProperty("line.separator");
    }
}
