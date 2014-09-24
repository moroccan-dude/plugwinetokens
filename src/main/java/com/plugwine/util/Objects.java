package com.plugwine.util;

import org.apache.commons.lang.ArrayUtils;

/**
 * This class allows you to test the equality of two objects in a null-safe matter.
 */
public final class Objects {

    /**
     * This class can't be instantiate it.
     */
    private Objects() {
    }

    /**
     * Return <code>true</code> if both objects are the same using the equals method.
     * This method is null-safe.
     * 
     * @param object1 first object.
     * 
     * @param object2 second objet.
     * 
     * @return <code>true</code> if both objects are the same.
     */
    public static boolean equals(Object object1, Object object2) {
        if (object1 != null) {
            return object1.equals(object2);
        } else if (object2 == null) {
            return true;
        }
        return false;
    }
    
    /** 
     * compare 2 arrays of objects
     * @param array1
     * @param array2
     * @return
     */
    public static boolean arrayEquals(Object[] array1, Object[] array2) 
    {
        return ArrayUtils.isEquals(array1, array2);
    }
}
