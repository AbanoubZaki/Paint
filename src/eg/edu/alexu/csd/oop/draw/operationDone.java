package eg.edu.alexu.csd.oop.draw;

public class operationDone {
	/**
	 * shape added , removed or replaced.
	 */
	public Shape shape1;
	
	/**
	 * used in case of replacement only.
	 */
	public Shape shape2;
	
	/**
	 * operation done.
	 * 1 --> added shape.
	 * 2 --> removed shape.
	 * 3 --> replaced a shape.
	 */
	public int operation;
	
	public operationDone(Shape shape1, int operation) {
		this.shape1 = shape1;
		this.operation = operation;
	}
	
	public operationDone(Shape shape1, Shape shape2) {
		this.shape1 = shape1;
		this.shape2 = shape2;
		this.operation = 3;
	}
}
