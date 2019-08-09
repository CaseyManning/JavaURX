package urx;

public class Arm {
	
	Networker net;
	
	public Arm(String ip) {
		net = new Networker(ip);
	}
	/**
	 * @param movement Specifies the joint angles of the movement to perform
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveJoint(double[] jointPositions, double a, double v) {
		String message = "Program({movej" + jointPositions.toString() + ", a="+a+", v="+v+")})";
		net.send(message);
	}
	
	
	/**
	 * @param movement Specifies the relative movement to perform in
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveRelative(double[] movement, double a, double v) {
		String message = "Program({" + movement.toString() + "})";
		net.send(message);
	}
}
