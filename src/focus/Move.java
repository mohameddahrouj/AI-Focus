package focus;

public class Move {
	
	private int r1;
	private int c1;
	private int r2;
	private int c2;
	
	public Move(int r1, int c1, int r2, int c2){

		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
	}

	public int getr1() {
		return r1;
	}

	public void setr1(int r1) {
		this.r1 = r1;
	}

	public int getc1() {
		return c1;
	}

	public void setc1(int c1) {
		this.c1 = c1;
	}

	public int getr2() {
		return r2;
	}

	public void setr2(int r2) {
		this.r2 = r2;
	}

	public int getc2() {
		return c2;
	}

	public void setc2(int c2) {
		this.c2 = c2;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(r1);
		sb.append(",");
		sb.append(c1);
		sb.append(" to ");
		sb.append(r2);
		sb.append(",");
		sb.append(c2);
		return sb.toString();
	}
}
