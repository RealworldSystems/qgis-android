/*
 *  This file is part of the Realworld Systems ELF Reader Library (ELFRL).
 *
 *  ELFRL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ELFRL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ELFRL.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.realworldsystems.elf;

/**
 * The Dynamic class can be available in a ProgramHeader.Entry
 *
 * An instance of this object represents a list of tags which contain
 * pointers to certain entries or values to indices.
 *
 * @author Sjoerd van Leent
 */
public class Dynamic {
    Dynamic() {};
	    
    /**
     * A Dynamic Table entry
     *
     * Represents a dynamic table entry using tags and values. String
     * resolution is done for NEEDED, SONAME and RPATH tags.
     *
     * @author Sjoerd van Leent
     */
    public static class DT {
	DT() {};
	    
	/**
	 * The tag number
	 */
	int tag;

	/**
	 * The value (if available)
	 */
	int val = 0;

	/**
	 * The pointer (if available)
	 */
	int ptr = 0;
	
	/**
	 * The name is set only if the value points into the string table
	 */
	String name = null;
		
	/**
	 * Gets the tag, which can be one of the tags listed in Tag.
	 *
	 * @return The tag itself
	 */
	public int getTag() { return tag; }
	

	/**
	 * Gets the value of the tag.
	 *
	 * Only available if the selected tag has a value, otherwise, the value
	 * will be 0.
	 *
	 * @return The value of the tag
	 */
	public int getVal() { return val; }
	
	/**
	 * Gets the pointer of the tag.
	 *
	 * Only available if the selected tag has a pointer, otherwise, the value
	 * will be 0.
	 *
	 * @return The value of the pointer
	 */
	public int getPtr() { return ptr; }
	
	/**
	 * Gets the name of the value pointed to by the current tag. Only
	 * available if this is of kind NEEDED, SONAME or RPATH.
	 */
	public String getName() { return name; }
		
	/**
	 * Contains known tag values.
	 */
	public static class Tag {
	    private Tag() {};
	    
	    /**
	     * A null tag. Has neither a value nor a pointer.
	     */
	    public static final int	NULL	 = 0;


	    /**
	     * A needed tag. Has a value.
	     *
	     * The needed tag is used to find necessary dependent
	     * dynamic libraries (shared objects.)
	     */
	    public static final int	NEEDED	 = 1;

	    /**
	     * A pltrelsz tag. Has a value.
	     */
	    public static final int	PLTRELSZ = 2;


	    /**
	     * A pltgot tag. Has a pointer.
	     */
	    public static final int	PLTGOT	 = 3;

	    /**
	     * A hash tag. Has a pointer.
	     */
	    public static final int	HASH	 = 4;

	    /**
	     * A strtab tag. Has a pointer.
	     *
	     * Contains a pointer to the ELF Object's string table
	     */
	    public static final int	STRTAB	 = 5;

	    /**
	     * A symtab tag. Has a pointer.
	     *
	     * Contains a pointer to the ELF Object's symbol table
	     */
	    public static final int	SYMTAB	 = 6;

	    /**
	     * A rela tag. Has a pointer.
	     */
	    public static final int	RELA	 = 7;

	    /**
	     * A relasz tag. Has a value.
	     */
	    public static final int	RELASZ	 = 8;

	    /**
	     * A relaent tag. Has a value.
	     */
	    public static final int	RELAENT	 = 9;

	    /**
	     * A strsz tag. Has a value.
	     */
	    public static final int	STRSZ	 = 10;

	    /**
	     * A syment tag. Has a value.
	     */
	    public static final int	SYMENT	 = 11;


	    /**
	     * An init tag. Has a pointer.
	     *
	     * Contains a pointer to additional initialization code.
	     */
	    public static final int	INIT	 = 12;

	    /**
	     * A fini tag. Has a pointer.
	     *
	     * Contains a pointer to additional finalization code.
	     */
	    public static final int	FINI	 = 13;

	    /**
	     * A soname tag. Has a value.
	     *
	     * Contains a value into the string table containing the name
	     * of this dynamic library (only available if this is a shared
	     * object.)
	     */
	    public static final int	SONAME	 = 14;


	    /**
	     * An rpath tag. Has a value.
	     *
	     * Contains a value into the string table containing a range
	     * of paths (delimited by a colon), where the NEEDED dynamic
	     * libraries should be found, excluding system default
	     * locations.
	     */
	    public static final int	RPATH	 = 15;

	    /**
	     * A symbolic tag. Has neither a value nor a pointer.
	     *
	     * This is a boolean value changing the dynamic linker
	     * functionality to start loading libraries from the
	     * perspective of the dynamic library itself.
	     */
	    public static final int	SYMBOLIC = 16;


	    /**
	     * A rel tag. Has a pointer.
	     */
	    public static final int	REL	 = 17;

	    /**
	     * A relsz tag. Has a value.
	     */
	    public static final int	RELSZ	 = 18;

	    /**
	     * A relent tag. Has a value.
	     */
	    public static final int	RELENT	 = 19;

	    /**
	     * A pltrel tag. Has a value.
	     */
	    public static final int	PLTREL	 = 20;

	    /**
	     * A debug tag. Has a pointer.
	     */
	    public static final int	DEBUG	 = 21;

	    /**
	     * A textrel tag. Has neither a value nor a pointer.
	     */
	    public static final int	TEXTREL	 = 22;

	    /**
	     * A jmprel tag. Has a pointer.
	     */
	    public static final int	JMPREL	 = 23;
	}
	

	/**
	 * Converts the tag to a string representing the tag
	 *
	 * @return A string representing the tag
	 */
	public String getTagName() {
	    switch(tag) {
	    case Tag.NULL : return "NULL";
	    case Tag.NEEDED : return "NEEDED";
	    case Tag.PLTRELSZ : return "PLTRELSZ";
	    case Tag.PLTGOT : return "PLTGOT";
	    case Tag.HASH : return "HASH";
	    case Tag.STRTAB : return "STRTAB";
	    case Tag.SYMTAB  : return "SYMTAB";
	    case Tag.RELA  : return "RELA";
	    case Tag.RELASZ  : return "RELASZ";
	    case Tag.RELAENT  : return "RELAENT";
	    case Tag.STRSZ  : return "STRSZ";
	    case Tag.SYMENT  : return "SYMENT";
	    case Tag.INIT  : return "INIT";
	    case Tag.FINI  : return "FINI";
	    case Tag.SONAME  : return "SONAME";
	    case Tag.RPATH  : return "RPATH";
	    case Tag.SYMBOLIC : return "SYMBOLIC";
	    case Tag.REL : return "REL";
	    case Tag.RELSZ  : return "RELSZ";
	    case Tag.RELENT  : return "RELENT";
	    case Tag.PLTREL  : return "PLTREL";
	    case Tag.DEBUG  : return "DEBUG";
	    case Tag.TEXTREL  : return "TEXTEL";
	    case Tag.JMPREL  : return "JMPREL";
	    default: return String.format("(Unknown [0x%x])", tag);
	    }		    
	}
    }
    
    DT[] dtList;

    /**
     * Gets the array of DT entries
     *
     * @return The array with DT entries set up.
     */
    public DT[] getDTList() { return dtList; }
    
    /**
     * Gets the size of the DT list
     *
     * @return An integer containing the size of the DT list
     */
    public int size() { return dtList.length; }
	    
    
    /**
     * Get an entry from the DT List
     *
     * @return A dynamic table tag
     */
    public DT getDT(int ndx) { return dtList[ndx]; }
}
