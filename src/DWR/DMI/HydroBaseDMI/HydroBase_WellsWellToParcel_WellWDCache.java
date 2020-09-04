package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

import RTi.Util.Message.Message;

/**
 * This class is a cache for HydroBase_Wells object for a well WD (water district).
 * A list of these objects is maintained in the HydroBase_WellsWellToParcel_YearCache object.
 * @author sam
 */
public class HydroBase_WellsWellToParcel_WellWDCache {
	
	/*
	 * The water district.
	 */
	private int wd;
	
	/**
	 * The list of WellsWellToParcel_YearCache for years.
	 */
	private List<HydroBase_WellsWellToParcel_YearCache> wellsForYearList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_WellsWellToParcel_WellWDCache ( int wd ) {
		this.wd = wd;
	}
	
	/**
	 * Add a data object to the list.
	 * @param yearData add year of data for a year
	 */
	public void add ( HydroBase_WellsWellToParcel_YearCache yearData ) {
		this.wellsForYearList.add(yearData);
	}

	/**
	 * Build a cache for data for a top-level wellWD index.
	 * @param wellWD well WD for the cache
	 * @param putsList list of HydroBase_Wells from a query,
	 * sorted by well WD and calendar year
	 */
	public static HydroBase_WellsWellToParcel_WellWDCache createCacheForWellWD ( int wellWD, List<HydroBase_Wells> wellsList ) {
		// Create container for the well ID
		HydroBase_WellsWellToParcel_WellWDCache wellsWellWDCache = new HydroBase_WellsWellToParcel_WellWDCache(wellWD);
		// Loop through the data records and build cache tree
		// Values to track when something has changed
		int dataYearPrev = -1;
		//int dataWellWDPrev = -1;
		//int dataIdPrev = -1;
		//int dataParcelId3Prev = -1;
		int dataYear;
		int dataWellWD;
		//int dataId
		//int dataParcelId3;
		//int dataId, dataParcelId;
		// Year cache within the Well WD object
		HydroBase_WellsWellToParcel_YearCache wellsYearCache = null;
		for ( HydroBase_Wells wells : wellsList ) {
			dataYear = wells.getCal_year();
			dataWellWD = wells.getWD();
			if ( dataWellWD != wellWD ) {
				// Major problem.  Data was queried for more than one WD but this method expects one WD.
				throw new RuntimeException ( "Well has WD=" + dataWellWD + ".  All data should match WD=" + wellWD);
			}
			//dataId = wells.getID();
			//dataParcelId = wells.getParcel_id();
			//dataParcelId3 = wells.getParcel_id3();
			if ( dataYear == dataYearPrev ) {
				// Current year is still ok to use below
			}
			else {
				// New year so create a new year cache
				// - first record will cause this
				wellsYearCache = new HydroBase_WellsWellToParcel_YearCache(dataYear);
				wellsWellWDCache.add(wellsYearCache);
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
		return wellsWellWDCache;
	}

	/**
	 * Get the data object at the index.
	 */
	public HydroBase_WellsWellToParcel_YearCache get ( int index ) {
		return this.wellsForYearList.get(index);
	}

	/**
	 * Get the cached HydroBase_Wells records for the given input.
	 * This version is used to find data for parcels associated with wells.
	 * @param calYear calendar year to match, used for second level tree index search
	 * @param wellWD well WD to match
	 * @param wellID well ID to match
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_Wells> getData ( int calYear, int wellWD, int wellID, String receipt ) {
		// Always return non-null to simplify error handling
		List<HydroBase_Wells> wellsList = new ArrayList<>();
		// Find the year data
		int parcelId = -1;
		boolean debug = false; // Use for troubleshooting
		if ( debug ) {
			Message.printStatus(2, "", "Tring to find calyear=" + calYear + " in " + this.wellsForYearList.size() + " year caches.");
		}
		for ( HydroBase_WellsWellToParcel_YearCache wellsYearCache : this.wellsForYearList ) {
			if ( debug ) {
				Message.printStatus(2, "", "cache calyear=" + wellsYearCache.getYear() );
			}
			if ( calYear == wellsYearCache.getYear() ) {
				// Matched the requested parcel year.  Find the requested data in the list.
				// - must pass calendar year, well WD, and well ID for unique match
				if ( debug ) {
					Message.printStatus(2, "", "Found calyear=" + calYear );
				}
				wellsList.addAll(wellsYearCache.getData(calYear, wellWD, wellID, receipt, parcelId));
			}
		}
		return wellsList;
	}

	/**
	 * Find the matching cache data object for the given well WD.
	 * @param wellsDistrictList list of data that has already been read and cached
	 * @param wellWD well WD
	 * @return cached well/parcel data list for the requested
	 * year or null if not available (in which case the data needs to be read)
	 */
	public static HydroBase_WellsWellToParcel_WellWDCache getForWellWD (
		List<HydroBase_WellsWellToParcel_WellWDCache> wellsWellWDList, int wd ) {
		for ( HydroBase_WellsWellToParcel_WellWDCache wellsWellWD : wellsWellWDList ) {
			if ( wellsWellWD.getWD() == wd ) {
				// Found the data for the well WD
				return wellsWellWD;
			}
		}
		return null;
	}
	
	/**
	 * Get the water district for the list.
	 */
	public int getWD () {
		return this.wd;
	}
}