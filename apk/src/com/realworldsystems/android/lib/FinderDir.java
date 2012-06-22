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

import java.io.File;
import java.util.ArrayList;


/**
 * Aids in finding all the dependencies of a given library in a given path
 *
 * @author Sjoerd van Leent
 */
public class FinderDir extends Finder {

    /**
     * Constructs a new finder object
     *
     * @param path Use this path to find dependending libraries (example: /data/data/.../lib)
     *
     * @throws FinderException if the path or the elf object can not be found
     */
    public FinderDir(String path) throws FinderException {
	try {
	    if(path == null) { throw new NullPointerException("Path is null"); }

	    if(path.substring(path.length() - 1).equals("/")) {
		this.path = path;
	    } else {
		this.path = String.format("%s%c", path, File.separatorChar);
	    }
	    this.elfPath = path;
	} catch (Exception ex) {
	    throw new FinderException("Could not instantiate due to an exception", ex);
	}
    }

    /**
     * Gets all dependencies in the right order
     */
    public String[] getOrderedDependencies() throws FinderException {
	try {
	    String[] currentDeps = new File(path).list();
	    
	    ArrayList<String> newDeps = new ArrayList<String>();
		
	    for(int i = 0; i < currentDeps.length; i++) {
		String currentDep = currentDeps[i];
	    
		// If the current dependency is already available in the newDeps list, it
		// can be skipped.
	    
		if(!newDeps.contains(currentDep)) {
		    // Figure out the dependencies of this library, if available
		
		    String depFullName = String.format("%s%s", path, currentDep);
		    if(new File(depFullName).exists()) {
			Finder f = new Finder(path, depFullName);
			String[] dependencies = f.getOrderedDependencies();
			for(int k = 0; k < dependencies.length; k++) {
			    if(!newDeps.contains(dependencies[k])) {
				newDeps.add(dependencies[k]);
			    }
			}
			if(!newDeps.contains(currentDep)) { newDeps.add(currentDep); }
		    }
		}
	    }
	    String[] array = new String[newDeps.size()];
	    return newDeps.toArray(array);
	} catch (FinderException ex)  {
	    throw ex;
	} catch (Exception ex) {
	    throw new FinderException("Could not process Finder Directory", ex);
	}
	
    }

    /**
     * A test routine for command line scanning
     */
    public static void main(String[] args) throws FinderException {
	if(args.length != 2) {
	    System.err.println("Usage: Finder <Path>");
	} else {
	    String[] deps = new FinderDir(args[0]).getOrderedDependencies();
	    for(int i = 0; i < deps.length; i++) {
		System.out.printf("Dependency: %s\n", deps[i]);
	    }
	}
    }

}