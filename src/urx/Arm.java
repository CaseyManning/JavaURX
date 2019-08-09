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
	public void moveJ(double[] jointPositions, double a, double v) {
		String message = "movej(" + Arrays.toString(jointPositions) + ", a="+a+", v="+v+", r=0)\n";
		net.send(message);
	}
	
	public void moveJDegrees(double[] jointPositions, double a, double v) {
		double[] radians = new double[jointPositions.length];
		for(int i = 0; i < jointPositions.length; i++) {
			radians[i] = Math.toRadians(jointPositions[i]);
		}
		String message = "movej(" + Arrays.toString(radians) + ", a="+a+", v="+v+", r=0)\n";
		net.send(message);
	}
	
	
	/**
	 * @param movement Specifies the relative movement to perform in
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveL(double[] movement, double a, double v) {
		String message = "movel(" + Arrays.toString(movement) + ", a="+a+", v="+v+")\n";
		net.send(message);
	}
	
	public void moveLRelative(double[] movement, double a, double v) {
		throw new UnsupportedOperationException();
	}
}
