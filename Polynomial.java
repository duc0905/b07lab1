import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class Polynomial {
	double[] coefs;
	int [] pols;

	public Polynomial() {
		coefs = new double[]{0};
		pols = new int[]{0};
	}

	public Polynomial(double[] c) {
		coefs = c;
	}

	public Polynomial(String f) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(f));
			String line = input.readLine();
			System.out.println(line);

			double[] new_c = new double[line.length()];
			int[] new_p = new int[line.length()];
			int n_p = 0;

			String[] s1 = line.split("\\+", -1);
			for (String s : s1)
			{
				String[] s2 = s.split("-", -1);
				String[] c_p = s2[0].split("x");
				int p = 0;
				double c = Double.parseDouble(c_p[0]);
				if (c_p.length > 1)
					p = Integer.parseInt(c_p[1]);

				new_c[n_p] = c;
				new_p[n_p] = p;
				n_p++;

				for (int i = 1; i < s2.length; i++)
				{
					c_p = s2[i].split("x");
					p = 0;
					c = Double.parseDouble(c_p[0]);
					if (c_p.length > 1)
						p = Integer.parseInt(c_p[1]);
					new_c[n_p] = -c;
					new_p[n_p] = p;
					n_p++;
				}

			}

			coefs = new double[n_p];
			pols = new int[n_p];
			for (int i = 0; i < n_p; i++){
				coefs[i] = new_c[i];
				pols[i] = new_p[i];
				System.out.println(coefs[i] + "x^" + pols[i]);
			}

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Polynomial add(Polynomial o) {
		Polynomial ans = new Polynomial(o);

		if (o.coefs.length < coefs.length)
			ans = new Polynomial(this);

		for (int i = 0; i < min(coefs.length, o.coefs.length); i++)
		{
			ans.coefs[i] = coefs[i] + o.coefs[i];
			ans.pols[i] = pols[i] + o.pols[i];
		}

		return ans;
	}

	public Polynomial multiply(Polynomial o) {
		double[] new_c = new double[pols.length * o.pols.length];
		int[] new_p = new int[pols.length * o.pols.length];
		int n_p = 0;

		for (int i = 0; i < pols.length; i++) {
			for (int j = 0; j < o.pols.length; j++) {
				int pos = -1;
				int p = pols[i] + o.pols[j];
				for (int k = 0; k < n_p; k++)
					if (new_p[k] == p) {
						pos = k;
						break;
					}
				
				if (pos == -1) {
					new_c[n_p] = coefs[i] * coefs[j];
					new_p[n_p] = p;
					n_p++;
				} else {
					new_c[pos] += coefs[i] * coefs[j];
				}

			}
		}

		Polynomial ans = new Polynomial();
		ans.coefs = new double[n_p];
		ans.pols = new int[n_p];

		for (int i = 0; i < n_p; i++) {
			ans.coefs[i] = new_c[i];
			ans.pols[i] = new_p[i];
		}
		return ans;
	}

	public double evaluate(double x) {
		double ans = 0;
		for (int i = 0; i < coefs.length; i++)
			ans += coefs[i] * power(x, pols[i]);
		return ans;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}

	public void display() {
		System.out.println("=============");
		for (int i = 0; i < pols.length; i++) {
			System.out.println(coefs[i] + "x^" + pols[i]);
		}
		System.out.println("=============");
	}

	public void saveToFile(String f) {
		try {
			FileWriter o = new FileWriter(f);
			o.write(stringify());
			o.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String stringify() {
		String ans = "";
		for (int i = 0; i < pols.length; i++) {
			String temp = "";
			temp += coefs[i];
			if (coefs[i] > 0)
				temp = "+" + temp;
			if (pols[i] != 0)
				temp += "x" + pols[i];
			ans += temp;
		}
		if (ans.charAt(0) == '+')
			return ans.substring(1);
		return ans;
	}

	private Polynomial(Polynomial o) {
		coefs = o.coefs;
		pols = o.pols;
	}

	private double power(double b, int p) {
		if (p == 0) return 1;
		if (p == 1) return b;

		return power(b, p / 2) * power(b, p / 2) * power(b, p % 2);
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	private int min(int a, int b) {
		return a < b ? a : b;
	}
}
