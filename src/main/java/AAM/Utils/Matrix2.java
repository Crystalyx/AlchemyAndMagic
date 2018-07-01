package AAM.Utils;

public class Matrix2
{
	/**
	 * left corner is (0,0)
	 */
	public double[][] arr = new double[2][2];

	public static final Matrix2 E = new Matrix2(1, 0, 0, 1);
	public static final Matrix2 i = new Matrix2(0, 1, -1, 0);

	public Matrix2()
	{
	}

	public Matrix2(double a, double b, double c, double d)
	{
		this.arr[0][0] = a;
		this.arr[1][0] = b;
		this.arr[0][1] = c;
		this.arr[1][1] = d;
	}

	public Matrix2(double[][] matr)
	{
		this.arr = matr;
	}

	/**
	 * vectors - columns
	 */
	public Matrix2(Vec2 ac, Vec2 bd)
	{
		this.arr[0][0] = ac.x;
		this.arr[1][0] = bd.x;
		this.arr[0][1] = ac.y;
		this.arr[1][1] = bd.y;
	}

	public Matrix2 crossProduct(Matrix2 m)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = this.arr[0][0] * m.arr[0][0] + this.arr[1][0] * m.arr[0][1];
		ret.arr[1][0] = this.arr[0][0] * m.arr[1][0] + this.arr[1][0] * m.arr[1][1];
		ret.arr[0][1] = this.arr[0][1] * m.arr[0][0] + this.arr[1][1] * m.arr[0][1];
		ret.arr[1][1] = this.arr[0][1] * m.arr[1][0] + this.arr[1][1] * m.arr[1][1];

		return ret;
	}

	public Matrix2 transp()
	{
		return new Matrix2(this.arr[0][0], this.arr[0][1], this.arr[1][0], this.arr[1][1]);
	}

	public double det()
	{
		return this.arr[0][0] * this.arr[1][1] - this.arr[0][1] * this.arr[1][0];
	}

	public Vec2 crossProduct(Vec2 m)
	{
		return new Vec2(this.arr[0][0] * m.x + this.arr[1][0] * m.y, this.arr[0][1] * m.x + this.arr[1][1] * m.y);
	}

	public Matrix2 dotProduct(Matrix2 m)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = this.arr[0][0] * m.arr[0][0];
		ret.arr[1][0] = this.arr[1][0] * m.arr[1][0];
		ret.arr[0][1] = this.arr[0][1] * m.arr[0][1];
		ret.arr[1][1] = this.arr[1][1] * m.arr[1][1];

		return ret;
	}

	public Matrix2 add(Matrix2 m)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = this.arr[0][0] + m.arr[0][0];
		ret.arr[1][0] = this.arr[1][0] + m.arr[1][0];
		ret.arr[0][1] = this.arr[0][1] + m.arr[0][1];
		ret.arr[1][1] = this.arr[1][1] + m.arr[1][1];

		return ret;
	}

	public Matrix2 multiply(double m)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = this.arr[0][0] * m;
		ret.arr[1][0] = this.arr[1][0] * m;
		ret.arr[0][1] = this.arr[0][1] * m;
		ret.arr[1][1] = this.arr[1][1] * m;

		return ret;
	}

	public String toString()
	{
		return "[ " + this.arr[0][0] + "  " + this.arr[1][0] + " ]" + "\n" + "[ " + this.arr[0][1] + "  " + this.arr[1][1] + " ]";
	}

	public static Matrix2 rotation(double a)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = Math.cos(a);
		ret.arr[1][0] = -Math.sin(a);
		ret.arr[0][1] = Math.sin(a);
		ret.arr[1][1] = Math.cos(a);

		return ret;
	}

	public static Matrix2 scale(double x, double y)
	{
		Matrix2 ret = new Matrix2();

		ret.arr[0][0] = x;
		ret.arr[1][0] = 0;
		ret.arr[0][1] = 0;
		ret.arr[1][1] = y;

		return ret;
	}
}
