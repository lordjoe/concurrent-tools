package com.lordjoe.logging;

import java.util.*;

/**
 * com.lordjoe.logging.ForwardingSet
 *    this is the wrapper class in Bloch's book
 * @author Steve Lewis
 * @date Aug 10, 2008
 */
public class ForwardingSet<T> implements Set<T>
{
    public static ForwardingSet[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = ForwardingSet.class;

    private final Set<T> m_Set;

    public ForwardingSet(Set<T> pSet)
    {
        m_Set = pSet;
    }

    /**
     * Returns the number of elements in this set (its cardinality).  If this
     * set contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this set (its cardinality)
     */
    public int size()
    {
        return m_Set.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty()
    {
        return m_Set.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this set
     * contains an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     * @throws ClassCastException   if the type of the specified element
     *                              is incompatible with this set (optional)
     * @throws NullPointerException if the specified element is null and this
     *                              set does not permit null elements (optional)
     */
    public boolean contains(Object o)
    {
        return m_Set.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this set.  The elements are
     * returned in no particular order (unless this set is an instance of some
     * class that provides a guarantee).
     *
     * @return an iterator over the elements in this set
     */
    public Iterator<T> iterator()
    {
        return m_Set.iterator();
    }

    /**
     * Returns an array containing all of the elements in this set.
     * If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the
     * elements in the same order.
     * <p/>
     * <p>The returned array will be "safe" in that no references to it
     * are maintained by this set.  (In other words, this method must
     * allocate a new array even if this set is backed by an array).
     * The caller is thus free to modify the returned array.
     * <p/>
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all the elements in this set
     */
    public Object[] toArray()
    {
        return m_Set.toArray();
    }

    /**
     * Returns an array containing all of the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     * <p/>
     * <p>If this set fits in the specified array with room to spare
     * (i.e., the array has more elements than this set), the element in
     * the array immediately following the end of the set is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * set <i>only</i> if the caller knows that this set does not contain
     * any null elements.)
     * <p/>
     * <p>If this set makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements
     * in the same order.
     * <p/>
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     * <p/>
     * <p>Suppose <tt>x</tt> is a set known to contain only strings.
     * The following code can be used to dump the set into a newly allocated
     * array of <tt>String</tt>:
     * <p/>
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * <p/>
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.
     *
     * @param a the array into which the elements of this set are to be
     *          stored, if it is big enough; otherwise, a new array of the same
     *          runtime type is allocated for this purpose.
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException  if the runtime type of the specified array
     *                              is not a supertype of the runtime type of every element in this
     *                              set
     * @throws NullPointerException if the specified array is null
     */
    public <T> T[] toArray(T[] a)
    {
        return m_Set.toArray(a);
    }

    /**
     * Adds the specified element to this set if it is not already present
     * (optional operation).  More formally, adds the specified element
     * <tt>e</tt> to this set if the set contains no element <tt>e2</tt>
     * such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.  In combination with the
     * restriction on constructors, this ensures that sets never contain
     * duplicate elements.
     * <p/>
     * <p>The stipulation above does not imply that sets must accept all
     * elements; sets may refuse to add any particular element, including
     * <tt>null</tt>, and throw an exception, as described in the
     * specification for {@link java.util.Collection#add Collection.add}.
     * Individual set implementations should clearly document any
     * restrictions on the elements that they may contain.
     *
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this set
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements
     * @throws IllegalArgumentException      if some property of the specified element
     *                                       prevents it from being added to this set
     */
    public boolean add(T e)
    {
        return m_Set.add(e);
    }

    /**
     * Removes the specified element from this set if it is present
     * (optional operation).  More formally, removes an element <tt>e</tt>
     * such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this set contains such an element.  Returns <tt>true</tt> if this set
     * contained the element (or equivalently, if this set changed as a
     * result of the call).  (This set will not contain the element once the
     * call returns.)
     *
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if this set contained the specified element
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this set (optional)
     * @throws NullPointerException          if the specified element is null and this
     *                                       set does not permit null elements (optional)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this set
     */
    public boolean remove(Object o)
    {
        return m_Set.remove(o);
    }

    /**
     * Returns <tt>true</tt> if this set contains all of the elements of the
     * specified collection.  If the specified collection is also a set, this
     * method returns <tt>true</tt> if it is a <i>subset</i> of this set.
     *
     * @param c collection to be checked for containment in this set
     * @return <tt>true</tt> if this set contains all of the elements of the
     *         specified collection
     * @throws ClassCastException   if the types of one or more elements
     *                              in the specified collection are incompatible with this
     *                              set (optional)
     * @throws NullPointerException if the specified collection contains one
     *                              or more null elements and this set does not permit null
     *                              elements (optional), or if the specified collection is null
     * @see #contains(Object)
     */
    public boolean containsAll(Collection<?> c)
    {
        return m_Set.containsAll(c);
    }

    /**
     * Adds all of the elements in the specified collection to this set if
     * they're not already present (optional operation).  If the specified
     * collection is also a set, the <tt>addAll</tt> operation effectively
     * modifies this set so that its value is the <i>union</i> of the two
     * sets.  The behavior of this operation is undefined if the specified
     * collection is modified while the operation is in progress.
     *
     * @param c collection containing elements to be added to this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of the
     *                                       specified collection prevents it from being added to this set
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this set does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this set
     * @see #add(Object)
     */
    public boolean addAll(Collection<? extends T> c)
    {
        return m_Set.addAll(c);
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection (optional operation).  In other words, removes
     * from this set all of its elements that are not contained in the
     * specified collection.  If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * <i>intersection</i> of the two sets.
     *
     * @param c collection containing elements to be retained in this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection (optional)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements (optional),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     */
    public boolean retainAll(Collection<?> c)
    {
        return m_Set.retainAll(c);
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     *
     * @param c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *                                       is not supported by this set
     * @throws ClassCastException            if the class of an element of this set
     *                                       is incompatible with the specified collection (optional)
     * @throws NullPointerException          if this set contains a null element and the
     *                                       specified collection does not permit null elements (optional),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c)
    {
        return m_Set.removeAll(c);
    }

    /**
     * Removes all of the elements from this set (optional operation).
     * The set will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> method
     *                                       is not supported by this set
     */
    public void clear()
    {
        m_Set.clear();

    }

    // Implementation fromAbstract Set
    /*
    public boolean equals(Object o) {
	if (o == this)
	    return true;

	if (!(o instanceof Set))
	    return false;
	Collection c = (Collection) o;
	if (c.size() != size())
	    return false;
        try {
            return containsAll(c);
        } catch (ClassCastException unused)   {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }
    */


//    public boolean equals(Object obj)
//    {
//        return m_Set.equals(obj);
//
//    }
//
//    public int hashCode()
//    {
//        return m_Set.hashCode();
//
//    }

    public boolean equals(Object o)
    {
        if (o == this)
            return true;

        if (!(o instanceof Set))
            return false;
        Collection c = (Collection) o;
        if (c.size() != size())
            return false;
        try {
            return containsAll(c);
        }
        catch (ClassCastException unused) {
            return false;
        }
        catch (NullPointerException unused) {
            return false;
        }
    }

    public int hashCode()
    {
        int h = 0;
        Iterator<T> i = iterator();
        while (i.hasNext()) {
            T obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }


    public String toString()
    {
        return m_Set.toString();

    }


    public static final String[] ORIGINAL_VALUES = {"fee", "fie", "foe", "fum"};

    public static void main(String[] args)
    {
        Set foo = new HashSet<String>(Arrays.asList(ORIGINAL_VALUES));
        Set bar = new HashSet<String>(Arrays.asList(ORIGINAL_VALUES));
        ForwardingSet<String> f1 = new ForwardingSet<String>(foo);
        ForwardingSet<String> f2 = new ForwardingSet<String>(bar);

        boolean isEqual = false;
        isEqual = f1.equals(foo);
        isEqual = f2.equals(foo);
        isEqual = foo.equals(f1);
         isEqual = f2.equals(f1);
        isEqual = f1.equals(f2);

        Set<Set<String>> holder = new HashSet();
        holder.add(f1);
        holder.add(f2);

        boolean isContained = false;
        isContained = holder.contains(f1);
        isContained = holder.contains(f2);
        
        bar.add("schwartz");

        isContained = holder.contains(f1);
        isContained = holder.contains(f2);

        isEqual = f1.equals(foo);
        isEqual = f2.equals(foo);
        isEqual = foo.equals(f1);
         isEqual = f2.equals(f1);
        isEqual = f1.equals(f2);


    }

}
