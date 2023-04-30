import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {

    private static ExecutorService exec = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10314);
            Socket socket;
            while((socket = server.accept()) != null) {
                Socket currSocket = socket;
                Runnable request = () -> {
                    try {
                        handleRequest(currSocket);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                };
                exec.execute(request);
            }
            server.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void handleRequest(Socket socket) throws IOException{
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        RemoteMethod remote_method = (RemoteMethod) in.readObject();
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        handleRemoteMethod(remote_method, out);
        out.flush();
        out.close();
        in.close();
    }

    private static void handleRemoteMethod(RemoteMethod remote_method,  ObjectOutputStream out) throws IOException {
        String method_name = remote_method.getMethodName();
        Object[] args = remote_method.getArguments();
        try {
            if (method_name.equals("add")) {
                out.writeObject(add((int)args[0], (int)args[1]));
            } else if (method_name.equals("divide")) {
                out.writeObject(divide((int)args[0], (int)args[1]));
            } else if (method_name.equals("echo")) {
                out.writeObject(echo((String)args[0]));
            }
        } catch (Exception ex) {
            out.writeObject(ex); 
        }
    }
     
        
    

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}