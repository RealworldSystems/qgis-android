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

public class ELFHeader {

    /**
     * Definitions for the header type
     */
    public class Type {

	/**
	 * No file type
	 */
	public static final int NONE = 0;
	
	/**
	 * Relocatable file type
	 */
	public static final int REL  = 1;
	
	/**
	 * Executable file type
	 */
	public static final int EXEC = 2;
	
	/**
	 * Shared object file
	 */
	public static final int DYN  = 3;
	
	/**
	 * Core file type
	 */
	public static final int CORE = 4;
    }

    /**
     * Definitions for the header machine
     */
    public class Machine {
	
	/**
	 * Not a known machine
	 */
	public static final int NONE = 0;
	
	/**
	 * A M32 machine
	 */
	public static final int M32 = 1;
	
	/**
	 * A SPARC machine
	 */
	public static final int SPARC = 2;
	
	/**
	 * An Intel 386 machine
	 */
	public static final int X386 = 3;
	
	/**
	 * A Motorola 68000 machine
	 */
	public static final int M68000 = 4;

	/**
	 * A Motorola 88000 machine
	 */
	public static final int M88000 = 5;
	
	/**
	 * A Intel 80860 machine
	 */
	public static final int I80860 = 7;

	/**
	 * A MIPS RS3000 machine
	 */
	public static final int MIPS_RS3000 = 8;


	/**
	 * An AMD x86_64 machine
	 */
	public static final int X86_64 = 62;

	/**
	 * An ARM machine
	 */
	public static final int ARM = 40;
    }

    /**
     * Definitions for the ELF class type
     */
    public class ELFClass {
	/**
	 * No class type available
	 */
	public static final byte NONE = (byte)0;
	
	/**
	 * 32-bit class type
	 */
	public static final byte ELFCLASS32 = (byte)1;


	/**
	 * 64-bit class type
	 */
	public static final byte ELFCLASS64 = (byte)2;
    }
    

    /**
     * Definitions for the ELF class type
     */
    public class Encoding {
	/**
	 * No class type available
	 */
	public static final byte NONE = (byte)0;
	
	/**
	 * Least significant byte
	 */
	public static final byte ELFDATA2LSB = (byte)1;


	/**
	 * Most significant byte
	 */
	public static final byte ELFDATA2MSB = (byte)2;
    }
    
    byte[] identifier;
    int type;
    int machine;
    int version;
    int entry;
    int phoff;
    int shoff;
    int flags;
    int ehsize;
    int phentsize;
    int phnum;
    int shentsize;
    int shnum;
    int shstrndx;


    public byte[] getIdentifier() { return identifier; }
    public int getType() {return type;}
    public int getMachine() { return machine; }
    public int getVersion() { return version; }
    public int getEntry() { return entry; }
    public int getPHOff() { return phoff; }
    public int getSHOff() { return shoff; }
    public int getFlags() { return flags; }
    public int getEHSize() { return ehsize; }
    public int getPHEntSize() { return phentsize; }
    public int getPHNum() { return phnum; }
    public int getSHEntSize() { return shentsize; }
    public int getSHNum() { return shnum; }
    public int getSHStrNdx() { return shstrndx; }

    
    public String getTypeName() {
	switch(type) {
	case Type.NONE: return "Unspecified";
	case Type.REL: return "Relocatable object";
	case Type.DYN: return "Dynamic/Shared object";
	case Type.EXEC: return "Executable object";
	case Type.CORE: return "Core object";
	default: return String.format("(Unknown [0x%x])\n", type);
	}
    }

    public byte getELFClass() {
	return identifier[4];
    }

    public byte getDataEncoding() {
	return identifier[5];
    }
    
    public byte getIdentifierVersion() {
	return identifier[6];
    }

    public byte getPadding() {
	return identifier[7];
    }

    public String getMachineName() {
	switch(machine) {
	case Machine.NONE: return "Unspecified";
	case Machine.M32: return "M32";
	case Machine.SPARC: return "SPARC";
	case Machine.X386: return "Intel 386";
	case Machine.M68000: return "Motorola M68000";
	case Machine.M88000: return "Motorola M88000";
	case Machine.I80860: return "Intel 80860";
	case Machine.MIPS_RS3000: return "MIPS RS3000";
	case Machine.ARM: return "ARM";
	case Machine.X86_64: return "AMD x86_64";
	default: return String.format("(Unknown [0x%x])\n", machine);
	}
    }

    public String getELFClassName() {
	switch(getELFClass()) {
	case ELFClass.NONE: return "Unspecified";
	case ELFClass.ELFCLASS32: return "32-bits";
	case ELFClass.ELFCLASS64: return "64-bits";
	default: return String.format("(Unknown [0x%x])\n", getELFClass());
	}
    }

    public String getDataEncodingName() {
	switch(getDataEncoding()) {
	case Encoding.NONE: return "Unspecified";
	case Encoding.ELFDATA2LSB: return "Least-significant";
	case Encoding.ELFDATA2MSB: return "Most-significant";
	default: return String.format("(Unknown [0x%x])\n", getDataEncoding());
	}
    }
}
