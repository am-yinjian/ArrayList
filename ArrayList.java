// Written by Maggie Jiang, Jian0886
public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] lst;                        //Initalizes a list variable to be used within the class
    private int size;                       //Keep tracks of elemnts in array
    private boolean isSorted;
    private int maxlength;                  //Keep tracks of maximum array length

    public ArrayList(){
        lst  = (T[]) new Comparable[2];                 //Initalize array list length to 2
        size = 0;
        isSorted = true;                                //set true since list is empty
        maxlength = 2;
    }

    public void grow(){
        T[] newlst = (T[]) new Comparable[maxlength*2];                 //Doubles the array length
        for (int i = 0; i < size; i++){                                 //Copy over the orginal elemnts into new larger list
            newlst[i] = lst[i];
        }
        maxlength = maxlength * 2;                                      //Set max length into new length
        lst = newlst;
    }

    public boolean add(T element) {
        if (element == null){
            return false;
        }
        if (size + 1 > maxlength) {                                 //Check if when adding new elemnt will be larger than maxlength
            grow();                                                 //Grows list if list is full
        }
        lst[size] = element;
        size++;                                                     //Updates the size
        isSorted = false;                                           //set to false first since we don't know if list is sorted anymore but if called isSorted it will automatically update using tempSort
        return true;
    }

    public boolean add(int index, T element) {
        if (element == null || index < 0 || index > size) {         //check if element is null or not in bounds
            return false;
        }else{
            for (int i = size - 1; i >= index; i--) {               //Makes space for the new element and moves all the elements over
                lst[i + 1] = lst[i];
            }
            lst[index] = element;
            isSorted = false;                                       //set to false first since we don't know if list is sorted anymore but if called isSorted it will automatically update using tempSort
            size++;                                                 //Updates the size
            return true;
        }
    }


    public void clear() {
        for (int i = 0; i < size; i++){                         //Sets all elements in the list to be null
            lst[i] = null;
        }
        size = 0;                                               //list is empty so size will be zero
        isSorted = false;                                       //set to false first since we don't know if list is sorted anymore but if called isSorted it will automatically update using tempSort
    }


    public T get(int index) {
        if(index < 0 || index >= size){                         //Checks if the index is in bounds
            return null;
        }else{
            return lst[index];
        }
    }

    public int indexOf(T element) {
        if (isSorted){                                              //If link is sorted returns the index of element early
            for (int i = 0; i< size; i++){
                if (lst[i].compareTo(element) == 0){                //Since list is sorted there is no null or empty space between list
                        return i;
                }else if (lst[i].compareTo(element) < 0){           //Return -1 early if element not found when element is smaller than current
                    return -1;                                      //Since if there is no element equal to the element before than then element does not exist
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (lst[i] == null) {                         //Checks if element is null if yes then skip and move on to next element
                    continue;
                }
                if (lst[i].equals(element)) {
                    return i;
                }
            }
        }
        return -1;                                               //return -1 if element not found
    }

    public boolean isEmpty() {
        if(size == 0){                                           //if size is zero there isn't any elements in the list
            return true;
        }else{
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void sort() {
        if (!isSorted){                                      //Does not sort if list is already sorted
            boolean swapped = true;                             //Checks if elements has been swapped so it doesn't duplicate swap again
            for (int i =0; i < size - 1 && swapped; i++){           //Checks all elements within the size and if it has been swapped or not
                swapped = false;
                for (int j = 1; j < size - i; j ++){
                    if (lst[j - 1].compareTo(lst[j]) > 0){              //If the current elemnt is larger than previous element
                        T temp = lst[j];                                   //swaps so its ascending
                        lst[j] = lst[j-1];
                        lst[j-1] = temp;
                        swapped = true;
                    }
                }
            }
            isSorted = true;                                     //Updates isSorted
        }
    }
    public boolean tempSort() {                                  //Helper function to check if list is sorted or not
        if (!isSorted){
            boolean swapped = true;
            for (int i = 0; i < size - 1 && swapped; i++){
                swapped = false;
                for (int j = 1; j < size - i; j ++){
                    if (lst[j-1].compareTo(lst[j]) > 0){                                         //return false if they detect it's not in ascending order
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public T remove(int index) {
        T removed;                                      //Keep track of which elemnt is removed
        if (index >= size || index < 0){                //Checks bounds
            return null;
        }else{
            removed= lst[index];
            lst[index] = null;
            for (int i = index + 1; i < size; i++) {                //Shifts all the elements over if removed
                lst[i - 1] = lst[i];
            }
            lst[size-1] = null;
            size--;                                                 //Updates size since list is smaller
            isSorted = tempSort();
            return removed;                             //Returns the element that was removed
        }
    }

    public void removeDuplicates() {
        for(int i = 0; i< size; i++){               //Checks the whole list
            for (int j = i+1; j< size; j++){                //compares i elements to j elements or the rest of the list
                if (lst[i].compareTo(lst[j]) == 0){
                    remove(j);
                    j--;                        //Since remove moves everything over one we need to move j back one as well
                }
            }
        }
    }

    public void reverse() {
        T prev;                                            //Creates local variable to keep track of previous element to be swapped around
        for (int i = 0; i < size / 2; i++) {            //Swaps elements
            prev = lst[i];
            lst[i] = lst[size - i - 1];
            lst[size - i - 1] = prev;
        }
    }

    public void exclusiveOr(List<T> otherList) {
        if (otherList == null || otherList.size() == 0) {                         //Checks if list is null or is empty
            return;
        }
        ArrayList<T> other = (ArrayList<T>) otherList;                                 //Set otherlist type to arraylist and simplify to other
        this.sort();                                                                    //Cleans up both list by sorting and removing any duplicates
        this.removeDuplicates();
        other.sort();
        other.removeDuplicates();

        ArrayList<T> result = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < this.size && j < other.size) {                   //Iterates over the same size parts of each list
            int compare = this.lst[i].compareTo(other.lst[j]);
            if (compare < 0) {                                  //if and else if checks if element from either list is different from each other
                result.add(this.lst[i]);                        //adds if it is the case
                i++;
            } else if (compare > 0) {
                result.add(other.lst[j]);
                j++;
            } else {                                            //If both list has the same element skips this
                i++;
                j++;
            }
        }

        while (i < this.size) {                             //If this list has leftover items after adding over the parts from other list
            result.add(this.lst[i]);                    //Copies over to results list
            i++;
        }

        while (j < other.size) {                        //If the other list has leftover items after adding over the parts from the this list
            result.add(other.lst[j]);                      //Copies over to results list
            j++;
        }

        this.lst = (T[]) result.lst;                    //updates our current list to the result of the exclusive or method
        this.size = result.size;                        //updates the size of the new list
        this.isSorted = tempSort();                     //updates isSorted by using helper function
        this.removeDuplicates();                        //remove any accidental duplicates
    }

    public T getMin() {
        if (size == 0){                                                  //Check if list is empty
            return null;
        }
        T min = lst[0];
        if (isSorted){                                              //Utlizes isSorted and since is in ascending order the first element is the smallest
            return min;
        }else{
            for (int i = 1; i< size; i++){                          //If not sorted checks every element and compares it to the first
                if (min.compareTo(lst[i])> 0){
                    min = lst[i];                                      //If element smaller than the previous minimum then updates
                }
            }
            return min;
        }
    }


    public T getMax() {
        if (size == 0){
            return null;
        }
        T max = lst[0];
        if (isSorted){
            max = lst[size -1];                                     //Utlizes isSorted and since is in ascending order the last element is the biggest
            return max;
        }else{
            for (int i = 1; i< size; i++){                      //If not sorted checks every element and comapres it to the first
                if (max.compareTo(lst[i])< 0){
                    max = lst[i];                               //Updates max if an element is bigger than previous max
                }
            }
            return max;
        }
    }

    public String toString(){
        String str = " ";
        for (int i =0; i< size; i++){           //Iterates through whole list and adds to new string
            str += lst[i];
            str += "\n";                        //Adds new line after each element
        }
        return str;
    }
    public boolean isSorted() {
        isSorted = tempSort();              //Uses helper function to check if it is sorted or not
        return isSorted;
    }
}
