import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Noeud {

	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run() {				
				System.out.println("IoRMatrix SERVEUR Noeud Start ...");				
				try {
					boolean ready = true;
                    ServerSocket Command = new ServerSocket(3000);
                    ServerSocket Matrix = new ServerSocket(3001);
                    Socket readFromCommand = null;
                    Socket writeToMatrix = null;
                    String s;

					while(ready) {
                        System.out.println("Wait for Clients  ...");
                        readFromCommand = Command.accept();
                        System.out.println("Client Commande connecte ...");
                        writeToMatrix = Matrix.accept();
						System.out.println("Client Matrix connecte ...");

                        
                        
                        BufferedReader br = new BufferedReader(new InputStreamReader(readFromCommand.getInputStream()));
                        PrintStream ps = new PrintStream(writeToMatrix.getOutputStream());

                        while(!(s = br.readLine()).equals("exit")){
                            System.out.println("Commande recu : " + s);
                            ps.println(s);
                            System.out.println("Commande envoye : " + s);
                        }

                        ps.println(s);
                        br.close();
                        ps.close();

					}
                    readFromCommand.close();
                    writeToMatrix.close();
                    Command.close();
                    Matrix.close();
				}
				catch(Exception e) {}
			}
		}.start();
	}

}