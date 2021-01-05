package com.wilcotech.kanjihelper.kanjihelper.utilities.Algorithms;

/**
 * Created by Wilson
 * on Mon, 21/12/2020.
 */

import java.util.*;

/**
 * This data structure supports the following operations in constant time
 *
 * insert(x): only if not already exists
 * remove(x): only if presents
 * search(x):
 * getRandom(): gets an element randomly from the remaining set of elements.
 *
 * Note that random picking of elements is never repeated on same element util
 * all other elements are randomly picked. Then, the random picking is repeated
 */
public class DecreasingRandom<T> {

    //a resizable array that contains the elems
   private ArrayList<T> arr;

    //keys -> arrays elements
    //values -> array element's corresponding indexes
    private HashMap<T, Integer> hash;

    //this is used to determine the insertion point of elements in the array
    //it divides the array into 2 segments: first seg. for elements that has been picked randomly
    //it is the index of the end of the first segment, the contains elems that have not been picked randomly
    private int endOfPickingSegment;


    public DecreasingRandom()
    {
        arr = new ArrayList<>();
        hash = new HashMap<>();
        endOfPickingSegment = -1;
    }

    public void addAll(T...  seed){
        for(T t: seed){
            add(t);
        }

    }
    public void addAll(List<T>  seed){
        for(T t: seed){
            add(t);
        }

    }

    private void add(T newElem)
    {
        //return if element already exists
        if (hash.get(newElem) != null)
            return;

        //put element at the end of the array
        int newIndex = arr.size();
        arr.add(newElem);

        //store element in the hash with its index
        hash.put(newElem, newIndex);

        //got the first element in the second segment
        endOfPickingSegment++;

        //swap the elem at endOfPickingSegment now with the added elem
        if(endOfPickingSegment != newIndex){//necessary when the array is empty
            T elementToSwap = arr.get(endOfPickingSegment);
            Collections.swap(arr,endOfPickingSegment,newIndex);
            hash.put(elementToSwap,newIndex);
            hash.put(newElem,endOfPickingSegment);
        }

    }

    //this removes elem from the array if present
    public void remove(T elem) {
        // Check if element is present
        Integer index = hash.get(elem);
        if (index == null)
            return;

        // If present, then remove element from hash
        hash.remove(elem, index);

        // swap elem with the last one for constant removal
        int size = arr.size();
        T last = arr.get(size - 1);
        Collections.swap(arr, index, size - 1);

        // Remove last element
        T removedElem = arr.remove(size - 1);

        // Update hash table for new index of last element
        hash.put(last, index);

        //update endOfPickingSegment
        if (endOfPickingSegment <= arr.size())
            endOfPickingSegment--;

    }

    // Returns a random elem that has not been returned before
   public T getRandom()
    {
        if(arr.isEmpty())
            return null;

        if(endOfPickingSegment == -1){
            endOfPickingSegment = arr.size()-1;
        }

        // Find a random index from 0 to size - 1
        int index = endOfPickingSegment;
        if(endOfPickingSegment >0){
            Random rand = new Random();  // Choose a different seed
            index = rand.nextInt(endOfPickingSegment+1);
        }

        // get element at randomly picked index
        T randomElem = arr.get(index);

        //swap the elem at the random index with the element at the end of the first segment
        if(endOfPickingSegment != index){
            Collections.swap(arr,endOfPickingSegment,index);
        }
        //adjust the endOfPickingSegment
        endOfPickingSegment--;

        return randomElem;
    }

    // returns the index of an element if it exists else -1
   public int search(T elem)
    {
        return hash.getOrDefault(elem,-1);
    }
}
