/**
*
*/
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
* Provides sorting algorithms.
*
*/
public class ListSorter<T> {

	/**
	* Sorts a list using a Comparator object.
	*
	* @param list
	* @param comparator
	* @return
	*/
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	* Sorts a list using a Comparator object.
	*
	* @param list
	* @param comparator
	* @return
	*/
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	* Sorts a list using a Comparator object.
	*
	* Returns a list that might be new.
	*
	* @param list
	* @param comparator
	* @return
	*/
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		int size = list.size();
		if (size <= 1) {
			return list;
		}
		List<T> left = new ArrayList<T>();
		List<T> right = new ArrayList<T>();

		for (int i = 0; i< size; i++) {
			if ((i%2) == 0) {
				left.add (list.get(i));
			}
			else {
				right.add (list.get(i));
			}
		}
		//recursively sort both sublist
		left = mergeSort (left, comparator);
		right = mergeSort (right, comparator);

		return merge (left, right, comparator);

	}

	public List<T> merge (List<T> left, List<T> right, Comparator<T> comparator) {
		List<T> result = new ArrayList<T>();

		int leftFirst = 0;
		int rightFirst = 0;

		while (leftFirst < left.size()  && rightFirst < right.size()) {
			// System.out.println ("leftF: " + leftFirst);
			// System.out.println ("rightF: " + rightFirst);
			int compare = comparator.compare(left.get(leftFirst), right.get(rightFirst));
			if (compare <= 0) {
				result.add (left.get(leftFirst));
				leftFirst++;
			}
			else {
				result.add (right.get(rightFirst));
				rightFirst++;
			}
		}
		while (leftFirst < left.size() ) {
			result.add (left.get(leftFirst));
			leftFirst++;
		}
		while (rightFirst < right.size()) {
			result.add (right.get(rightFirst));
			rightFirst++;
		}
		return result;

	}

	/**
	* Sorts a list using a Comparator object.
	*
	* @param list
	* @param comparator
	* @return
	*/
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> queue = new PriorityQueue<T>();
		for (T element: list) {
			queue.offer (element);
		}
		list.clear();
		while (queue.size() > 0) {
			list.add (queue.poll());
		}
	}


	/**
	* Returns the largest `k` elements in `list` in ascending order.
	*
	* @param k
	* @param list
	* @param comparator
	* @return
	* @return
	*/
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		for (T element: list) {
			if (heap.size() < k) {
				heap.offer (element);
			}
			else {
				if (comparator.compare (element, heap.peek()) > 0) {
					heap.poll();
					heap.offer (element);
				}
			}
		}
		List<T> top = new ArrayList<T>();
		while (!heap.isEmpty()) {
			top.add(heap.poll());
		}
		return top;
	}


	/**
	* @param args
	*/
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
