package DWR.DMI.HydroBaseDMI;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a cache for ParcelUseTSStructureToParcel object for a specific WDID.
 * Other cache classes will have stored for calendar year and district.
 * @author sam
 *
 */
public class HydroBase_ParcelUseTSStructureToParcel_WDIDCache {
	
	/*
	 * The water district for the WDID.
	 */
	private int wd;
	
	/**
	 * The ID for the WDID.
	 */
	private int id;
	
	/**
	 * The list of ParcelUseTSStructureToParcel for the WD.
	 */
	private List<HydroBase_ParcelUseTSStructureToParcel> parcelUseTSListForWDID = new ArrayList<>();
	
	/**
	 * Constructor.
	 */
	public HydroBase_ParcelUseTSStructureToParcel_WDIDCache ( int wd, int id ) {
		this.wd = wd;
		this.id = id;
	}
	
	/**
	 * Add a data object to the list.
	 */
	public void add ( HydroBase_ParcelUseTSStructureToParcel puts ) {
		this.parcelUseTSListForWDID.add(puts);
	}
	
	/**
	 * Get the data object at the index.
	 */
	public HydroBase_ParcelUseTSStructureToParcel get ( int index ) {
		return this.parcelUseTSListForWDID.get(index);
	}

	/**
	 * Get the list of data objects matching the parcel ID.
	 * @param putsList list to add to, if null create a new list - fastest if list is supplied
	 * @param parcelId parcel identifier to match.
	 * @return the list of objects matching parcelId
	 */
	public List<HydroBase_ParcelUseTSStructureToParcel> getDataForParcelId (
		List<HydroBase_ParcelUseTSStructureToParcel> putsList, int parcelId ) {
		if ( putsList == null ) {
			// Create a list to return
			putsList = new ArrayList<>();
		}
		// Search for matching parcel ID
		for ( HydroBase_ParcelUseTSStructureToParcel puts : this.parcelUseTSListForWDID ) {
			if ( puts._parcel_id == parcelId ) {
				putsList.add(puts);
			}
		}
		return putsList;
	}

	/**
	 * Get the list of data objects at the index.
	 */
	public List<HydroBase_ParcelUseTSStructureToParcel> getData () {
		return this.parcelUseTSListForWDID;
	}
	
	/**
	 * Get the identifier for the list.
	 */
	public int getId () {
		return this.id;
	}

	/**
	 * Get the water district for the list.
	 */
	public int getWd () {
		return this.wd;
	}

}