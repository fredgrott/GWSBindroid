package com.github.shareme.gwsbindroid.library.utils;

/**
 * Allows an alternative definition of equality to be defined.
 * 
 * @param <T>
 *          the type being compared.
 */
public interface EqualityComparer<T> {
  boolean equals(T obj1, T obj2);
}
