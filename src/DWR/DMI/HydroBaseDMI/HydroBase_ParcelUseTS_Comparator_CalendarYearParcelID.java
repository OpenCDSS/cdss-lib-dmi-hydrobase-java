// HydroBase_ParcelUseTS_Comparator_CalendarYearParcelID - compare two ParcelUseTS objects

package DWR.DMI.HydroBaseDMI;

import java.util.Comparator;

public class HydroBase_ParcelUseTS_Comparator_CalendarYearParcelID implements Comparator<HydroBase_ParcelUseTS>
{

/**
Compare two ParcelUseTS objects, as per the Comparator interface, sorting by calendar year and then parcel ID.
@param o1 first instance
@param o2 second instance
@return 1 if o1 is greater, -1 if o1 is less, and 0 if the same (based on calendar year and parcel ID).
*/
public int compare ( HydroBase_ParcelUseTS o1, HydroBase_ParcelUseTS o2 )
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
