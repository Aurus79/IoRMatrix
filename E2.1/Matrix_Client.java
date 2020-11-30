import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class Matrix_Client {

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
				
				try {
					boolean ready = true;
					while(ready) {
						System.out.println("IoRMatrix CLIENT Start ...");
						Socket readFromNoeud = new Socket("localhost", 3001);
						System.out.println("Client connecte ...");
						
						matrix.init();

						BufferedReader br = new BufferedReader(new InputStreamReader(readFromNoeud.getInputStream()));
						String s ;
						while(!(s = br.readLine()).equals("exit")) {
							if(s.equals("d")) { System.out.println("DROITE"); matrix.incX(); System.out.print("IoRMatrix_S > "); }
							if(s.equals("g")) { System.out.println("GAUCHE"); matrix.decX(); System.out.print("IoRMatrix_S > "); }
							if(s.equals("b")) { System.out.println("BAS"); matrix.incY(); System.out.print("IoRMatrix_S > "); }
							if(s.equals("h")) { System.out.println("HAUT"); matrix.decY(); System.out.print("IoRMatrix_S > "); }
							if(s.equals("init")) { System.out.println("INIT"); matrix.init(); System.out.print("IoRMatrix_S > ");}
							if(s.equals("afficher")) { System.out.println("INIT"); matrix.afficher(); System.out.print("IoRMatrix_S > "); }
						}
						matrix.cacher();
						System.out.println("Bye");
						readFromNoeud.close();
					}
					frame.dispose();

				}
				catch(Exception e) {}
			}
		}.start();
	}

}