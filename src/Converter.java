import java.awt.Color;

public class Converter {
	private char hexValues[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'}; //used for hex operations
	
	public String decToBinary(long i) { //takes decimal and returns binary
		String output ="";
		long temp;
		while(i>0) {
			temp = i%2;
			output=temp+output;
			i/=2;
		}
		return output;
	}
	
	public long binaryToDec(String b) { //takes binary and returns decimal
		long output = 0, n = 0;
		for(int i = b.length();i>0;i--) {	
			output += (Character.getNumericValue(b.charAt(i-1))*(Math.pow(2, n)));
			n++;
		}
		return output;
	}
	
	public String hexToBinary(String h) { //takes hex and returns binary
		String output ="";
		h=h.substring(2);
		int dec = 0;
		String hexString = new String(hexValues);
		for(int i =0;i<h.length();i++) {
			dec=16*dec + (hexString.indexOf(h.charAt(i)));
		}
		output=decToBinary(dec);
		return output;
	}
	
	public String binaryToHex(String b) { //takes binary and returns hex
		String output = "";
		long dec = binaryToDec(b), remainder=0;
		while(dec>0) {	
			remainder = dec%16;
			output=hexValues[(int)remainder]+output;
			dec/=16;
		}
		output = "0x"+output;
		return output;
	}
	
	public String octToBinary(long o) { //takes octal and returns binary
		String output = "";
		int dec =0;
		for(int i =0;o!=0;i++) {
			dec += ((o%10) * Math.pow(8,i));
			o /= 10;
		}
		output=decToBinary(dec);
		return output;
	}
	
	public long binaryToOct(String b) { //takes binary and returns octal
		long dec = binaryToDec(b);
		long oct=0;
		for(long i = 1;dec!=0;i*=10) {
			oct += (dec%8) * i;
			dec /= 8;
		}
		if(Long.toString(oct).contains("8")||Long.toString(oct).contains("9"))
			oct=Long.parseLong("7777777777777777777"); //maximum octal value storable by a long
		return oct;
	}
	
	public String ASCIIToBinary(String A) { //takes ascii characters and returns binary value
		String output = "";
		for(int i=0;i<A.length();i++) {
			char c = A.charAt(i);
			int dec = (int)c;
			String temp = decToBinary(dec);
			while(temp.length()!=8) //add a 0 to the front of each character if needed to make 8 bits
				temp = "0"+temp;
			output += temp;
		}
		return output;
	}
	
	public String binaryToASCII(String b) { //takes binary and returns ascii characters in a string
		String output = "";
		while(b.length()%8!=0) //append 0's until reach a length divisible by 8
			b="0"+b;
		while(b.length()!=0) {
			String temp = b.substring(0,8);
			b=b.substring(8);
			long dec = binaryToDec(temp);
			char c = (char)dec;
			output+=c;
		}
		return output;
	}
	
	public Color hexToColor(String h) { //takes hex value and returns a color
		int r,g,b;
		h=h.substring(2);
		while(h.length()<6) //make sure exactly 6 digits by either appending 0's or truncating
			h="0"+h;
		while(h.length()>6)
			h=h.substring(1);
		r=(int) binaryToDec(hexToBinary("0x"+h.substring(0,2)));
		g=(int) binaryToDec(hexToBinary("0x"+h.substring(2,4)));
		b=(int) binaryToDec(hexToBinary("0x"+h.substring(4,6)));
		Color output = new Color(r,g,b); 
		return output;
	}
	
	public String colorToHex(Color c) { //takes color and returns a hex value 
		String output = "";
		int r=c.getRed(), g=c.getGreen(),b=c.getBlue();
		String R=(binaryToHex(decToBinary(r))).substring(2);
		while(R.length()<2)
			R="0"+R;
		String G=(binaryToHex(decToBinary(g))).substring(2);
		while(G.length()<2)
			G="0"+G;
		String B=(binaryToHex(decToBinary(b))).substring(2);
		while(B.length()<2)
			B="0"+B;
		output="0x"+R+G+B;
		return output;
	}

	private String excessToBinary(String b) { //converts excess-127 to binary
		long dec = binaryToDec(b);
		dec-=127;
		String output = decToBinary(dec);
		while(output.length()<8)
			output = "0"+output;
		return output;
	}
	private float handleMantisse(String b) { //takes binary in form 1xxxxxxxxx
		float d = 1;
		int exp=-1;
		b=b.substring(1);
		for(int i=0;i<23;i++) {
			if(b.charAt(i)=='1') {
				d+=Math.pow(2,exp);
			}	
			exp--;
		}
		return d;
	}
	public String IEEEToFloat(String b) { //takes binary in ieee-754 format
		String output="0";
		String e,m;
		float mantisse = 0;
		double exp=0.0;
		int pow=0;
		if(b.length()>0&&b.charAt(0)=='1')
			b="0"+b;
		while(b.length()<32)
			b+="0";
		if(b.length()==32) {
			e=b.substring(1, 9);
			if(e.equals("11111111"))
				output="NaN";
			else {
				exp = binaryToDec(excessToBinary(e));
				exp= Math.pow(2, exp);
				m=b.substring(9);
				m="1"+m;
				mantisse = handleMantisse(m);
				mantisse*=exp;
				while(mantisse>10) {
					mantisse/=10;
					pow++;
				}
				output=mantisse+"e"+pow;
				if(b.charAt(0)=='1')
					output="-"+output;
			}
		}
		return output;
	}

	public String floatToBinary(String f) { //takes string of type #.##e### and returns binary
		String output ="",n,e;
		float num=0,exp=0;
		n=f.substring(0, f.indexOf("e"));
		e=f.substring(f.indexOf("e")+1);
		num=Float.parseFloat(n);
		exp=(float) Math.pow(10,Float.parseFloat(e));
		long dec = (long) (exp*num);
		output=decToBinary(dec);
		return output;
	}
}
