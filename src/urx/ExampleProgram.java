package urx;

public class ExampleProgram {
	
	// Specifies joint positions of base, shoulder, 
	// elbow, wrist1, wrist2, wrist3, respectively
	static double[] waypoint1 = {94.14, -121.48, 101.14, -69.44, -90.36, 0};
	static double[] waypoint2 = {80, -120, 80, -69.44, -90.36, 0};
	
	//Relative cardinal movement, takes dx, dy, dx, rx, ry, rz
	// This moves the robot 10 centimeters forwards on +y
	static double[] relativeMove1 = {0, 0.1, 0, 0, 0, 0};
	
	static final String arm_ip = "10.1.6.101";
	
	public static void main(String[] args) {
		
		System.out.println("Starting");
		
		Arm arm = new Arm(arm_ip);
		
		
		while(true) {
			arm.moveJDegrees(waypoint1, 1, 1);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("———————————————————————————");
			arm.moveJDegrees(waypoint2, 1, 1);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("———————————————————————————");
		}

//		arm.moveJDegrees(waypoint2, 5, 5);
		
//		System.out.println("Finishing");
//		
//		arm.moveL(relativeMove1, 1, 1);
	}
}
