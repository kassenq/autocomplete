
/*************************************************************************
 * @author Kevin Wayne
 *
 * Description: A term and its weight.
 * 
 * @author Owen Astrachan, revised for Java 8-11, toString added
 * 
 *************************************************************************/

import java.util.Comparator;
import java.lang.String;

public class Term implements Comparable<Term> {

	private final String myWord;
	private final double myWeight;

	/**
	 * The constructor for the Term class. Should set the values of word and
	 * weight to the inputs, and throw the exceptions listed below
	 * 
	 * @param word
	 *            The word this term consists of
	 * @param weight
	 *            The weight of this word in the Autocomplete algorithm
	 * @throws NullPointerException
	 *             if word is null
	 * @throws IllegalArgumentException
	 *             if weight is negative
	 */
	public Term(String word, double weight) {
		// TODO: Complete Term constructor, throw exceptions
		if (word == null) {
			throw new NullPointerException();
		}
		else if (weight < 0) {
			throw new IllegalArgumentException("negative weight "+weight);
		} else {
			myWord = word;
			myWeight = weight;
		}
	}
	
	/**
	 * Default compare is by word, no weight involved
	 * @return this.getWord().compareTo(that.getWord())
	 */
	public int compareTo(Term that) {
		return myWord.compareTo(that.myWord);
	}

	/**
	 * Getter for Term's word
	 * @return this Term's word
	 */
	public String getWord() {
		return myWord;
	}

	/**
	 * Getter for Term's weight
	 * @return this Term's weight
	 */
	public double getWeight() {
		return myWeight;
	}

	/**
	 * @return (word,weight) for this Term
	 */
	@Override
	public String toString() {
		return String.format("(%2.1f,%s)", myWeight, myWord);
	}
	
	/**
	 * Use default compareTo, so only word for equality
	 * @return true if this.getWord().equal(o.getWord())
	 */
	@Override
	public boolean equals(Object o) {
		Term other = (Term) o;
		return this.compareTo(other) == 0;
	}

	/**
	 * A Comparator for comparing Terms using a set number of the letters they
	 * start with. 
	 * This Comparator required for assignment.
	 *
	 */
	public static class PrefixOrder implements Comparator<Term> {
		private final int myPrefixSize;

		public PrefixOrder(int r) {
			this.myPrefixSize = r;
		}

		/**
		 * Compares v and w lexicographically using only their first r letters.
		 * If the first r letters are the same, then v and w should be
		 * considered equal. This method should take O(r) to run, and be
		 * independent of the length of v and w's length. You can access the
		 * Strings to compare using v.word and w.word.
		 * 
		 * @param v/w
		 *            - Two Terms whose words are being compared
		 */
		public int compare(Term v, Term w) {
			// TODO: Implement compare
			int wordmin = Math.min(v.myWord.length(), w.myWord.length());
			int min = Math.min(wordmin, myPrefixSize);
			for (int i=0; i<min; i++) {
					if (v.myWord.charAt(i) != w.myWord.charAt(i)) {
						return (v.myWord.charAt(i) - w.myWord.charAt(i));
					}
			}
			if (wordmin < myPrefixSize) {
				return v.myWord.length() - w.myWord.length();
			}
			return 0;
		}
	
	}

	/**
	 * A Comparator for comparing Terms using only their weights, in descending
	 * order. This comparator required for assignment.
	 *
	 */
	public static class ReverseWeightOrder implements Comparator<Term> {
		public int compare(Term v, Term w) {
			// TODO: Implement compare
			if (v.getWeight() < w.getWeight()) {
				return 1;
//			} else if (v.getWeight() == w.getWeight()) {
//				return 0;
			} else if (w.getWeight() < v.getWeight()) {
				return -1;
			}
			return 0;
		}
	}

	/**
	 * A Comparator for comparing Terms using only their weights, in ascending
	 * order. This comparator required for assignment.
	 *
	 */
	public static class WeightOrder implements Comparator<Term> {
		public int compare(Term v, Term w) {
			//TODO: Implement compare
			if (v.getWeight() < w.getWeight()) {
				return -1;
//			} else if (v.getWeight() == w.getWeight()) {
//				return 0;
			} else if (w.getWeight() < v.getWeight()) {
				return 1;
			}
			return 0;
		}
	}
}
