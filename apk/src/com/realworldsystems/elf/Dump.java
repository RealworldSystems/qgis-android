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

public class Dump {
    
    public static void dumpHeader(ELFHeader h) {
	System.out.print("Identifier: ");
	byte[] identifier = h.getIdentifier();
	for(int k = 0; k < identifier.length; k++) {
	    if(k > 0) System.out.print(", ");
	    System.out.print(identifier[k]);
	}
	System.out.println("");
	
	System.out.printf("ELF Header Size: %d\n", h.getEHSize());
	System.out.printf("-- Type: %s\n", h.getTypeName());
	System.out.printf("-- Machine: %s\n", h.getMachineName());
	System.out.printf("-- Class: %s\n", h.getELFClassName());
	System.out.printf("-- Data Encoding: %s\n", h.getDataEncodingName());
	System.out.printf("-- Entry Point: 0x%x\n", h.getEntry());
	System.out.printf("-- Program Header Table Offset: 0x%x\n", h.getPHOff());
	System.out.printf("-- Program Header Entries: 0x%x\n", h.getPHNum());
	System.out.printf("-- Segment Header Table Offset: 0x%x\n", h.getSHOff());
	System.out.printf("-- Segment Header Entries: 0x%x\n", h.getSHNum());
	
    }
    
    public static void dumpProgramHeader(ProgramHeader h) {
	ProgramHeader.Entry[] entries = h.getEntries();
	for(int i = 0; i < entries.length; i++) {
	    ProgramHeader.Entry e = entries[i];
	    System.out.printf("Program Header Entry %d\n", i);
	    System.out.printf("-- Entry Type: %s\n", e.getTypeName());
	    System.out.printf("-- Offset: 0x%x\n", e.getOffset());
	    System.out.printf("-- Virtual Address: 0x%x\n", e.getVAddr());
	    System.out.printf("-- File Size: %d Bytes\n", e.getFileSZ());
	    System.out.printf("-- Memory Size: %d Bytes\n", e.getMemSZ());
	    System.out.printf("-- Flags: 0x%x\n", e.getFileSZ());
	    
	    if(e.getType() == ProgramHeader.Entry.Type.DYNAMIC) {
		System.out.println("-- Dynamic Header Information:");
		Dynamic.DT[] dtList = e.getDynamic().getDTList();
		for(int j = 0; j < dtList.length; j++) {
		    int tag = dtList[j].getTag();
		    System.out.printf("--== Dynamic Tag: %s\n", dtList[j].getTagName());
		    if(tag == Dynamic.DT.Tag.NEEDED ||
		       tag == Dynamic.DT.Tag.RPATH ||
		       tag == Dynamic.DT.Tag.SONAME) {
			System.out.printf("--== Dynamic Name: %s\n", dtList[j].getName());
		    }
		}
	    }

	}
    }

    public static void dumpDependencies(String[] list) {
	    System.out.println("All library dependencies:");
	for(int i = 0; i < list.length; i++) {
	    System.out.printf("-- %s\n", list[i]);
	}
    }

    public static void main(String[] args) throws ReaderException {
	
	for(int i = 0; i < args.length; i++) {
	    Reader r = new Reader(args[i]);
	    
	    dumpHeader(r.getELFHeader());
	    dumpProgramHeader(r.getProgramHeader());
	    dumpDependencies(r.getLibraryDependencies());
	}
	
    }
    
}