

public class Polynomial {
	double[] coefs;

	public Polynomial() {
		coefs = new double[]{0};
	}

	public Polynomial(double[] c) {
		coefs = c;
	}

	public Polynomial add(Polynomial o) {
		Polynomial ans = new Polynomial(o);

		if (o.coefs.length < coefs.length)
			ans = new Polynomial(this);

		for (int i = 0; i < min(coefs.length, o.coefs.length); i++)
			ans.coefs[i] = coefs[i] + o.coefs[i];

		return ans;
	}

	public double evaluate(double x) {
		double ans = 0;
		for (int i = 0; i < coefs.length; i++)
			ans += coefs[i] * power(x, i);
		return ans;
	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}

	private Polynomial(Polynomial o) {
		coefs = o.coefs;
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
