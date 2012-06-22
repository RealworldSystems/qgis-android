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


/**
 * A finder exception is thrown whenever a failure occurs in a Finder object
 *
 * @author Sjoerd van Leent
 */
public class FinderException extends Exception
{
    
    /*package*/ FinderException(String message, Throwable cause)
    {
	super(message, cause);
    }
    
    /*package*/ FinderException(String message)
    {
	super(message);
    }
}