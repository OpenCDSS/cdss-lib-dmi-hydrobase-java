//------------------------------------------------------------------------------
// HydroBaseException - an Exception to be thrown when a HydroBase interaction
//				fails (for example a query throws an exception)
//------------------------------------------------------------------------------
// Copyright:	See the COPYRIGHT file.
//------------------------------------------------------------------------------
// History:
//
// 2006-04-26	Steven A. Malers, RTi	Initial version.
//------------------------------------------------------------------------------
// EndHeader

package DWR.DMI.HydroBaseDMI;

/**
A HydroBaseException should be thrown when a HydroBase interaction fails.  For
example, catch a low-level SQLException and then throw a HydroBaseException with
a more user-friendly message.
*/
public class HydroBaseException extends Exception
{

public HydroBaseException ( String message )
{	super ( message );
}

}
