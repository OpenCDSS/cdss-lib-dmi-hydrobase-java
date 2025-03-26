// HydroBase_CropRef - Class to hold data from the HydroBase crop_ref table.

/* NoticeStart

CDSS HydroBase Database Java Library
CDSS HydroBase Database Java Library is a part of Colorado's Decision Support Systems (CDSS)
Copyright (C) 2018-2025 Colorado Department of Natural Resources

CDSS HydroBase Database Java Library is free software:  you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

CDSS HydroBase Database Java Library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

You should have received a copy of the GNU General Public License
    along with CDSS HydroBase Database Java Library.  If not, see <https://www.gnu.org/licenses/>.

NoticeEnd */

package DWR.DMI.HydroBaseDMI;

import RTi.DMI.DMIDataObject;
import RTi.DMI.DMIUtil;

/**
Class to store data from the HydroBase crop_ref table.
*/
public class HydroBase_CropRef
extends DMIDataObject {

protected String _crop_code = DMIUtil.MISSING_STRING;
protected String _crop_desc = DMIUtil.MISSING_STRING;

/**
Constructor.
*/
public HydroBase_CropRef() {
	super();
}

/**
Returns _crop_code
@return _crop_code
*/
public String getCrop_code() {
	return _crop_code;
}

/**
Returns _crop_desc
@return _crop_desc
*/
public String getCrop_desc() {
	return _crop_desc;
}

/**
Sets _crop_code
@param crop_code value to put into _crop_code
*/
public void setCrop_code(String crop_code) {
	_crop_code = crop_code;
}

/**
Sets _crop_desc
@param crop_desc value to put into _crop_desc
*/
public void setCrop_desc(String crop_desc) {
	_crop_desc = crop_desc;
}

/**
returns a string representation of this object
@return a string representation of this object
*/
public String toString() {
	return "HydroBase_CropRef {"		+ "\n" +
		"Crop_code: " + _crop_code + "\n" +
		"Crop_desc: " + _crop_desc + "\n}\n";
}

}