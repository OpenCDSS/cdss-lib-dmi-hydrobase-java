package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for ParcelUseTSStructureToParcel_YearCache object for a specific district.
 * Other cache classes will have stored for year and WDID.
 * @author sam
 *
 */
public class HydroBase_ParcelUseTSStructureToParcel_DistrictCache {
	
	/*
	 * The district.
	 */
	private int district;
	
	/**
	 * The list of ParcelUseTSStructureToParcel_YearCache for the year.
	 */
	private List<HydroBase_ParcelUseTSStructureToParcel_YearCache> parcelUseTSForYearList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_ParcelUseTSStructureToParcel_DistrictCache ( int district ) {
		this.district = district;
	}
	
	/**
	 * Add a data object to the list.
	 */
	public void add ( HydroBase_ParcelUseTSStructureToParcel_YearCache yearData ) {
		this.parcelUseTSForYearList.add(yearData);
	}

	/**
	 * Build a cache for data for a water district.
	 * @param wd water district for the cache
	 * @param putsList list of HydroBase_ParcelUseTSStructureToParcel from a query,
	 * sorted by calendar year, wd, and id.
	 */
	public static HydroBase_ParcelUseTSStructureToParcel_DistrictCache createCache(
		int wd, List<HydroBase_ParcelUseTSStructureToParcel> putsList ) {
		HydroBase_ParcelUseTSStructureToParcel_DistrictCache putsDistrictCache =
			new HydroBase_ParcelUseTSStructureToParcel_DistrictCache(wd);
		// Loop through the data records and build cache tree
		// Values to track when something has changed
		int dataYearPrev = -1;
		int dataWdPrev = -1;
		int dataIdPrev = -1;
		int dataYear, dataWd, dataId;
		// Current year cache
		HydroBase_ParcelUseTSStructureToParcel_YearCache putsYearCache = null;
		// Current WDID cache
		HydroBase_ParcelUseTSStructureToParcel_WDIDCache putsWDIDCache = null;
		for ( HydroBase_ParcelUseTSStructureToParcel puts : putsList ) {
			dataYear = puts.getCal_year();
			dataWd = puts.getStructureWD();
			dataId = puts.getStructureID();
			if ( dataYear == dataYearPrev ) {
				// Current year is still ok to use below
			}
			else {
				// New year so create a new year cache
				putsYearCache = new HydroBase_ParcelUseTSStructureToParcel_YearCache(dataYear);
				putsDistrictCache.add(putsYearCache);
			}
			if ( (dataWd == dataWdPrev) && (dataId == dataIdPrev) ) {
				// Current WDID is still ok to use below
			}
			else {
				// New WDID so create a new year cache
				putsWDIDCache = new HydroBase_ParcelUseTSStructureToParcel_WDIDCache(dataWd, dataId);
				putsYearCache.add(putsWDIDCache);
			}
			// Add the record to the WDID cache
			putsWDIDCache.add(puts);
			// Save the information for the next iteration
			dataYearPrev = dataYear;
			dataWdPrev = dataWd;
			dataIdPrev = dataId;
		}
		return putsDistrictCache;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_ParcelUseTSStructureToParcel_YearCache get ( int index ) {
		return this.parcelUseTSForYearList.get(index);
	}

	/**
	 * Get the cached HydroBase_ParcelUseTSStructureToParcel records for the given input.
	 * @param cal_year calendar year of interest
	 * @param wd water district (part of WDID) of interest
	 * @param id structure identifier (part of WDID) of interest
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_ParcelUseTSStructureToParcel> getData ( int cal_year, int wd, int id ) {
		// Always return non-null to simplify error handling
		List<HydroBase_ParcelUseTSStructureToParcel> putsList = new ArrayList<>();
		// Find the year data
		for ( HydroBase_ParcelUseTSStructureToParcel_YearCache putsYearCache : this.parcelUseTSForYearList ) {
			if ( cal_year == putsYearCache.getYear() ) {
				// Matched the requested year. Find the WDID data.
				putsList.addAll(putsYearCache.getData(wd, id));
			}
		}
		return putsList;
	}
	
	/**
	 * Get the district for the list.
	 */
	public int getDistrict () {
		return this.district;
	}

	/**
	 * Find the matching data object for the given water district.
	 * @param parcelUseDistrictList list of data that has already been read and cached
	 * @param wd water district to match
	 * @return HydroBase_ParcelUseTSStructureToParcel_DistrictCache cached parcel data list for the requested
	 * water district or null if not available (in which case the data needs to be read)
	 */
	public static HydroBase_ParcelUseTSStructureToParcel_DistrictCache getForDistrict (
		List<HydroBase_ParcelUseTSStructureToParcel_DistrictCache> parcelUseDistrictList, int wd ) {
		for ( HydroBase_ParcelUseTSStructureToParcel_DistrictCache putsDistrict : parcelUseDistrictList ) {
			if ( putsDistrict.getDistrict() == wd ) {
				// Found the data for the district
				return putsDistrict;
			}
		}
		return null;
	}
}