package urx;
import java.util.Arrays;

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
		String message = "Program({movej" + Arrays.toString(jointPositions) + ", a="+a+", v="+v+", r=0)})";
		net.send(message);
	}
	
	
	/**
	 * @param movement Specifies the relative movement to perform in
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveRelative(double[] movement, double a, double v) {
		String message = "Program({" + Arrays.toString(movement) + ", a="+a+", v="+v+")})";
		net.send(message);
	}
}
