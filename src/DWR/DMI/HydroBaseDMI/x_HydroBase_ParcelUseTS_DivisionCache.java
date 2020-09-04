package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for ParcelUseTS_YearCache object for a specific division.
 * TODO smalers 2020-08-25 it may be possible to divide by district but ParcelUseTS data records
 * only have division as most granular spatial data field.
 * The StateDMI_Processor
 * @author sam
 *
 */
public class x_HydroBase_ParcelUseTS_DivisionCache {
	
	/*
	 * The division.
	 */
	private int division;
	
	/**
	 * The list of ParcelUseTS_YearCache for the year.
	 */
	private List<HydroBase_ParcelUseTS_YearCache> parcelUseTSForYearList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public x_HydroBase_ParcelUseTS_DivisionCache ( int division ) {
		this.division = division;
	}
	
	/**
	 * Add a data object to the list.
	 */
	public void add ( HydroBase_ParcelUseTS_YearCache yearData ) {
		this.parcelUseTSForYearList.add(yearData);
	}

	/**
	 * Build a cache for data for a water division and year.
	 * @param div water division for the cache
	 * @param putsList list of HydroBase_ParcelUseTS from a query,
	 * MUST be sorted by division and calendar year. 
	 */
	public static x_HydroBase_ParcelUseTS_DivisionCache createCache(
		int div, List<HydroBase_ParcelUseTS> putsList ) {
		x_HydroBase_ParcelUseTS_DivisionCache putsDivisionCache = new x_HydroBase_ParcelUseTS_DivisionCache(div);
		// Loop through the data records and build cache tree
		// Values to track when something has changed
		int dataYearPrev = -1;
		int dataYear;
		// Current year cache
		HydroBase_ParcelUseTS_YearCache putsYearCache = null;
		for ( HydroBase_ParcelUseTS puts : putsList ) {
			dataYear = puts.getCal_year();
			if ( dataYear == dataYearPrev ) {
				// Current year is still ok to use below
			}
			else {
				// New year so create a new year cache
				putsYearCache = new HydroBase_ParcelUseTS_YearCache(dataYear);
				putsDivisionCache.add(putsYearCache);
			}
			// Add the record to the year cache
			putsYearCache.add(puts);
			// Save the information for the next iteration
			dataYearPrev = dataYear;
		}
		return putsDivisionCache;
	}
	
	/**
	 * Get the data object at the index.:w
	 * 
	 */
	public HydroBase_ParcelUseTS_YearCache get ( int index ) {
		return this.parcelUseTSForYearList.get(index);
	}

	/**
	 * Get the cached HydroBase_ParcelUseTS records for the given input.
	 * @param cal_year calendar year of interest
	 * @param parcelId parcel identifier of interest
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_ParcelUseTS> getData ( int cal_year, int parcelId ) {
		// Always return non-null to simplify error handling
		List<HydroBase_ParcelUseTS> putsList = new ArrayList<>();
		// Find the year data
		for ( HydroBase_ParcelUseTS_YearCache putsYearCache : this.parcelUseTSForYearList ) {
			if ( cal_year == putsYearCache.getYear() ) {
				// Matched the requested year. Find the data matching the request.
				// TODO smalers commented to prevent compile error
				//putsList.addAll(putsYearCache.getData( -1, parcelId, null, null));
			}
		}
		return putsList;
	}
	
	/**
	 * Get the district for the list.
	 */
	public int getDivision () {
		return this.division;
	}

	/**
	 * Find the matching data object for the given water division.
	 * @param parcelUseDivisionList list of data that has already been read and cached
	 * @param div water division to match
	 * @return HydroBase_ParcelUseTS_DivisionCache cached parcel data list for the requested
	 * water division or null if not available (in which case the data needs to be read)
	 */
	public static x_HydroBase_ParcelUseTS_DivisionCache getForDivision (
		List<x_HydroBase_ParcelUseTS_DivisionCache> parcelUseDivisionList, int div ) {
		for ( x_HydroBase_ParcelUseTS_DivisionCache putsDivision : parcelUseDivisionList ) {
			if ( putsDivision.getDivision() == div ) {
				// Found the data for the division
				return putsDivision;
			}
		}
		return null;
	}
}