package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for HydroBase_Wells object for a well WD (water district).
 * A list of these objects is maintained in the HydroBase_WellsWellToParcel_YearCache object.
 * @author sam
 */
public class HydroBase_WellsWellToParcel_ParcelId3Cache {
	
	/*
	 * The parcel ID, first 3-digits.
	 */
	private int parcelId3;
	
	/**
	 * The list of WellsWellToParcel_YearCache for years.
	 */
	private List<HydroBase_WellsWellToParcel_YearCache> wellsForYearList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_WellsWellToParcel_ParcelId3Cache ( int parcelId3 ) {
		this.parcelId3 = parcelId3;
	}

	/**
	 * Add a data object to the list.
	 * @param yearData add year of data for a year
	 */
	public void add ( HydroBase_WellsWellToParcel_YearCache yearData ) {
		this.wellsForYearList.add(yearData);
	}

	/**
	 * Build a cache for data for a top-level parcelId3 index.
	 * @param parcelId3 3-digit parcel ID for the cache
	 * @param putsList list of HydroBase_Wells from a query,
	 * sorted by parcelID and calendar year
	 */
	public static HydroBase_WellsWellToParcel_ParcelId3Cache createCacheForParcelId3 ( int parcelId3, List<HydroBase_Wells> wellsList ) {
		// Create container for the parcel 3-digit ID
		HydroBase_WellsWellToParcel_ParcelId3Cache wellsParcelId3Cache = new HydroBase_WellsWellToParcel_ParcelId3Cache(parcelId3);
		// Loop through the data records and build cache tree
		// Values to track when something has changed
		int dataYearPrev = -1;
		//int dataWDPrev = -1;
		//int dataIdPrev = -1;
		//int dataParcelId3Prev = -1;
		int dataYear;
		//int dataWD;
		//int dataId
		int dataParcelId3;
		//int dataId;
		//int dataParcelId;
		// Year cache within the 3-digit parcel ID object
		HydroBase_WellsWellToParcel_YearCache wellsYearCache = null;
		for ( HydroBase_Wells wells : wellsList ) {
			dataYear = wells.getCal_year();
			//dataWD = wells.getWD();
			//dataId = wells.getID();
			//dataParcelId = wells.getParcel_id();
			dataParcelId3 = wells.getParcel_id3();
			if ( dataParcelId3 != parcelId3 ) {
				// Major problem.  Data was queried for more than one 3-digit parcel ID but this method expects one.
				throw new RuntimeException ( "Well has 3-digit parcel ID=" + dataParcelId3 + ".  All data should 3-digit ID=" + parcelId3);
			}
			if ( dataYear == dataYearPrev ) {
				// Current year is still ok to use below
			}
			else {
				// New year so create a new year cache
				// - first record will cause this
				wellsYearCache = new HydroBase_WellsWellToParcel_YearCache(dataYear);
				wellsParcelId3Cache.add(wellsYearCache);
			}
			// Add the record to the year cache
			wellsYearCache.add(wells);
			// Save the information for the next iteration
			// Currently not granular beyond year
			//dataParcelId3Prev = dataParcelId3;
			dataYearPrev = dataYear;
			// dataIdPrev = dataId;
			// dataParcelIdPrev = dataParcelId;
		}
		return wellsParcelId3Cache;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_WellsWellToParcel_YearCache get ( int index ) {
		return this.wellsForYearList.get(index);
	}

	/**
	 * Get the cached HydroBase_Wells records for the given input.
	 * This version is used to find data for parcels associated with ditches.
	 * @param calYear calendar year of interest, used for second level tree index search
	 * @param parcelId full parcel identifier, used to match data record
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_Wells> getData ( int calYear, int parcelId ) {
		// Always return non-null to simplify error handling
		List<HydroBase_Wells> wellsList = new ArrayList<>();
		// Find the year data
		int wellWD = -1;
		int wellID = -1;
		String receipt = null;
		for ( HydroBase_WellsWellToParcel_YearCache wellsYearCache : this.wellsForYearList ) {
			if ( calYear == wellsYearCache.getYear() ) {
				// Matched the requested parcel year.  Find the requested data in the list.
				// - must pass calendar year and parcel ID for unique match
				wellsList.addAll(wellsYearCache.getData(calYear, wellWD, wellID, receipt, parcelId));
			}
		}
		return wellsList;
	}

	/**
	 * Find the matching cache data object for the given 3-digit parcel identifier.
	 * @param wellsDistrictList list of data that has already been read and cached
	 * @param parcelId3 3-digit parcel identifier
	 * @return cached well/parcel data list for the requested
	 * year or null if not available (in which case the data needs to be read)
	 */
	public static HydroBase_WellsWellToParcel_ParcelId3Cache getForParcelId3 (
		List<HydroBase_WellsWellToParcel_ParcelId3Cache> wellsParcelId3List, int parcelId3 ) {
		for ( HydroBase_WellsWellToParcel_ParcelId3Cache wellsParcelId3 : wellsParcelId3List ) {
			if ( wellsParcelId3.getParcelId3() == parcelId3 ) {
				// Found the data for the 3-digit parcel ID
				return wellsParcelId3;
			}
		}
		return null;
	}
	
	/**
	 * Get the parcelId3 for the list.
	 */
	public int getParcelId3 () {
		return this.parcelId3;
	}
}