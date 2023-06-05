
public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		p.coefs = new double[]{1, 2, 3};
		p.pols = new int[]{1, 2, 3};
		Polynomial a = p.multiply(p);
		a.saveToFile("multiplyTest.out");

		Polynomial fromFile = new Polynomial("test.txt");
		fromFile.saveToFile("readFileTest.out");

	}
}
