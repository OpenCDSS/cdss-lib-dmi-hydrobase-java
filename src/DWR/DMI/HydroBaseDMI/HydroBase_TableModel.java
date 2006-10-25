// ----------------------------------------------------------------------------
// HydroBase_TableModel - The default table model from which HydroBase
//	TableModels should be built.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-23	J. Thomas Sapienza, RTi	Initial version.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import java.util.Date;
import java.util.Vector;

import RTi.DMI.DMIUtil;

import RTi.Util.GUI.JWorksheet_AbstractRowTableModel;

import RTi.Util.Time.DateTime;

/**
This class is an abstract table model defining methods that should be
present in HydroBase table models.
*/
public abstract class HydroBase_TableModel 
extends JWorksheet_AbstractRowTableModel {

/**
Returns an array containing the widths (in number of characters) that the 
fields in the table should be sized to.
@return an integer array containing the widths for each field.
*/
public abstract int[] getColumnWidths();

}
