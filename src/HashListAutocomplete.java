import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class HashListAutocomplete implements Autocompletor {
	private int mySize;
	private static final int MAX_PREFIX = 10;
	private Map<String, List<Term>> myMap;
	
	
	public HashListAutocomplete(String[] terms, double[] weights) {

		if (terms == null || weights == null) {
			throw new NullPointerException("One or more arguments null");
		}

		if (terms.length != weights.length) {
			throw new IllegalArgumentException("terms and weights are not the same length");
		}
		initialize(terms,weights);
	}
	
	@Override
	public void initialize(String[] terms, double[] weights) {
		myMap = new HashMap<>();
		for (int i = 0; i < terms.length; i++) {
			if (weights[i] < 0) {
				throw new IllegalArgumentException("Negative weight "+ weights[i]);
			}
			String s = terms[i];
			Term t = new Term(terms[i], weights[i]);
			int length = Math.min(MAX_PREFIX, s.length());
			for (int j = 0; j <= length; j++) {
				String a = s.substring(0,j);
				myMap.putIfAbsent(a,new ArrayList<>());
				myMap.get(a).add(t);
			}
		}
		for (String key : myMap.keySet()) {
			Collections.sort(myMap.get(key), new Term.ReverseWeightOrder());
		}
	}
	
	@Override
	public List<Term> topMatches(String prefix, int k) {
		int length = Math.min(MAX_PREFIX, prefix.length());
		prefix = prefix.substring(0,length);
		if (!myMap.containsKey(prefix)) return new ArrayList<>();
		//Collections.sort(myMap.get(prefix), new Term.ReverseWeightOrder());
		List<Term> every = myMap.get(prefix);
		List<Term> list = every.subList(0, Math.min(k, every.size()));
		return list;
	}
	
	@Override
	public int sizeInBytes() {
		if (mySize == 0) {
			
			for(String key1 : myMap.keySet()) {
				int a = 0;
				int c = 0;
				List<Term> b = myMap.get(key1);
				for (Term t : b) {
					a+=t.getWord().length();
					c+=1;
				}
			    mySize += BYTES_PER_DOUBLE*c + 
			    		  BYTES_PER_CHAR*(key1.length()+a);	
			}
		}
		return mySize;
	}
} 
