import java.util.Scanner;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {
				JFrame frame = new JFrame("IoRMatrix");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 600, 600);				
				MyMatrix matrix = new MyMatrix();
				frame.add(matrix);
				frame.setVisible(true);
				Scanner scan = new Scanner(System.in);
				String s ;
				while(!(s = scan.nextLine()).equals("exit")) {
					if(s.equals("afficher")) matrix.afficher();
					if(s.equals("cacher")) matrix.cacher();
					if(s.equals("init")) matrix.init();
					if(s.equals("exit")) System.exit(0);
					if(s.equals("d")) matrix.incX();
					if(s.equals("g")) matrix.decX();
					if(s.equals("b")) matrix.incY();
					if(s.equals("h")) matrix.decY();
				}
				matrix.cacher();
				System.out.println("Bye");
				frame.dispose();
				scan.close();
			}
		}.start();
	}

}