package serializeTest;

import java.util.Hashtable;
import java.util.Map;

public class Start {
	public static void main(String[] args) {
		
		Map<String, String> result = new Hashtable<String, String>();
		
		Map<String, String> outData = new Hashtable<String, String>();
		outData.put("1", "h");
		outData.put("2", "a");
		outData.put("3", "n");
		outData.put("4", "i");
		outData.put("5", "l");
		
		SerialTest st = new SerialTest(outData);
		st.save("d:/hihi.ser");
		
		result = st.load("d:/hihi.ser");
		
		System.out.print(result.get("1"));
		System.out.print(result.get("2"));
		System.out.println(result.get("3"));
		System.out.print(result.get("4"));
		System.out.print(result.get("5"));
		
	}
}
