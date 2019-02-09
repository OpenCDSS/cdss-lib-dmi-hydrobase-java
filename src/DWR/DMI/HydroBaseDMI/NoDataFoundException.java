// NoDataFoundException - an Exception to be thrown when no data are found in HydroBase.

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
