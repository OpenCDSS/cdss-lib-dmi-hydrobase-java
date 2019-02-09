// HydroBase_CUPenmanMonteith_Sorter - Sort HydroBase_CUPenmanMonteith by crop name, growth stage, and curve position.

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
Sort HydroBase_CUPenmanMonteith by crop name, growth stage, and curve position.  This is necessary because
HydroBase does not have a sort order implemented for the Penman-Monteith data.
*/
public class HydroBase_CUPenmanMonteith_Sorter implements Comparator<HydroBase_CUPenmanMonteith>
{

/**
Compare two HydroBase_CUPenmanMonteith instances for sorting.
*/
public int compare ( HydroBase_CUPenmanMonteith pm1, HydroBase_CUPenmanMonteith pm2 )
{
	int nameCompare = pm1.getCropname().toUpperCase().compareTo(pm2.getCropname().toUpperCase());
	if ( nameCompare != 0 ) {
		return nameCompare;
	}
	else {
		// Need to compare more parts...
		int pm1gs = pm1.getGrowthstage_no();
		int pm2gs = pm2.getGrowthstage_no();
		if ( pm1gs < pm2gs ) {
			return -1;
		}
		else if ( pm1gs > pm2gs ) {
			return 1;
		}
		else {
			// Growth stage is same so check the position
			return Double.compare(pm1.getCurve_value(), pm2.getCurve_value());
		}
	}
}

}
