// ----------------------------------------------------------------------------
// HydroBase_TableModel - The default table model from which HydroBase
//	TableModels should be built.
// ----------------------------------------------------------------------------
// Copyright:   See the COPYRIGHT file
// ----------------------------------------------------------------------------
// History:
//
// 2005-06-23	J. Thomas Sapienza, RTi	Initial version.
// 2007-02-26	SAM, RTi		Clean up code based on Eclipse feedback.
// ----------------------------------------------------------------------------

package DWR.DMI.HydroBaseDMI;

import RTi.Util.GUI.JWorksheet_AbstractRowTableModel;

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
