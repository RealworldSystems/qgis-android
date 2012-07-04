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

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/** Reader class to be able to read headers from an ELF object
 *
 * @author Sjoerd van Leent
 */
public class Reader {
    
    /**
     * Contains the ELF object file
     */
    private File elfObject;

    
    /**
     * The ELF Header object
     *
     * Initially, this element is not set. If the ELF header is parsed through
     * getELFHeader(), it will be set. Further calls to getELFHeader() will return
     * this value.
     */
    private ELFHeader elfHeader = null;


    /**
     * The Program Header object
     *
     * Initially, this element is not set. If the program header is parsed through
     * getProgramHeader(), it will be set. Further calls to getProgramHeader() will return
     * this value.
     *
     */
    private ProgramHeader programHeader = null;


    /**
     * Initializes the object.
     *
     * @param elfObject initializes form this object coming from various ctor's
     */
    private void initialize(File elfObject) throws ReaderException {
	if(!elfObject.exists()) {
	    throw new ReaderException("The ELF object is not available");
	} else if(!elfObject.canRead() || !elfObject.isFile()) {
	    throw new ReaderException("The ELF object is not an accessible file");
	}

	this.elfObject = elfObject;
    }

    /**
     * Constructs a new Reader object which reads data from the
     * given ELF object.
     *
     * @param elfObject The file to the ELF object to be inspected.
     *
     * @throws ReaderException if the elfObject cannot be used
     */
    public Reader(File elfObject) throws ReaderException {
	initialize(elfObject);
    }

    /**
     * Constructs a new Reader object which reads data from the
     * given ELF object.
     *
     * @param elfPath The path to the ELF object to be inspected.
     *
     * @throws ReaderException if the elfPath cannot be used
     */
    public Reader(String elfPath) throws ReaderException {
	try {
	    initialize(new File(elfPath));
	} catch (NullPointerException ex) {
	    throw new ReaderException("The elfPath is null", ex);
	}
    }
    
    private int readShort(InputStream is) throws IOException {
	byte[] raw = new byte[2];
	is.read(raw, 0, raw.length);
	return ((int)(0x000000FF & raw[1]) << 8) | (int)(0x000000FF & raw[0]);
    }
    
    private int readInteger(InputStream is) throws IOException {
	byte[] raw = new byte[4];
	is.read(raw, 0, raw.length);	
	return ((int)(0x000000FF & raw[3]) << 24) | ((int)(0x000000FF & raw[2]) << 16) | 
	    ((int)(0x000000FF & raw[1]) << 8) | (int)(0x000000FF & raw[0]);
    }

    /**
     * Returns the ELFHeader object
     *
     * @return This will get the ELFHeader from the ELF Object
     */
    public ELFHeader getELFHeader() throws ReaderException {
	// Cache: Return cached elfHeader if present
	if(this.elfHeader != null) return this.elfHeader;
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(this.elfObject);
	    ELFHeader header = new ELFHeader();
	    
	    byte[] elfMagic = new byte[16];
	    fis.read(elfMagic, 0, 16);
	    if(elfMagic[0] != 0x7f ||
	       elfMagic[1] != 0x45 ||
	       elfMagic[2] != 0x4c ||
	       elfMagic[3] != 0x46) {
		throw new ReaderException("File not identified as valid ELF file");
	    }
	    
	    header.identifier = elfMagic;
	    
	    // Get the type information (byte offset 16 and 17)
	    header.type	     = readShort(fis);
	    
	    // Get the machine information (byte offset 18 and 19)
	    header.machine   = readShort(fis);
	    
	    // Get the version information (byte offset 20-23)
	    header.version   = readInteger(fis);
	    
	    // Get the entry point address (byte offset 24-27)
	    header.entry     = readInteger(fis);

	    // Get the header table file offset (byte offset 28-31)
	    header.phoff     = readInteger(fis);

	    // Get the section header table file offset (byte offset 32-35)
	    header.shoff     = readInteger(fis);
	    
	    // Get the flags (byte offset 36-39)
	    header.flags     = readInteger(fis);
	    
	    // Get the ELF header size (byte offset 40 and 41)
	    header.ehsize    = readShort(fis);
	    
	    // Get the size of each individual program header entry (byte offset 42 and 43)
	    header.phentsize = readShort(fis);
	    
	    // Get the count of program header entries (byte offset 44 and 45)
	    header.phnum     = readShort(fis);
	    
	    // Get the size of each individual section header entry (byte offset 46 and 47)
	    header.shentsize = readShort(fis);
	    
	    // Get the count of section header entries (byte offset 48 and 49)
	    header.shnum     = readShort(fis);
	    
	    // Get the index of the section header string table (byte offset 50 and 51)
	    header.shstrndx  = readShort(fis);

	    this.elfHeader = header;
	} catch(ReaderException rex) {
	    throw rex;  // Rethrow if this is already a ReaderException
	} catch (Exception ex) {
	    throw new ReaderException("Could not parse ELFHeader", ex);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (IOException ex) {
		    throw new Error(ex.getMessage());
		}
	    }
	}
	return this.elfHeader;
    }

    private Dynamic readDynamic(int offset, int size) throws Exception {
	    FileInputStream fis = new FileInputStream(elfObject);
	    fis.skip(offset);
	    
	    Dynamic.DT[] dtlist = new Dynamic.DT[size / 8];
	    

	    {
		int j = 0;

		for(int i = offset; i < (offset + size); i += 8) {
		    int tag = readInteger(fis);
		    int un = readInteger(fis);

		    Dynamic.DT dt = new Dynamic.DT();
		    dt.tag = tag;
		
		    if((dt.tag >= 3 && dt.tag <= 7) ||
		       (dt.tag >= 12 && dt.tag <= 13) ||
		       dt.tag == 17 || dt.tag == 21 || dt.tag == 23) {
			dt.ptr =  un;
		    } else if(!(dt.tag == 0 || dt.tag == 16 || 
				dt.tag == 22 || dt.tag > 23)) {
			dt.val = un;
		    }
		
		    dtlist[j] = dt;
		    j++;
		}
	    }
	    
	    fis.close();
	    
	    Dynamic d = new Dynamic();
	    d.dtList = dtlist;

	    // Read the STRTAB
	    
	    int strtabOffset = 0;
	    for(int i = 0; i < d.dtList.length; i++) { // Fin dstrtab
		if(d.dtList[i].getTag() == Dynamic.DT.Tag.STRTAB)
		    {
			strtabOffset = d.dtList[i].getPtr();
			break;
		    }
	    }

	    for(int i = 0; i < d.dtList.length; i++) {
		Dynamic.DT dt = d.dtList[i];
		int tag = dt.getTag();
		if(tag == Dynamic.DT.Tag.NEEDED ||
		   tag == Dynamic.DT.Tag.RPATH ||
		   tag == Dynamic.DT.Tag.SONAME) {
		    
		    FileInputStream fisStrTab = new FileInputStream(elfObject);
		    fisStrTab.skip(strtabOffset);
		    fisStrTab.skip(dt.getVal());
		    
		    ArrayList<Byte> byteList = new ArrayList<Byte>();
		    while(true) {
			int b = fisStrTab.read();
			
			if(b < 0) {
			    fisStrTab.close();
			    throw new ReaderException("Not enough bytes in strtab");
			}
			else if(b > 0) {
			    byteList.add((byte)b);
			}
			else {
			    byte[] array = new byte[byteList.size()];
			    for(int k = 0; k < array.length; k++) {
				array[k] = byteList.get(k);
			    }
			    dt.name = new String(array, "US-ASCII");
			    break;
			}
		    }

		    fisStrTab.close();
		}
	    }
	    return d;
    }

    private ProgramHeader.Entry getProgramHeaderEntry(int offset, int size) 
	throws ReaderException {
	if(size < 8) throw new ReaderException("Can't allocate Program Header Entry Size");
	
	ProgramHeader.Entry entry = new ProgramHeader.Entry();
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(elfObject);
	    fis.skip(offset);
	    
	    entry.type	 = readInteger(fis);
	    entry.offset = readInteger(fis);
	    entry.vaddr	 = readInteger(fis);
	    entry.paddr	 = readInteger(fis);
	    entry.filesz = readInteger(fis);
	    entry.memsz	 = readInteger(fis);
	    entry.flags	 = readInteger(fis);
	    entry.align	 = readInteger(fis);

	    if (entry.getType() == ProgramHeader.Entry.Type.DYNAMIC) {
		entry.dynamic = readDynamic(entry.getOffset(), entry.getFileSZ());
	    }
	
	} catch (Exception ex) {
	    throw new ReaderException("Could not parse program header entry", ex);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (IOException ex) {
		    throw new Error(ex.getMessage());
		}
	    }
	}

	return entry;
    }
    

    /**
     * Returns the ProgramHeader object
     *
     * @return This will get the ProgramHeader from the ELF Object
     */
    public ProgramHeader getProgramHeader() throws ReaderException {
	// Cache: If the program header is present, return it
	if(this.programHeader != null) return this.programHeader;
	
    
	ELFHeader	header = getELFHeader();	// Load if not present yet
	    
	ProgramHeader.Entry[] entries = 
	    new ProgramHeader.Entry[header.getPHNum()];
	    
	for(int k = 0; k < header.getPHNum(); k++) {
	    int size = header.getPHEntSize();
	    entries[k] = 
		getProgramHeaderEntry(header.getPHOff() + (k * size), size);
	}
	    
	this.programHeader = new ProgramHeader(entries);
	return this.programHeader;
    }

    /**
     * Gets all the library dependencies
     *
     * Looks in all DYNAMIC program header segments and returns the different
     * libraries where this object depents upon.
     */
    public String[] getLibraryDependencies() throws ReaderException {
	
	ArrayList<String>	list   = new ArrayList<String>();
	ProgramHeader		header = this.getProgramHeader();
	
	for(int i = 0; i < header.size(); i++) {
	    ProgramHeader.Entry entry = header.getEntry(i);
	    if(entry.type == ProgramHeader.Entry.Type.DYNAMIC) {
		for(int j = 0; j < entry.getDynamic().size(); j++) {
		    Dynamic.DT dt = entry.getDynamic().getDT(j);
		    if(dt.tag == Dynamic.DT.Tag.NEEDED) {
			list.add(dt.getName());
		    }
		}		
	    }
	}
	
	String[] array = new String[list.size()];
	return list.toArray(array);
    }
    
}