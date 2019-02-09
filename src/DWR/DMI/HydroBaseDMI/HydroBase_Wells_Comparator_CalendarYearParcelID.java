// HydroBase_Wells_Comparator_CalendarYearParcelID - Comparator implementation to compare HydroBase_Wells using calendar year and parcel ID.

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

package DWR.DMI.HydroBaseDMI;

import java.util.Comparator;

/**
Comparator implementation to compare HydroBase_Wells using calendar year and parcel ID.
*/
public class HydroBase_Wells_Comparator_CalendarYearParcelID implements Comparator<HydroBase_Wells>
{

/**
Compare two HydroBase_Wells objects, as per the Comparator interface, sorting by calendar year and then parcel ID.
@param o1 first instance
@param o2 second instance
@return 1 if o1 is greater, -1 if o1 is less, and 0 if the same (based on calendar year and parcel ID).
*/
public int compare ( HydroBase_Wells o1, HydroBase_Wells o2 )
{
	int year1 = o1.getCal_year();
	int year2 = o2.getCal_year();
	if ( year1 > year2 ) {
		return 1;
	}
	else if ( year1 < year2 ) {
		return -1;
	}
	
	int id1 = o1.getParcel_id();
	int id2 = o2.getParcel_id();
	if ( id1 > id2 ) {
		return 1;
	}
	else if ( id1 < id2 ) {
		return -1;
	}
	
	// Fall through...
	return 0;
}

}
