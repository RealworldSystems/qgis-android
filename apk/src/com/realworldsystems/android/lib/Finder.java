/*
 *  This file is part of the Realworld Android Library Finder.
 *
 *  Realworld Android Library Finder is free software: you can 
 *  redistribute it and/or modify  it under the terms of the 
 *  GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Realworld Android Library Finder  is distributed in the hope that 
 *  it will be useful, but WITHOUT ANY WARRANTY; without even the 
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Realworld Android Library Finder.
 *  If not, see <http://www.gnu.org/licenses/>.
 */

package com.realworldsystems.android.lib;

import com.realworldsystems.elf.Reader;
import com.realworldsystems.elf.ReaderException;
import java.util.ArrayList;
import java.io.File;

/**
 * Aids in finding all the dependencies of a given library in a given path
 *
 * @author Sjoerd van Leent
 */
public class Finder {
    
    /**
     * The path to use for finding dependending libraries
     */
    protected String path;
    
    /**
     * The reader which is used to parse the ELF object files
     */
    private Reader reader;
    
    /**
     * The string containing the path to the elf object
     */
    protected String elfPath;

    private static ArrayList<String> knownSystemLibraries;

    static {
	knownSystemLibraries = new ArrayList<String>();
	knownSystemLibraries.add("libc.so");
	knownSystemLibraries.add("libm.so");
	knownSystemLibraries.add("libdl.so");
	knownSystemLibraries.add("liblog.so");
	knownSystemLibraries.add("libz.so");
    }

    /**
     * An empty protected finder constructor for overloading classes
     */
    protected Finder() {};


    /**
     * Constructs a new finder object
     *
     * @param path Use this path to find dependending libraries (example: /data/data/.../lib)
     * @param elfObject the path pointing to the ELF object file
     *
     * @throws FinderException if the path or the elf object can not be found
     */
    public Finder(String path, String elfObject) throws FinderException {
	try {
	    if(path == null) { throw new NullPointerException("Path is null"); }

	    if(path.substring(path.length() - 1).equals("/")) {
		this.path = path;
	    } else {
		this.path = String.format("%s%c", path, File.separatorChar);
	    }
	    
	    this.reader = new Reader(elfObject);
	    this.elfPath = elfObject;
	} catch (Exception ex) {
	    throw new FinderException("Could not instantiate due to an exception", ex);
	}
    }
    
    String getELFPath() { return elfPath; }

    public String[] getOrderedDependencies() throws FinderException {
	return this.getOrderedDependencies(null);
    }
    
    /**
     * Gets all dependencies in the right order
     *
     * If a cyclical dependency is discovered, the method with throw an exception
     */
    String[] getOrderedDependencies(Finder[] parents) throws FinderException {
	String[] currentDeps = null;
	Finder[] newParents = null;
	if(parents != null) {
	    newParents = new Finder[parents.length + 1];
	    for(int k = 0; k < parents.length; k++) {
		newParents[k] = parents[k];
	    }
	    newParents[parents.length] = this;
	} else {
	    newParents = new Finder[1];
	    newParents[0] = this;
	}
	try {
	    currentDeps = this.reader.getLibraryDependencies();
	} catch (ReaderException ex) {
	    throw new FinderException("Could not read ELF Object", ex);
	}

	ArrayList<String> newDeps = new ArrayList<String>();
	
	for(int i = 0; i < currentDeps.length; i++) {
	    String currentDep = currentDeps[i];
	    String depFullName = String.format("%s%s", path, currentDep);

	    // If the current dependency is one of the parents, the process will fail
	    if(parents != null) {
		for(int j = 0; j < parents.length; j++) {
		    Finder parent = parents[j];
		    if(parent.getELFPath().equals(depFullName)) {
			throw new FinderException("This dependency is cyclical, aborting");
		    }
		}
	    }
	    
	    // If the current dependency is already available in the newDeps list, it
	    // can be skipped.
	    
	    if(!newDeps.contains(currentDep)) {
		// Figure out the dependencies of this library, if available
		
		if(new File(depFullName).exists()) {
		    Finder f = new Finder(path, depFullName);
		    String[] dependencies = f.getOrderedDependencies(newParents);
		    for(int k = 0; k < dependencies.length; k++) {
			if(!newDeps.contains(dependencies[k])) {
			    newDeps.add(dependencies[k]);
			}
		    }
		    if(!newDeps.contains(currentDep)) { newDeps.add(currentDep); }
		} else {
		    if(!knownSystemLibraries.contains(currentDep)) {
			System.out.printf("Assume system library: %s\n", currentDep);
		    }
		}
	    }
	}
	
	String[] array = new String[newDeps.size()];
	return newDeps.toArray(array);
    }
}