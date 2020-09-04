package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for WellsWellToParcel_YearCache object for a specific district.
 * Other cache classes will have stored for year.
 * @author sam
 *
 */
public class x_HydroBase_WellsWellToParcel_DistrictCache {
	
	/*
	 * The district.
	 */
	private int district;
	
	/**
	 * The list of WellsWellToParcel_YearCache for the year.
	 */
	private List<x_HydroBase_WellsWellToParcel_YearCache> wellsForYearList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public x_HydroBase_WellsWellToParcel_DistrictCache ( int district ) {
		this.district = district;
	}
	
	/**
	 * Add a data object to the list.
	 */
	public void add ( x_HydroBase_WellsWellToParcel_YearCache yearData ) {
		this.wellsForYearList.add(yearData);
	}

	/**
	 * Build a cache for data for a water district.
	 * @param wd water district for the cache
	 * @param putsList list of HydroBase_ParcelUseTSStructureToParcel from a query,
	 * sorted by calendar year, wd, and id.
	 */
	public static x_HydroBase_WellsWellToParcel_DistrictCache createCache( int wd, List<HydroBase_Wells> wellsList ) {
		x_HydroBase_WellsWellToParcel_DistrictCache wellsDistrictCache = new x_HydroBase_WellsWellToParcel_DistrictCache(wd);
		// Loop through the data records and build cache tree
		// Values to track when something has changed
		int dataYearPrev = -1;
		//int dataWdPrev = -1;
		//int dataIdPrev = -1;
		//int dataParcelIdPrev = -1;
		int dataYear;
		//int dataWd, dataId, dataParcelId;
		// Current year cache
		x_HydroBase_WellsWellToParcel_YearCache wellsYearCache = null;
		for ( HydroBase_Wells wells : wellsList ) {
			dataYear = wells.getCal_year();
			//dataWd = wells.getWD();
			//dataId = wells.getID();
			//dataParcelId = wells.getParcel_id();
			if ( dataYear == dataYearPrev ) {
				// Current year is still ok to use below
			}
			else {
				// New year so create a new year cache
				wellsYearCache = new x_HydroBase_WellsWellToParcel_YearCache(dataYear);
				wellsDistrictCache.add(wellsYearCache);
			}
			// Add the record to the WDID cache
			wellsYearCache.add(wells);
			// Save the information for the next iteration
			dataYearPrev = dataYear;
			// Currently not granular beyond year
			//dataWdPrev = dataWd;
			// dataIdPrev = dataId;
			// dataParcelIdPrev = dataParcelId;
		}
		return wellsDistrictCache;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public x_HydroBase_WellsWellToParcel_YearCache get ( int index ) {
		return this.wellsForYearList.get(index);
	}

	/**
	 * Get the cached HydroBase_Wells records for the given input.
	 * Typically the key information is the parcelId since looking for wells associated with the parcel
	 * @param calYear calendar year of interest
	 * @param wd water district (part of WDID) of interest
	 * @param id structure identifier (part of WDID) of interest
	 * @param parcelId parcel identifier to match
	 * @param receipt well receipt to match
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_Wells> getData ( int calYear, int wd, int id, int parcelId, String receipt ) {
		// Always return non-null to simplify error handling
		List<HydroBase_Wells> wellsList = new ArrayList<>();
		// Find the year data
		for ( x_HydroBase_WellsWellToParcel_YearCache wellsYearCache : this.wellsForYearList ) {
			if ( calYear == wellsYearCache.getYear() ) {
				// Matched the requested year. Find the requested data.
				wellsList.addAll(wellsYearCache.getData(wd, id, parcelId, receipt));
			}
		}
		return wellsList;
	}
	
	/**
	 * Get the district for the list.
	 */
	public int getDistrict () {
		return this.district;
	}

	/**
	 * Find the matching data object for the given water district.
	 * @param wellsDistrictList list of data that has already been read and cached
	 * @param wd water district to match
	 * @return HydroBase_ParcelUseTSStructureToParcel_DistrictCache cached parcel data list for the requested
	 * water district or null if not available (in which case the data needs to be read)
	 */
	public static x_HydroBase_WellsWellToParcel_DistrictCache getForDistrict (
		List<x_HydroBase_WellsWellToParcel_DistrictCache> wellsDistrictList, int wd ) {
		for ( x_HydroBase_WellsWellToParcel_DistrictCache wellsDistrict : wellsDistrictList ) {
			if ( wellsDistrict.getDistrict() == wd ) {
				// Found the data for the district
				return wellsDistrict;
			}
		}
		return null;
	}
}