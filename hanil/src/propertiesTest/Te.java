package propertiesTest;

public class Te {
	public static void main(String[] args) {
		String str = "super han\"dfdkjfhkjhjfdfkjhlkjhuchbjh kjhvskufhsd kdjdfhk kjvs\"il super";
		
		String[] sts = str.split("\"");
		
		String a = sts[0] + sts[2];
		
		System.out.println(a);
		
		
		
	}

}
