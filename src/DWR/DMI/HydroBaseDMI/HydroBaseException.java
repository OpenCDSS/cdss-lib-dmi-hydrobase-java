// HydroBaseException - an Exception to be thrown when a HydroBase interaction fails

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2019 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

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
@SuppressWarnings("serial")
public class HydroBaseException extends Exception
{

public HydroBaseException ( String message )
{	super ( message );
}

}