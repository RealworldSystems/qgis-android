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

public class ProgramHeader {
 
    public static class Entry {
	Entry() {};

	public static class Dynamic {
	    Dynamic() {};
	    
	    public static class DT {
	    
		int tag;
		int val;
		int ptr;
		String name = null;
		
		public int getTag() { return tag; }
		public int getVal() { return val; }
		public int getPtr() { return ptr; }
		public String getName() { return name; }
		
		public static class Tag {
		    public static final int	NULL	 = 0;
		    public static final int	NEEDED	 = 1;
		    public static final int	PLTRELSZ = 2;
		    public static final int	PLTGOT	 = 3;
		    public static final int	HASH	 = 4;
		    public static final int	STRTAB	 = 5;
		    public static final int	SYMTAB	 = 6;
		    public static final int	RELA	 = 7;
		    public static final int	RELASZ	 = 8;
		    public static final int	RELAENT	 = 9;
		    public static final int	STRSZ	 = 10;
		    public static final int	SYMENT	 = 11;
		    public static final int	INIT	 = 12;
		    public static final int	FINI	 = 13;
		    public static final int	SONAME	 = 14;
		    public static final int	RPATH	 = 15;
		    public static final int	SYMBOLIC = 16;
		    public static final int	REL	 = 17;
		    public static final int	RELSZ	 = 18;
		    public static final int	RELENT	 = 19;
		    public static final int	PLTREL	 = 20;
		    public static final int	DEBUG	 = 21;
		    public static final int	TEXTREL	 = 22;
		    public static final int	JMPREL	 = 23;
		}

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
	    
	    public DT[] getDTList() { return dtList; }
	    
	}

	
	int type;
	int offset;
	int vaddr;
	int paddr;
	int filesz;
	int memsz;
	int flags;
	int align;
	Dynamic dynamic = null;

	public int getType() { return type; }
	public int getOffset() { return offset; }
	public int getVAddr() { return vaddr; }
	public int getPAddr() { return paddr; }
	public int getFileSZ() { return filesz; }
	public int getMemSZ() { return memsz; }
	public int getFlags() { return flags; }
	public int getAlign() { return align; }
	public Dynamic getDynamic() { return dynamic; }

	public static class Type {
	    private Type() {};
	    
	    public static final int NULL = 0;
	    public static final int LOAD = 1;
	    public static final int DYNAMIC = 2;
	    public static final int INTERP = 3;
	    public static final int NOTE = 4;
	    public static final int SHLIB = 5;
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

    ProgramHeader.Entry[] getEntries() { return entries; }
   
}