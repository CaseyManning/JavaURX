package urx;
import java.util.Arrays;

public class Arm {
	
	Networker net;
	
	public Arm(String ip) {
		net = new Networker(ip);
	}
	/**
	 * @param movement Specifies the joint angles (in radians) of the movement to perform
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveJ(double[] jointPositions, double a, double v) {
		String message = "movej(" + Arrays.toString(jointPositions) + ", a="+a+", v="+v+", r=0)\n";
		net.send(message);
	}
	/**
	 * @param movement Specifies the joint angles (in degrees) of the movement to perform
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveJDegrees(double[] jointPositions, double a, double v) {
		double[] radians = new double[jointPositions.length];
		for(int i = 0; i < jointPositions.length; i++) {
			radians[i] = Math.toRadians(jointPositions[i]);
		}
		String message = "movej(" + Arrays.toString(radians) + ", a="+a+", v="+v+", r=0)\n";
		net.send(message);
	}
	
	/**
	 * @param movement Specifies the cartesian coordinates of the desired arm position (from the base of the arm)
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveL(double[] movement, double a, double v) {
		String message = "movel(" + Arrays.toString(movement) + ", a="+a+", v="+v+")\n";
		net.send(message);
	}
	
	/**
	 * @param movement Specifies the cartesian coordinates of the desired arm position (relative to the current arm position)
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveLRelative(double[] movement, double a, double v) {
		double[] realMove = new double[6];
		double[] joints = getArmCartesian();
		for(int i = 0; i < movement.length; i++) {
			realMove[i] = movement[i] - joints[i];
		}
		String message = "movel(" + Arrays.toString(realMove) + ", a="+a+", v="+v+")\n";
		net.send(message);
	}
	/**
	 * @param movement Specifies the angle (in radians) of a relative movement of the arms joints
	 * @param a The acceleration of the arm movement
	 * @param v The velocity of the arm movement 
	 */
	public void moveJRelative(double[] movement, double a, double v) {
		double[] realMove = new double[6];
		double[] joints = getArmJoints();
		for(int i = 0; i < movement.length; i++) {
			realMove[i] = movement[i] - joints[i];
		}
		String message = "movel(" + Arrays.toString(realMove) + ", a="+a+", v="+v+")\n";
		net.send(message);
	}
	
	public double[] getArmJoints() {
		return net.allData.get("joints");
	}
	
	public double[] getArmCartesian() {
		return net.allData.get("cartesian");
	}
}
