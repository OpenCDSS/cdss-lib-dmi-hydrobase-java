package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for ParcelUseTSStructureToParcel_WDIDCache object for a specific year.
 * Other cache classes will have stored for district and WDID.
 * @author sam
 *
 */
public class HydroBase_ParcelUseTSStructureToParcel_YearCache {
	
	/*
	 * The calendar year.
	 */
	private int year;
	
	/**
	 * The list of ParcelUseTSStructureToParcel_WDIDCache for the year.
	 */
	private List<HydroBase_ParcelUseTSStructureToParcel_WDIDCache> parcelUseTSForWDIDList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_ParcelUseTSStructureToParcel_YearCache ( int year ) {
		this.year = year;
	}
	
	/**
	 * Add a data object to the list.
	 */
	public void add ( HydroBase_ParcelUseTSStructureToParcel_WDIDCache wdidData ) {
		this.parcelUseTSForWDIDList.add(wdidData);
	}

	/**
	 * Get the cached HydroBase_ParcelUseTSStructureToParcel records for the given input.
	 * @param wd water district (part of WDID) of interest
	 * @param id structure identifier (part of WDID) of interest
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_ParcelUseTSStructureToParcel> getData ( int wd, int id ) {
		// Always return non-null to simplify error handling
		List<HydroBase_ParcelUseTSStructureToParcel> putsList = new ArrayList<>();
		// Find the WDID data
		for ( HydroBase_ParcelUseTSStructureToParcel_WDIDCache putsWDIDCache : this.parcelUseTSForWDIDList ) {
			if ( (wd == putsWDIDCache.getWd()) && (id == putsWDIDCache.getId()) ) {
				// Matched the requested year. Find the WDID data.
				putsList.addAll(putsWDIDCache.getData());
			}
		}
		return putsList;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_ParcelUseTSStructureToParcel_WDIDCache get ( int index ) {
		return this.parcelUseTSForWDIDList.get(index);
	}
	
	/**
	 * Get the year for the list.
	 */
	public int getYear () {
		return this.year;
	}
}