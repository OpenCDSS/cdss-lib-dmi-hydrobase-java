package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for HydroBase_Wells object for a specific year.
 * Other cache classes will have stored for district.
 * @author sam
 *
 */
public class x_HydroBase_WellsWellToParcel_YearCache {
	
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
	public x_HydroBase_WellsWellToParcel_YearCache ( int year ) {
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
	 * Get the cached HydroBase_Wells records for the given input.
	 * @param wd water district (part of WDID) of interest
	 * @param id structure identifier (part of WDID) of interest
	 * @param parcelId parcel identifier of interest.
	 * @param receipt well receipt to match
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_Wells> getData ( int wd, int id, int parcelId, String receipt ) {
		// Always return non-null to simplify error handling
		List<HydroBase_Wells> wellsList = new ArrayList<>();
		// Find the wells data
		int matchTry = 0;
		int matchFound = 0;
		for ( HydroBase_Wells wellsCache : this.wellsList ) {
			// The following generates A LOT of output
			// - only use for troubleshooting
			//Message.printStatus(2,"","Trying to match wd=" + wd + "(data=" + wellsCache.getWD() +
			//	") id=" + id + " (data=" + wellsCache.getID() + ") parcelId=" + parcelId +
			//	" (data=" + wellsCache.getParcel_id() + ")" );
			if ( parcelId >= 0 ) {
				// Want to match the parcel_id
				++matchTry;
				if ( parcelId == wellsCache.getParcel_id() ) {
					++matchFound;
				}
			}
			if ( wd >= 0 ) {
				// Want to match the wd
				++matchTry;
				if ( wd == wellsCache.getWD() ) {
					++matchFound;
				}
			}
			if ( id >= 0 ) {
				// Want to match the id
				++matchTry;
				if ( id == wellsCache.getID() ) {
					++matchFound;
				}
			}
			if ( (receipt != null) && !receipt.isEmpty() ) {
				// Want to match the receipt
				++matchTry;
				if ( receipt.equals(wellsCache.getReceipt()) ) {
					++matchFound;
				}
			}
			if ( matchTry == matchFound ) {
				// Add to the list
				//Message.printStatus(2,"","  Matched.");
				wellsList.add(wellsCache);
			}
			else {
				//Message.printStatus(2,"","  Not matched.");
			}
		}
		return wellsList;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_Wells get ( int index ) {
		return this.wellsList.get(index);
	}
	
	/**
	 * Get the year for the list.
	 */
	public int getYear () {
		return this.year;
	}
}