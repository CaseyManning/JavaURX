package urx;

public class ExampleProgram {
	
	// Specifies joint positions of base, shoulder, 
	// elbow, wrist1, wrist2, wrist3, respectively
	static double[] waypoint1 = {60, 30, 30, 35, 50, 100};
	static double[] waypoint2 = {30, 30, 50, 70, 50, 80};
	
	//Relative cardinal movement, takes dx, dy, dx, rx, ry, rz
	// This moves the robot 10 centimeters forwards on +y
	static double[] relativeMove1 = {0, 0.1, 0, 0, 0, 0};
	
	static final String arm_ip = "10.1.6.101";
	
	public static void main(String[] args) {
		
		System.out.println("Starting");
		
		Arm arm = new Arm(arm_ip);
		
		arm.moveJointAbsolute(waypoint1, 1, 1);
		
		arm.moveJointAbsolute(waypoint2, 5, 5);
		
		System.out.println("Finishing");
		
		arm.moveLinear(relativeMove1, 1, 1);
	}
}
