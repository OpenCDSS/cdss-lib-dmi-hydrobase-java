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
