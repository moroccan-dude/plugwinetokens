package com.plugwine.domain.holder;

import java.util.AbstractList;

import javax.xml.bind.annotation.*;

/**
 * ArrayList Wrapper (Decorator) Class to allow for XML Marshalling for a list of Holder elements
 * @See: JAXB always expects a @XmlRootElement annotation on the entity, it gets to marshal. This is mandatory and can not be skipped
 *
 * @param <T>
 */
@XmlRootElement (name = "list")
public class PlugwineList<T> extends AbstractList<T> {

	 private java.util.List<T> values;

	 public PlugwineList()
	 { /* mandated by the JAXB contract */
		 super();
	 }
	 
	 public PlugwineList(java.util.List<T> elements)
	 {
		 this.values = elements;
	 }
	 
     @XmlAnyElement(lax=true)
     public java.util.List<T> getValues() {
        return values;
     }
     
	 @Override
	 public T get(int index) {
		return values.get(index);
	 }

	 @Override
	 public int size() {
		return values.size();
	 }
}
