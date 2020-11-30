import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main_Client {

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("IoRMatrix CLIENT Start ...");
					System.out.print("IoRMatrix_C > ");
					Socket client = new Socket("localhost", 3000);
					PrintStream ps = new PrintStream(client.getOutputStream());
					Scanner scan = new Scanner(System.in);
					String s ;
					while(!(s = scan.nextLine()).equals("exit")) {
						System.out.print("IoRMatrix_C > ");
						ps.println(s);
					}
					System.out.println("Bye");
					ps.println(s);
					ps.close();
					scan.close();
					client.close();
				} catch (Exception e) {}
			}
		}.start();
	}

}