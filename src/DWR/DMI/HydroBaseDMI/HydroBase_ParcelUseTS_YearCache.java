package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for HydroBase_ParcelUseTS object for a specific year.
 * A list of this class is maintained in HydroBase_ParcelUseTS_ParcelId3Cache.
 * @author sam
 *
 */
public class HydroBase_ParcelUseTS_YearCache {
	
	/*
	 * The calendar year.
	 */
	private int year;
	
	/**
	 * The list of ParcelUseTS for the year.
	 */
	private List<HydroBase_ParcelUseTS> putsList = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_ParcelUseTS_YearCache ( int year ) {
		this.year = year;
	}
	
	/**
	 * Add a data object to the list.
	 * @param putsData ParcelUseTS object to add.
	 */
	public void add ( HydroBase_ParcelUseTS putsData ) {
		this.putsList.add(putsData);
	}

	/**
	 * Get the cached HydroBase_ParcelUseTS records for the given input.
	 * Will have already filtered by division and year.
	 * @param calYear calendar year.
	 * @param parcelNum parcel number of interest.
	 * @param parcelId parcel identifier of interest.
	 * @param landUse land use of interest.
	 * @param irrigType irrigation type of interest.
	 * @return list of matched records, guaranteed to be non-null
	 */
	public List<HydroBase_ParcelUseTS> getData ( int calYear, int parcelNum, int parcelId, String landUse, String irrigType ) {
		// Always return non-null to simplify error handling
		List<HydroBase_ParcelUseTS> putsList = new ArrayList<>();
		// Find the ParcelUseTS data
		for ( HydroBase_ParcelUseTS putsCache : this.putsList ) {
			// The following generates A LOT of output
			// - only use for troubleshooting
			//Message.printStatus(2,"","Trying to match parcelNum=" + parcelNum + "(data=" + putssCache.getParcel_Num() +
			//	") parcelId=" + parcelId + " (data=" + putsCache.getParcel_id() + ") landUse=" + landUse +
			//	" (data=" + wellsCache.getLand_use() + ") irrigType= " + irrigType + (data=" + putsCache.getIrrig_type() + ")" );
			if ( calYear >= 0 ) {
				// Want to match the calendar year
				if ( calYear != putsCache.getCal_year() ) {
					continue;
				}
			}
			if ( parcelNum >= 0 ) {
				// Want to match the parcel_num
				if ( parcelNum != putsCache.getParcel_num() ) {
					continue;
				}
			}
			if ( parcelId >= 0 ) {
				// Want to match the parcel_id
				if ( parcelId != putsCache.getParcel_id() ) {
					continue;
				}
			}
			if ( (landUse != null) && !landUse.isEmpty() ) {
				// Want to match the receipt
				if ( !landUse.equals(putsCache.getLand_use()) ) {
					continue;
				}
			}
			if ( (irrigType != null) && !irrigType.isEmpty() ) {
				// Want to match the receipt
				if ( !irrigType.equals(putsCache.getIrrig_type()) ) {
					continue;
				}
			}
			// Add to the list
			//Message.printStatus(2,"","  Matched.");
			putsList.add(putsCache);
		}
		return putsList;
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_ParcelUseTS get ( int index ) {
		return this.putsList.get(index);
	}
	
	/**
	 * Get the year for the list.
	 */
	public int getYear () {
		return this.year;
	}
}