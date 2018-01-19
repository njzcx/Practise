package com.demo.util;

public class MyList<T> {
	
	private T[] element;
	
	private int size;
	
	public MyList() {
		this(10);
	}
	
	public MyList(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("初始化容量有误" + initialCapacity);
		this.element = (T[]) new Object[initialCapacity];
	}
	
	public void ensureCapacity(int minCapacity) {
		int oldCapacity = this.element.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity*3)/2 + 1;
			if (minCapacity > newCapacity)
				newCapacity = minCapacity;
			T[] oldElement = this.element;
			this.element = (T[]) new Object[newCapacity];
			System.arraycopy(oldElement, 0, this.element, 0, size);
		}
	}
	
	private void checkRange(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException("Size:" + size + ", Index:" + index);
	}
	
	public void add(T o) {
		ensureCapacity(size + 1);
		this.element[size++] = o;
	}
	
	public void add(int index, T o) {
		checkRange(index);
		ensureCapacity(size + 1);
		int moveNum = size - index;
		System.arraycopy(this.element, index, this.element, index + 1, moveNum);
		this.element[index] = o;
		size++;
	}
	
	public T remove(int index) {
		checkRange(index);
		T oldValue = this.element[index];
		int moveNum = size - index - 1;
		if (moveNum > 0)
			System.arraycopy(this.element, index + 1, this.element, index, moveNum);
		this.element[--size] = null;
		return oldValue;
	}
	
	public void set(int index, T o) {
		checkRange(index);
		this.element[index] = o;
	}
	
	public void clear() {
		for (int i = 0; i < size; i++)
			this.element[i] = null;
		size = 0;
	}
	
	public int[] mergeArr(int arr1[], int arr2[]) {
		int[] arr = new int[arr1.length + arr2.length];
		int pos = 0;
		int pos1 = 0;
		int pos2 = 0;
		while (pos1 < arr1.length && pos2 < arr2.length) {
			if (arr1[pos1] >= arr2[pos2]) {
				arr[pos++] = arr2[pos2++];
			} else {
				arr[pos++] = arr1[pos1++];
			}
		}
		while (pos1 < arr1.length)
			arr[pos++] = arr1[pos1++];
		while (pos2 < arr2.length)
			arr[pos++] = arr2[pos2++];
		return arr;
	}
	
	public static void main(String[] args) {
		String x = "01234567890123456789012345678901234";
//		String x = "一二三四五六七八九十一二三四五六七4";
		x.getBytes();
		int[] arr1 = {1,3,5,7,9};
		int[] arr2 = {2,4,6,8,10};
		int[] arr3 = {11,13,15,17,19};
		int[] arr = new MyList<Integer>().mergeArr(arr1, arr3);
		for (int i : arr)
			System.out.print(i + ",");
	}
}
