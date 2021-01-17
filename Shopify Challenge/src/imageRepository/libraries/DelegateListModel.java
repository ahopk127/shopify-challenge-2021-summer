/**
 * Copyright (C) 2018 Adrien Hopkins
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package imageRepository.libraries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;

/**
 * A list model that delegates to a list.
 * <p>
 * It is recommended to use the delegate methods in DelegateListModel instead of
 * the delegated list's methods because the delegate methods handle updating the
 * list.
 * </p>
 * 
 * @author Adrien Hopkins
 */
public final class DelegateListModel<E> extends AbstractListModel<E>
		implements List<E> {
	/**
	 */
	private static final long serialVersionUID = 8985494428224810045L;
	
	/**
	 * The list that this model is a delegate to.
	 * 
	 */
	private final List<E> delegate;
	
	/**
	 * Creates an empty {@code DelegateListModel}.
	 * 
	 */
	public DelegateListModel() {
		this(new ArrayList<>());
	}
	
	/**
	 * Creates the {@code DelegateListModel}.
	 * 
	 * @param delegate list to delegate
	 */
	public DelegateListModel(final List<E> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean add(final E element) {
		final int index = this.delegate.size();
		final boolean success = this.delegate.add(element);
		this.fireIntervalAdded(this, index, index);
		return success;
	}
	
	@Override
	public void add(final int index, final E element) {
		this.delegate.add(index, element);
		this.fireIntervalAdded(this, index, index);
	}
	
	@Override
	public boolean addAll(final Collection<? extends E> c) {
		boolean changed = false;
		for (final E e : c) {
			if (this.add(e)) {
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	public boolean addAll(final int index, final Collection<? extends E> c) {
		for (final E e : c) {
			this.add(index, e);
		}
		return !c.isEmpty(); // Since this is a list, it will always change if c
									// has elements.
	}
	
	@Override
	public void clear() {
		final int oldSize = this.delegate.size();
		this.delegate.clear();
		if (oldSize >= 1) {
			this.fireIntervalRemoved(this, 0, oldSize - 1);
		}
	}
	
	@Override
	public boolean contains(final Object elem) {
		return this.delegate.contains(elem);
	}
	
	@Override
	public boolean containsAll(final Collection<?> c) {
		for (final Object e : c) {
			if (!c.contains(e))
				return false;
		}
		return true;
	}
	
	@Override
	public E get(final int index) {
		return this.delegate.get(index);
	}
	
	@Override
	public E getElementAt(final int index) {
		return this.delegate.get(index);
	}
	
	@Override
	public int getSize() {
		return this.delegate.size();
	}
	
	@Override
	public int indexOf(final Object elem) {
		return this.delegate.indexOf(elem);
	}
	
	@Override
	public boolean isEmpty() {
		return this.delegate.isEmpty();
	}
	
	@Override
	public Iterator<E> iterator() {
		return this.delegate.iterator();
	}
	
	@Override
	public int lastIndexOf(final Object elem) {
		return this.delegate.lastIndexOf(elem);
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return this.delegate.listIterator();
	}
	
	@Override
	public ListIterator<E> listIterator(final int index) {
		return this.delegate.listIterator(index);
	}
	
	@Override
	public E remove(final int index) {
		final E returnValue = this.delegate.get(index);
		this.delegate.remove(index);
		this.fireIntervalRemoved(this, index, index);
		return returnValue;
	}
	
	@Override
	public boolean remove(final Object o) {
		final int index = this.delegate.indexOf(o);
		final boolean returnValue = this.delegate.remove(o);
		this.fireIntervalRemoved(this, index, index);
		return returnValue;
	}
	
	@Override
	public boolean removeAll(final Collection<?> c) {
		boolean changed = false;
		for (final Object e : c) {
			if (this.remove(e)) {
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	public boolean retainAll(final Collection<?> c) {
		final int oldSize = this.size();
		final boolean returnValue = this.delegate.retainAll(c);
		this.fireIntervalRemoved(this, this.size(), oldSize - 1);
		return returnValue;
	}
	
	@Override
	public E set(final int index, final E element) {
		final E returnValue = this.delegate.get(index);
		this.delegate.set(index, element);
		this.fireContentsChanged(this, index, index);
		return returnValue;
	}
	
	@Override
	public int size() {
		return this.delegate.size();
	}
	
	@Override
	public List<E> subList(final int fromIndex, final int toIndex) {
		return this.delegate.subList(fromIndex, toIndex);
	}
	
	@Override
	public Object[] toArray() {
		return this.delegate.toArray();
	}
	
	@Override
	public <T> T[] toArray(final T[] a) {
		return this.delegate.toArray(a);
	}
	
	@Override
	public String toString() {
		return this.delegate.toString();
	}
}
