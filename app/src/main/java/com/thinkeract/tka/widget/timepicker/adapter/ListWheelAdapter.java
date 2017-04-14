package com.thinkeract.tka.widget.timepicker.adapter;


import java.util.List;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ListWheelAdapter<T> implements WheelAdapter {

	/** The default items length */
	public static final int DEFAULT_LENGTH = 4;

	// items
	public List<T> items;
	// length
	private int length;

	/**
	 * Constructor
	 * @param items the items
	 * @param length the max items length
	 */
	public ListWheelAdapter(List<T> items, int length) {
		this.items = items;
		this.length = length;
	}

	/**
	 * Contructor
	 * @param items the items
	 */
	public ListWheelAdapter(List<T> items) {
		this(items, DEFAULT_LENGTH);
	}

	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}
		return "";
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int indexOf(Object o){
		return items.indexOf(o);
	}

}
