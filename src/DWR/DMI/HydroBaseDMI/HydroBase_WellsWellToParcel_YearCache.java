package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

import RTi.Util.Message.Message;

/**
 * This class is an cache for WellsWellToParcel data at the year level.
 * It contains a list of HydroBase_Wells for the year.
 * A list of this class is maintained in HydroBase_WellsWellToParcel_WellWDCache and
 * HydroBase_WellsWellToParcel_ParcelId3Cache to support two ways to search for data.
 * @author sam
 *
 */
public class HydroBase_WellsWellToParcel_YearCache {
	
	/*
	 * The calendar year.
	 */
	private int year;

	/**
	 * The list of Wells for the year.
	 */
	private List<HydroBase_Wells> wellsList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_WellsWellToParcel_YearCache ( int year ) {
		this.year = year;
	}

	/**
	 * Add a data object to the list.
	 * @param wellData well object to add.
	 */
	public void add ( HydroBase_Wells wellData ) {
		this.wellsList.add(wellData);
	}
	
	/**
	 * Get the data object at the index well index.
	 */
	public HydroBase_Wells get ( int index ) {
		return this.wellsList.get(index);
	}

	/**
	 * Get the cached HydroBase_Wells records for the given input.
	 * @param calYear calendar year to match, -1 to not check.
	 * @param wd water district (part of WDID) to match, -1 to not check.
	 * @param id structure identifier (part of WDID) to match, -1 to not check.
	 * Missing ID is negative so will check receipt.
	 * @param receipt well receipt to match, null to not check (passing empty string will match empty receipt).
	 * @param parcelId parcel identifier to match, -1 to not check.
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_Wells> getData ( int calYear, int wd, int id, String receipt, int parcelId ) {
		// Always return non-null to simplify error handling
		List<HydroBase_Wells> wellsList = new ArrayList<>();
		boolean debug = false; // Use for troubleshooting
		// Find the wells data
		if ( debug ) {
			Message.printStatus(2,"","Trying to match calYear=" + calYear + " wd=" + wd + " id=" + id +
				" receipt=" + receipt + " parcelId=" + parcelId + " in " + this.wellsList.size() + " cached Wells.");
		}
		for ( HydroBase_Wells wellsCache : this.wellsList ) {
			// The following generates A LOT of output
			// - only use for troubleshooting
			if ( debug ) {
				Message.printStatus(2, "", "Data calYear=" + wellsCache.getCal_year() +
					" wd=" + wellsCache.getWD() +
					" id=" + wellsCache.getID() +
					" receipt=" + wellsCache.getReceipt() +
					" parcelId=\"" + wellsCache.getParcel_id() + "\"" );
			}
			if ( calYear > 0 ) {
				// Want to match the cal_year
				if ( calYear != wellsCache.getCal_year() ) {
					if ( debug ) {
						Message.printStatus(2, "", "Year " + calYear + " not matched.");
					}
					continue;
				}
			}
			if ( parcelId > 0 ) {
				// Want to match the parcel_id
				if ( parcelId != wellsCache.getParcel_id() ) {
					if ( debug ) {
						Message.printStatus(2, "", "Parcel ID " + parcelId + " not matched.");
					}
					continue;
				}
			}
			if ( wd > 0 ) {
				// Want to match the wd
				if ( wd != wellsCache.getWD() ) {
					if ( debug ) {
						Message.printStatus(2, "", "WD " + wd + " not matched.");
					}
					continue;
				}
			}
			if ( id > 0 ) {
				// Want to match the id
				if ( id != wellsCache.getID() ) {
					if ( debug ) {
						Message.printStatus(2, "", "ID " + id + " not matched.");
					}
					continue;
				}
			}
			if ( receipt != null ) {
				// Want to match the receipt
				if ( !receipt.equals(wellsCache.getReceipt()) ) {
					if ( debug ) {
						Message.printStatus(2, "", "Receipt \"" + receipt + "\" not matched.");
					}
					continue;
				}
			}
			// If here have matched criteria.
			// Add to the list
			if ( debug ) {
				Message.printStatus(2, "", "Matched.");
			}
			wellsList.add(wellsCache);
		}
		return wellsList;
	}

	/**
	 * Get the calendar year for the list.
	 */
	public int getYear () {
		return this.year;
	}

	/**
	 * Find the matching cache data object for the given water district.
	 * @param wellsDistrictList list of data that has already been read and cached
	 * @param year calendar year to match
	 * @return cached well/parcel data list for the requested
	 * year or null if not available (in which case the data needs to be read)
	 */
	public static HydroBase_WellsWellToParcel_YearCache getForYear (
		List<HydroBase_WellsWellToParcel_YearCache> wellsYearList, int year ) {
		for ( HydroBase_WellsWellToParcel_YearCache wellsYear : wellsYearList ) {
			if ( wellsYear.getYear() == year ) {
				// Found the data for the district
				return wellsYear;
			}
		}
		return null;
	}
}