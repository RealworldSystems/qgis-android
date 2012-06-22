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
 * An instance of this object in a Reader instance represents the program header
 *
 * @author Sjoerd van Leent
 */
public class ProgramHeader {
 
    /**
     * The program header contains entries in an array
     */
    public static class Entry {
	Entry() {};

	/**
	 * The type of the entry
	 */
	int type;


	/**
	 * The offset of the entry
	 */
	int offset;

	/**
	 * The virtual address of the entry
	 */
	int vaddr;

	/**
	 * The program address of the entry
	 */
	int paddr;

	/**
	 * The file size of the entry
	 */
	int filesz;

	/**
	 * The memory size of the entry
	 */
	int memsz;

	/**
	 * The flags of the entry
	 */
	int flags;

	/**
	 * The alignment of the entry
	 */
	int align;


	/**
	 * The dynamic table if present of the entry
	 */
	Dynamic dynamic = null;

	/**
	 * Gets the type of the entry
	 *
	 * @return The type of the entry
	 */
	public int getType() { return type; }

	/**
	 * Gets the offset of the entry
	 *
	 * @return The offset of the entry
	 */
	public int getOffset() { return offset; }

	/**
	 * Gets the virtual address of the entry
	 *
	 * @return The virtual address of the entry
	 */
	public int getVAddr() { return vaddr; }

	/**
	 * Gets the program address of the entry
	 *
	 * @return The program address of the entry
	 */
	public int getPAddr() { return paddr; }

	/**
	 * Gets the file size of the entry
	 *
	 * @return The file size of the entry
	 */
	public int getFileSZ() { return filesz; }

	/**
	 * Gets the memory size of the entry
	 *
	 * @return The memory size of the entry
	 */
	public int getMemSZ() { return memsz; }

	/**
	 * Gets the flags of the entry
	 *
	 * @return The flags of the entry
	 */
	public int getFlags() { return flags; }

	/**
	 * Gets the alignment of the entry
	 *
	 * @return The alignment of the entry
	 */
	public int getAlign() { return align; }


	/**
	 * Gets the dynamic table of the entry
	 *
	 * If the specified program header is of type Type.DYNAMIC, a
	 * dynamic table is present. This table contains entries to
	 * carry out the dynamic linking and loading.
	 *
	 * @return The dynamic table of the entry or null
	 */
	public Dynamic getDynamic() { return dynamic; }

	/**
	 * Known types of the program header's entry are defined here
	 */
	public static class Type {
	    private Type() {};
	    
	    /**
	     * This header is a NULL header
	     */
	    public static final int NULL = 0;

	    /**
	     * This header is a LOAD header
	     * 
	     * contains a loadable segment
	     */
	    public static final int LOAD = 1;


	    /**
	     * This header is a DYNAMIC header 
	     *
	     * contains a dynamic table
	     */
	    public static final int DYNAMIC = 2;

	    /**
	     * This header is an INTERP header
	     * 
	     * contains a path name  to an interpreter, if available
	     */
	    public static final int INTERP = 3;


	    /**
	     * This header is a NOTE header
	     * 
	     * contains a pointer into a NOTE segment
	     */
	    public static final int NOTE = 4;

	    /**
	     * This is a reserved header
	     */
	    public static final int SHLIB = 5;

	    /**
	     * Speficies the location of the program header table itself
	     */
	    public static final int PHDR = 6;
	}
	
	public String getTypeName() {
	    switch(type) {
	    case Type.NULL: return "NULL";
	    case Type.LOAD: return "LOAD";
	    case Type.DYNAMIC: return "DYNAMIC";
	    case Type.INTERP: return "INTERP";
	    case Type.NOTE: return "NOTE";
	    case Type.SHLIB: return "SHLIB";
	    case Type.PHDR: return "PHDR";
	    default: return String.format("(Unknown [0x%x])", type);
	    }
	}


    }

    private ProgramHeader.Entry[] entries;

    ProgramHeader(ProgramHeader.Entry[] entries) {
	this.entries = entries;
    }

    /**
     * Gets all available entries
     *
     * @return Available entries
     */
    public ProgramHeader.Entry[] getEntries() { return entries; }
    
    /**
     * Gets the size of available program header entries
     *
     * @return An integer representing the size of available entries
     */
    public int size() { return entries.length; }
    
    /**
     * Gets a specfic program header entry
     *
     * @param int Index into the program header entry table
     * @return The program header
     */
    public ProgramHeader.Entry getEntry(int ndx) { return entries[ndx]; }
}