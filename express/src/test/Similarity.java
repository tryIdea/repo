package test;

public class Similarity {
	public static void main(String[] args) {
		String str1 = "泰安一大型挖掘机施工不慎滚下山坡 驾驶员被困";
		String str2 = "泰安一大型挖掘机施工不慎滚下山坡 驾驶员被困(图)";
		String str3 = "泰安一大型挖掘机施工不慎滚下山坡&nbsp;驾驶员被困";
		
		double simi = getSimi(str2, str3);
		if(simi>0.7) {
			System.out.println("--" + simi);
		}
	}
	
	private static double getSimi(String str1, String str2) {
		double total = 1;
		double count = 0;
		if(str1.length() <= str2.length()) {
			total = str1.length();
			for(int i=0; i<str1.length(); i++) {
				String s = String.valueOf(str1.charAt(i));
				if(str2.contains(s)) {
					count++;
				}
			}
		}
		return count/total;
	}
}
