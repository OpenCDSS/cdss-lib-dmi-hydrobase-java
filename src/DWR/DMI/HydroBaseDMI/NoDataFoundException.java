//------------------------------------------------------------------------------
// NoDataFoundException - an Exception to be thrown when no data are found in
//			HydroBase.
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2006-06-27	Steven A. Malers, RTi	Initial version.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

/**
A NoDataFoundException can be thrown when a HydroBase query results in no
data.  For example, when processing time series, a number of initial queries
may occur to determine if a time series is defined.  If no data are found, then
this exception indicates that no data are available and subsequent queries
cannot be made.
*/
@SuppressWarnings("serial")
public class NoDataFoundException extends Exception
{

public NoDataFoundException ( String message )
{	super ( message );
}

}
