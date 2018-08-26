package agsml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A generic bag of properties used to store properties that apply to a specific
 * target (data object descriptions, signature properties collector, signed data
 * objects). The properties are organized by type (class).
 * 
 * @author Lus
 */
class PropertiesSet<T>
{
    private final Map<Class, Set<T>> properties;

    /**
     * Initializes the property bag with the given initial diferent property types.
     * @param initialNPropTypes the initial number of different property types.
     */
    public PropertiesSet(int initialNPropTypes)
    {
        this.properties = new HashMap<Class, Set<T>>(initialNPropTypes);
    }

    /**
     * Puts a property in the bag. The put operation doesn't allow repeated
     * property types. If a property of this type was previously added an exception
     * is thrown.
     *
     * @param prop the property
     *
     * @throws NullPointerException if {@code prop} is {@code null}
     * @throws PropertyTargetException if a property of this type was previously
     *                              added
     */
    public void put(T prop) throws Exception
    {
        if (null == prop)
            throw new NullPointerException("Property cannot be null");

        // If an entry for the property's type is already present it means that a
        // property of this type was previously added. Adding another property of
        // this type is not allowed.
        if (properties.containsKey(prop.getClass()))
            throw new Exception(String.format("A property of type %s was already added", prop.getClass().getSimpleName()));

        add(prop);
    }

    /**
     * Adds a property to the bag. The add operation allows multiple properties
     * of the same type but not repeated instances.
     * @param prop the property
     *
     * @throws NullPointerException if {@code prop} is {@code null}
     * @throws PropertyTargetException if the given property (instance)
     *                                      is already present in the bag
     */
    public void add(T prop) throws Exception
    {
        if (null == prop)
            throw new NullPointerException("Property cannot be null");

        Set<T> propsOfCurrType = properties.get(prop.getClass());
        if (null == propsOfCurrType)
        {
            // No properties of this type have been added.
            propsOfCurrType = new HashSet<T>(1);
            properties.put(prop.getClass(), propsOfCurrType);
        }
        // Repeated instances are not allowed.
        if (!propsOfCurrType.add(prop))
            throw new Exception("Property instance already present");
    }

    /**
     * Removes a property from the bag.
     *
     * @param prop the property to be removed
     *
     * @throws NullPointerException if the property is {@code null}
     * @throws IllegalStateException if the property is not present
     */
    public void remove(T prop)
    {
        if (null == prop)
            throw new NullPointerException("Property cannot be null");

        Set<T> propsOfCurrType = properties.get(prop.getClass());
        if (null == propsOfCurrType || !propsOfCurrType.remove(prop))
            throw new IllegalStateException("Property not present");

        if (propsOfCurrType.isEmpty())
            properties.remove(prop.getClass());
    }

    /**
     * Indicates whether the bag has any properties.
     * @return {@code true} if the bag has no properties
     */
    public boolean isEmpty()
    {
        // If any entry for any property type is present, then at least one
        // property was specified.
        return properties.isEmpty();
    }

    /**
     * Gets the properties in the bag.
     * @return un unmodifiable collection of properties
     */
    public Collection<T> getProperties()
    {
        if (properties.isEmpty())
            return Collections.emptyList();

        Collection<T> props = new ArrayList<T>(properties.size() + 3);
        for (Set<T> propsOfType : properties.values())
        {
            props.addAll(propsOfType);
        }
        return props;
    }
    
}