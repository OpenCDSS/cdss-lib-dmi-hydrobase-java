package DWR.DMI.HydroBaseDMI;

/**
Class to store SFUTG2 diversion coding class information for diversion records.
This information is needed to understand the intricacies of HydroBase diversion coding.
*/
public class HydroBase_SFUTG2
{

/**
Source in SFUTG2.
*/
private String __source = "";

/**
From.Account in SFUTG2.
*/
private String __from = "";

/**
Use in SFUTG2.
*/
private String __use = "";

/**
Type in SFUTG2.
*/
private String __type = "";

/**
Group in SFUTG2.
*/
private String __group = "";

/**
To (2) in SFUTG2.
*/
private String __to = "";

/**
Constructor to parse the identifier string "S:X F:X U:X T:X G:X 2:X" into its parts, where X can be blank.
@param identifier SFUTG2 identifier string to parse
*/
public HydroBase_SFUTG2 ( String identifier )
{
    // Parse the identifier into its parts.
    // Assume that there is one space between parts
    // TODO SAM 2013-01-07 Make more robust if the formatting is not as expected
    String [] parts = identifier.split(" ");
    // Loop through and look for the known prefix.  Ignore empty strings are found.
    if ( parts != null ) {
        for ( int i = 0; i < parts.length; i++ ) {
            String partTrimmed = parts[i].trim();
            if ( partTrimmed.toUpperCase().startsWith("S:") && (partTrimmed.length() >2) ) {
                __source = partTrimmed.substring(2).trim();
            }
            else if ( partTrimmed.toUpperCase().startsWith("F:") && (partTrimmed.length() >2) ) {
                __from = partTrimmed.substring(2).trim();
            }
            else if ( partTrimmed.toUpperCase().startsWith("U:") && (partTrimmed.length() >2) ) {
                __use = partTrimmed.substring(2).trim();
            }
            else if ( partTrimmed.toUpperCase().startsWith("T:") && (partTrimmed.length() >2) ) {
                __type = partTrimmed.substring(2).trim();
            }
            else if ( partTrimmed.toUpperCase().startsWith("G:") && (partTrimmed.length() >2) ) {
                __group = partTrimmed.substring(2).trim();
            }
            else if ( partTrimmed.toUpperCase().startsWith("2:") && (partTrimmed.length() >2) ) {
                __to = partTrimmed.substring(2).trim();
            }
        }
    }
}

/**
Constructor where individual coding parts are set.
@param source "source" from SFUTG2
@param from "from" from SFUTG2
@param use "use" from SFUTG2
@param type "type" from SFUTG2
@param group "group" from SFUTG2
@param to "to" from SFUTG2
*/
public HydroBase_SFUTG2 ( String source, String from, String use, String type, String group, String to )
{
    __source = source;
    __from = from;
    __use = use;
    __type = type;
    __group = group;
    __to = to;
}

/**
Return the "from" for SFUTG2.
*/
public String getFrom ()
{
    return __from;
}

/**
Return the "group" for SFUTG2.
*/
public String getGroup ()
{
    return __group;
}

/**
Return the "source" for SFUTG2.
*/
public String getSource ()
{
    return __source;
}

/**
Return the "to" for SFUTG2.
*/
public String getTo ()
{
    return __to;
}

/**
Return the "type" for SFUTG2.
*/
public String getType ()
{
    return __type;
}

/**
Return the "use" for SFUTG2.
*/
public String getUse ()
{
    return __use;
}

}