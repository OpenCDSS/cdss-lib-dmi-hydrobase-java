//------------------------------------------------------------------------------
// fillUsingDiversionComment_Command - 
// handle the fillUsingDiversionComment() command
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2007-01-25	Kurt Tometich, RTi	Initial version
//					
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

import javax.swing.JFrame;

import java.util.Vector;

import RTi.TS.TS;
import RTi.TS.MonthTSLimits;
import RTi.TS.TSCommandProcessor;
import RTi.TS.TSUtil;

import RTi.Util.Message.Message;
import RTi.Util.Message.MessageUtil;
import RTi.Util.IO.Command;
import RTi.Util.IO.CommandException;
import RTi.Util.IO.CommandWarningException;
import RTi.Util.IO.InvalidCommandParameterException;
import RTi.Util.IO.InvalidCommandSyntaxException;
import RTi.Util.IO.Prop;
import RTi.Util.IO.PropList;
import RTi.Util.IO.SkeletonCommand;
import RTi.Util.String.StringUtil;
import RTi.Util.Time.DateTime;
import RTi.Util.Time.TimeInterval;

/**
<p>
This class initializes, checks, and runs the fillUsingDiversionComments() command.
</p>
<p>The CommandProcessor must return the following properties:  TSResultsList.
</p>
*/
public class fillUsingDiversionComments_Command extends SkeletonCommand
implements Command
{

/**
Protected data members shared with the dialog and other related classes.
*/
protected final String _AllTS = "AllTS";
protected final String _SelectedTS = "SelectedTS";
protected final String _AllMatchingTSID = "AllMatchingTSID";

/**
Constructor.
*/
public fillUsingDiversionComments_Command ()
{	super();
	setCommandName ( "fillUsingDiversionComments" );
}

/**
Check the command parameter for valid values, combination, etc.
@param parameters The parameters for the command.
@param command_tag an indicator to be used when printing messages, to allow a
cross-reference to the original commands.
@param warning_level The warning level to use when printing parse warnings
(recommended is 2 for initialization, and 1 for interactive command editor
dialogs).
*/
public void checkCommandParameters (	PropList parameters, String command_tag,
					int warning_level )
throws InvalidCommandParameterException
{	
	//String TSList = parameters.getValue ( "TSList" );
	String TSID = parameters.getValue ( "TSID" );
	String FillStart = parameters.getValue ( "FillStart" );
	String FillEnd = parameters.getValue ( "FillEnd" );
	String FillFlag = parameters.getValue ( "FillFlag" );
	String RecalcLimits = parameters.getValue ( "RecalcLimits" );
	String warning = "";
	
	if ( (TSID == null) || (TSID.length() == 0) ) {
				warning += "\nA TSID must be specified.";
	}
	if (	(FillStart != null) && !FillStart.equals("") &&
		!FillStart.equalsIgnoreCase("OutputStart")){
		try {	DateTime startdate = DateTime.parse(FillStart);
		}
		catch ( Exception e ) {
			warning += 
				"\nThe fill start date/time \"" + FillStart +
				"\" is not a valid date/time.\n"+
				"Specify a date or OutputStart.";
		}
	}
	if (	(FillEnd != null) && !FillEnd.equals("") &&
		!FillEnd.equalsIgnoreCase("OutputEnd") ) {
		try {	DateTime enddate = DateTime.parse( FillEnd);
		}
		catch ( Exception e ) {
			warning +=
				"\nThe fill end date/time \"" + FillEnd +
				"\" is not a valid date/time.\n"+
				"Specify a date or OutputEnd.";
		}
	}
	if ( (FillFlag != null) && !(FillFlag.equalsIgnoreCase("Auto"))&&
			(FillFlag.length() != 1) ) {
		warning += "\nThe fill flag must be 1 character long or set to Auto.";
	}
	if ( warning.length() > 0 ) {
		Message.printWarning ( warning_level,
		MessageUtil.formatMessageTag(command_tag,warning_level),
		warning );
		throw new InvalidCommandParameterException ( warning );
	}
}

/**
Edit the command.
@param parent The parent JFrame to which the command dialog will belong.
@return true if the command was edited (e.g., "OK" was pressed), and false if
not (e.g., "Cancel" was pressed.
*/
public boolean editCommand ( JFrame parent )
{	// The command will be modified if changed...
	return (new fillUsingDiversionComments_JDialog ( parent, this )).ok();
}


/**
Parse the command string into a PropList of parameters.  This method currently
supports old syntax and new parameter-based syntax.
@param command A string command to parse.
@param command_tag an indicator to be used when printing messages, to allow a
cross-reference to the original commands.
@param warning_level The warning level to use when printing parse warnings
(recommended is 2).
@exception InvalidCommandSyntaxException if during parsing the command is
determined to have invalid syntax.
syntax of the command are bad.
@exception InvalidCommandParameterException if during parsing the command
parameters are determined to be invalid.
*/
public void parseCommand (	String command, String command_tag,
				int warning_level )
throws InvalidCommandSyntaxException, InvalidCommandParameterException
{	
	int warning_count = 0;
	String routine = "fillUsingDiversionComments_Command.parseCommand",
	  message;

	if ( command.indexOf('=') < 0 ) {
	
		Vector v = StringUtil.breakStringList(command,
			"(),\t", StringUtil.DELIM_SKIP_BLANKS |
			StringUtil.DELIM_ALLOW_STRINGS );
		int ntokens = 0;
		if ( v != null ) {
			ntokens = v.size();
		}
		if ( ntokens != 2 ) {
			// Command name and TSID
			message = "Syntax error in \"" + command +
			"\".  One token expected.";
			Message.printWarning ( warning_level, routine, message);
			throw new InvalidCommandSyntaxException ( message );
		}

		// Get the individual tokens of the expression...
		String TSID = ((String)v.elementAt(1)).trim();
		
		// Set parameters and new defaults...
		_parameters = new PropList ( getCommandName() );
		_parameters.setHowSet ( Prop.SET_FROM_PERSISTENT );
		if ( TSID.length() > 0 ) {
			_parameters.set ( "TSID", TSID );
			_parameters.setHowSet(Prop.SET_AS_RUNTIME_DEFAULT);	
		}
		_parameters.setHowSet ( Prop.SET_UNKNOWN );
	}
	else {	// Current syntax...
		Vector tokens = StringUtil.breakStringList ( command,
			"()", StringUtil.DELIM_SKIP_BLANKS );
		if ( (tokens == null) || tokens.size() < 1 ) {
			// Must have at least the command name, TSID
			message = "Syntax error in \"" + command +
				"\".  Not enough tokens.";
			Message.printWarning ( warning_level,
			MessageUtil.formatMessageTag(
			command_tag,++warning_count), routine, message);
			throw new InvalidCommandSyntaxException ( message );
		}
		// Get the input needed to process the file...
		try {	_parameters = PropList.parse ( Prop.SET_FROM_PERSISTENT,
				(String)tokens.elementAt(1), routine, "," );
		}
		catch ( Exception e ) {
			message = "Syntax error in \"" + command +
				"\".  Not enough tokens.";
			Message.printWarning ( warning_level,
			MessageUtil.formatMessageTag(
			command_tag,++warning_count), routine, message);
			throw new InvalidCommandSyntaxException ( message );
		}
	}
}


/**
Helper method to execute the fillUsingDiversionComments() command.
@param String: command Command to process.
@param int:  The message warning level to write to the log file with.
@exception Exception if there is an error processing the command.
*/
public void runCommand ( String command, int warningLevel )
throws InvalidCommandParameterException,
CommandWarningException, CommandException
{	
	String message, routine = "fillUsingDiversionComments_Command.runCommand";
	String TSID = _parameters.getValue ( "TSID" );
	String FillStart = _parameters.getValue ( "FillStart" );
	String FillEnd = _parameters.getValue ( "FillEnd" );
	String FillFlag = _parameters.getValue ( "FillFlag" );
	String RecalcLimits = _parameters.getValue ( "RecalcLimits" );
	
	DateTime start = null;
	DateTime end = null;
	try {	if ( FillStart != null ) {
			start = DateTime.parse(FillStart);
		}
	}
	catch ( Exception e ) {
		message = "Fill start is not a valid date.  Ignoring date.";
		Message.printWarning ( warningLevel, routine, message );
		throw new InvalidCommandParameterException ( message );
	}
	try {	if ( FillEnd != null ) {
			end = DateTime.parse(FillEnd);
		}
	}
	catch ( Exception e ) {
		message = "Fill end is not a valid date.  Ignoring date.";
		Message.printWarning ( warningLevel, routine, message );
		throw new InvalidCommandParameterException ( message );
	}
	TS ts = null;			// Time series instance to update
	HydroBaseDMI hbdmi = null;	// HydroBaseDMI to use
	TSCommandProcessor TSCmdProc = (TSCommandProcessor)_processor;
	
	if ( TSID.equals("*") ) {
		// Fill everything in memory...
		int nts = TSCmdProc.getTimeSeriesSize();
		for ( int its = 0; its < nts; its++ ) {
			try {
				ts = TSCmdProc.getTimeSeries(its);
			} catch (Exception e1) {
				Message.printWarning(warningLevel, routine, e1);
			}
			hbdmi = TSCmdProc.getHydroBaseDMI (
				ts.getIdentifier().getInputName() );
			
			if ( TSCmdProc.haveOutputPeriod() ) {
				// No need to extend the period...
				try {
					HydroBase_Util.fillTSUsingDiversionComments (
					hbdmi, ts, start, end, FillFlag, false );
				} catch (Exception e) {
					Message.printWarning(warningLevel, routine, e);
				}
			}
			else {	// Extend the period if data are available...
				try {
					HydroBase_Util.fillTSUsingDiversionComments (
					hbdmi, ts, start, end, FillFlag, true );
				} catch (Exception e) {
					Message.printWarning(warningLevel, routine, e);
				}
			}
			if ( RecalcLimits.equalsIgnoreCase("True") ) {
				try {	ts.setDataLimitsOriginal (
						TSCmdProc.calculateTSAverageLimits(ts));
				}
				catch ( Exception e ) {
					Message.printWarning ( 2, routine,
					"Error recalculating original data " +
					"limits for \"" +
					ts.getIdentifierString() + "\""  );
					Message.printWarning ( 2, routine, e );
				}
			}
			// Update...
			try {
				TSCmdProc.processTimeSeriesAction ( TSCmdProc.getUpdateTS(), 
					ts, its );
			} catch (Exception e) {
				Message.printWarning(warningLevel, routine, e);
			}
		}
	}
	else {	// Fill one time series...
		int ts_pos = TSCmdProc.indexOf ( TSID );
		if ( ts_pos >= 0 ) {
			try {
				ts = TSCmdProc.getTimeSeries ( ts_pos );
			} catch (Exception e1) {
				Message.printWarning(warningLevel, routine, e1);
			}
			hbdmi = TSCmdProc.getHydroBaseDMI (
				ts.getIdentifier().getInputName() );
			
			if ( TSCmdProc.haveOutputPeriod() ) {
				// No need to extend the period...
				try {
					HydroBase_Util.fillTSUsingDiversionComments (
					hbdmi, ts, start, end, FillFlag, false );
				} catch (Exception e) {
					Message.printWarning(warningLevel, routine, e);
				}
			}
			else {	// Extend the period...
				try {
					HydroBase_Util.fillTSUsingDiversionComments (
					hbdmi, ts, start, end, FillFlag, true );
				} catch (Exception e) {
					Message.printWarning(warningLevel, routine, e);
				}
			}
			if ( RecalcLimits.equalsIgnoreCase("True") ) {
				try {	ts.setDataLimitsOriginal (
						TSCmdProc.calculateTSAverageLimits(ts));
				}
				catch ( Exception e ) {
					Message.printWarning ( 2, routine,
					"Error recalculating original data " +
					"limits for \"" +
					ts.getIdentifierString() + "\""  );
					Message.printWarning ( 2, routine, e );
				}
			}
			try {
				TSCmdProc.processTimeSeriesAction ( TSCmdProc.getUpdateTS(), ts, ts_pos );
			} catch (Exception e) {
				Message.printWarning(warningLevel, routine, e);
			}
		}
		else {	message = "Unable to find time series \"" +
				TSID + "\" for fillUsingDiversionComments() " +
				"command.";
			Message.printWarning ( 2, routine, message );
			try {
				throw new Exception ( message );
			} catch (Exception e) {
				Message.printWarning(warningLevel, routine, e);
			}
		}
	}
}


/**
Return the string representation of the command.
*/
public String toString ( PropList props )
{	if ( props == null ) {
		return getCommandName() + "()";
	}
	//String TSList = props.getValue( "TSList" );
	String TSID = props.getValue( "TSID" );
	//String ConstantValue = props.getValue( "ConstantValue" );
	String FillStart = props.getValue( "FillStart" );
	String FillEnd = props.getValue( "FillEnd" );
	String FillFlag = props.getValue( "FillFlag" );
	String RecalcLimits = props.getValue( "RecalcLimits" );
	StringBuffer b = new StringBuffer ();
	
	if ( (TSID != null) && (TSID.length() > 0) ) {
		if ( b.length() > 0 ) {
			b.append ( "," );
		}
		b.append ( "TSID=\"" + TSID + "\"" );
	}
	if ( (FillStart != null) && (FillStart.length() > 0) ) {
		if ( b.length() > 0 ) {
			b.append ( "," );
		}
		b.append ( "FillStart=\"" + FillStart + "\"" );
	}
	if ( (FillEnd != null) && (FillEnd.length() > 0) ) {
		if ( b.length() > 0 ) {
			b.append ( "," );
		}
		b.append ( "FillEnd=\"" + FillEnd + "\"" );
	}
	if ( (FillFlag != null) && (FillFlag.length() > 0) ) {
		if ( b.length() > 0 ) {
			b.append ( "," );
		}
		b.append ( "FillFlag=\"" + FillFlag + "\"" );
	}
	if ( ( RecalcLimits != null) && (RecalcLimits.length() > 0) ) {
		if ( b.length() > 0 ) {
			b.append ( "," );
		}
		b.append ( "RecalcLimits=\"" + RecalcLimits + "\"" );
	}
	
	return getCommandName() + "(" + b.toString() + ")";
}

}
