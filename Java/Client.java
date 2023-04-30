import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
public class Client {

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        try (Socket socket = new Socket("localhost", 10314);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());) {
            os.writeObject(new RemoteMethod("add", new Object[]{lhs, rhs}));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            return (int) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom) throws ArithmeticException {
        try (Socket socket = new Socket("localhost", 10314);
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());) {
            os.writeObject(new RemoteMethod("divide", new Object[]{num, denom}));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            Object result = is.readObject();
            try {
                return (int) result;
            } catch (ClassCastException e) {
                throw new ArithmeticException();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        try (Socket socket = new Socket("localhost", 10314);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());) {
            os.writeObject(new RemoteMethod("echo", new Object[]{message}));
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            return (String) is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    

    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello") == "You said Hello!")
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}