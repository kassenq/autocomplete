import java.util.*;

/**
 * 
 * Using a sorted array of Term objects, this implementation uses binary search
 * to find the top term(s).
 * 
 * @author Austin Lu, adapted from Kevin Wayne
 * @author Jeff Forbes
 * @author Owen Astrachan in Fall 2018, revised API
 */
public class BinarySearchAutocomplete implements Autocompletor {

	private Term[] myTerms;
	private int mySize;

	/**
	 * Given arrays of words and weights, initialize myTerms to a corresponding
	 * array of Terms sorted lexicographically.
	 * 
	 * This constructor is written for you, but you may make modifications to
	 * it.
	 * 
	 * @param terms
	 *            - A list of words to form terms from
	 * @param weights
	 *            - A corresponding list of weights, such that terms[i] has
	 *            weight[i].
	 * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
	 *         a Term with word terms[i] and weight weights[i].
	 * @throws a
	 *             NullPointerException if either argument passed in is null
	 */
	public BinarySearchAutocomplete(String[] terms, double[] weights) {
		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}
		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}

		myTerms = new Term[terms.length];

		for (int i = 0; i < terms.length; i++) {
			myTerms[i] = new Term(terms[i], weights[i]);
		}

		Arrays.sort(myTerms);
//		initialize(terms,weights);
	}

	/**
	 * Uses binary search to find the index of the first Term in the passed in
	 * array which is considered equivalent by a comparator to the given key.
	 * This method should not call comparator.compare() more than 1+log n times,
	 * where n is the size of a.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The first index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int firstIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
//		int index = BinarySearchLibrary.firstIndex(Arrays.asList(a), key, comparator);
//		return index;
		int low= -1;
		int high = a.length-1;
		if (a.length==0) return -1;

		while (low + 1 != high) {
			int mid = (low + high) /2 ;
			int cmp = comparator.compare(a[mid], key);
			// System.out.println(mid + " " + low + " "+ high + " " + cmp);
			// System.out.println(cmp);
			if (cmp < 0) low = mid;
			else  high = mid;
			// System.out.println(mid + " " + low + " "+ high + " " + cmp);

		}

		//	System.out.println(low + " "+ high);
		if (comparator.compare(a[high], key)==0){
			//System.out.println("A");
			return high;
		}
		return -1;
		// TODO: Implement firstIndexOf
	}

	/**
	 * The same as firstIndexOf, but instead finding the index of the last Term.
	 * 
	 * @param a
	 *            - The array of Terms being searched
	 * @param key
	 *            - The key being searched for.
	 * @param comparator
	 *            - A comparator, used to determine equivalency between the
	 *            values in a and the key.
	 * @return The last index i for which comparator considers a[i] and key as
	 *         being equal. If no such index exists, return -1 instead.
	 */
	public static int lastIndexOf(Term[] a, Term key, Comparator<Term> comparator) {
//		int index = BinarySearchLibrary.lastIndex(Arrays.asList(a), key, comparator);
//		return index;
		int low= 0;
		int high = a.length;
		if (a.length==0) return -1;

		while (low + 1 != high) {
			int mid = (low + high) /2 ;
			int cmp = comparator.compare(a[mid], key);
			// System.out.println(mid + " " + low + " "+ high + " " + cmp);
			// System.out.println(cmp);
			if (cmp <= 0) low = mid;
			else  high = mid;
			//System.out.println(mid + " " + low + " "+ high + " " + cmp);

		}
		/*while (low + 1 != high) {
		 int mid = (low + high )/2;
		 int cmp = comparator.compare(a[mid], key);
			if (cmp<0) high = mid;
			else low = mid;
			 System.out.println(mid + " " + low + " "+ high + " " + cmp);
		}
		 */
		if (low>a.length-1 || low < 0)return -1;
		if (comparator.compare(a[low], key)==0) {return low;}
		return -1;
	}

	/**
	 * Required by the Autocompletor interface. Returns an array containing the
	 * k words in myTerms with the largest weight which match the given prefix,
	 * in descending weight order. If less than k words exist matching the given
	 * prefix (including if no words exist), then the array instead contains all
	 * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
	 * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
	 * 2) should return {"air"}
	 * 
	 * @param prefix
	 *            - A prefix which all returned words must start with
	 * @param k
	 *            - The (maximum) number of words to be returned
	 * @return An array of the k words with the largest weights among all words
	 *         starting with prefix, in descending weight order. If less than k
	 *         such words exist, return an array containing all those words If
	 *         no such words exist, reutrn an empty array
	 * @throws a
	 *             NullPointerException if prefix is null
	 */
	
	@Override
	public List<Term> topMatches(String prefix, int k) {
		if (k<0) {
			throw new NullPointerException("Illegal value of k:"+k);
		}
		if (k==0) {
			return new ArrayList<>();
		}
//		Comparator<Term> preCompare = new Term.PrefixOrder(prefix.length());
		Term.PrefixOrder comp = new Term.PrefixOrder(prefix.length());
		Term dummy = new Term(prefix,0);
		
//		ArrayList<Term> list = new ArrayList<>();
//		return new ArrayList<>();
			
		   int first = firstIndexOf(myTerms, dummy, comp);
		   int last = lastIndexOf(myTerms, dummy, comp);
		   
		   if (first < 0 || last < 0) { // checks if there are no matched strings found
		      return new ArrayList<Term>();
		   }
//		   
//		   for (int i = first; i <= last; i++) {
//		      list.add(myTerms[i]);
//		   }
//		   
//		   ArrayList<Term> list2 = new ArrayList<>();
//		   int l = Math.min(last-first+1, k);
//		     Comparator<Term> revCompare = new Term.ReverseWeightOrder();
//		      Collections.sort(list, revCompare); //sorts the list to a reverse weighted order
//		   for (int i = 0; i < l; i++) {
//		      list2.add(list.get(i)); //creates a new list
//		   }
//			return list2; //returns the list
			PriorityQueue<Term> pq = 
					new PriorityQueue<Term>(10, new Term.WeightOrder());
			for (int i = first; i <= last; i++) {

				if (pq.size() < k) {
					pq.add(myTerms[i]);
				} else if (pq.peek().getWeight() < myTerms[i].getWeight()) {
					pq.remove();
					pq.add(myTerms[i]);
				}
			}
			int numResults = Math.min(k, pq.size());
			LinkedList<Term> ret = new LinkedList<>();
			for (int i = 0; i < numResults; i++) {
				ret.addFirst(pq.remove());
			}
			return ret;
	}

	@Override
	public void initialize(String[] terms, double[] weights) {
		myTerms = new Term[terms.length];
		
		for (int i = 0; i < terms.length; i++) {
			myTerms[i] = new Term(terms[i], weights[i]);
		}
		
		Arrays.sort(myTerms);
	}
	
	@Override
	public int sizeInBytes() {
		if (mySize == 0) {
			
			for(Term t : myTerms) {
			    mySize += BYTES_PER_DOUBLE + 
			    		  BYTES_PER_CHAR*t.getWord().length();	
			}
		}
		return mySize;
	}
}
