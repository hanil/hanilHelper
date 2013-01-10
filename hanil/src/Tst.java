public class Tst {
	public static void main(String[] args) {

		String key = "DEVP.HINF_PERMANENT_CONNECTION";
		String poolName = "";

		String[] str;
		String[] conf;
		str = key.split("\\.");
		
		conf = str[1].split("_");
		poolName = conf[0];

		System.out.println("poolName  : " + poolName);
		
	}

}
