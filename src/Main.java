import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException, IOException {
        Server();

    }
    public static void Server() throws IOException {
        String request;
        System.out.println("krok 1");
        ServerSocket server = new ServerSocket(10000);

        //funkce date()
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //vypisuje datum a čas v tomto formátu pomocí ofPattern("")
        LocalDateTime now = LocalDateTime.now(); //lokální časová zona

        int x = 0;

        while(true){
            //čekám až se na server neěkdo připojí
            try(Socket socket = server.accept()){
                System.out.println("Připojeno na server");

                //získáni IP adresy klienta
                InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
                String clientIpAddress = socketAddress.getAddress().getHostAddress();

                //získání vstupu klienta
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                request = in.readLine();

                while(!(request.isEmpty())) {
                    //vypsání vstupu a IP adresy
                    System.out.println("request from " + clientIpAddress + " : " + request);
                    request = in.readLine();
                }

                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("HTTP/1.1 200 OK");
                out.println("Connection: close");
                out.println("Connect-Type: text/html; charset=UTF-8");
                out.println();
                out.println("<html><body><form action=\"DynamicGeneratingWebsite\" method=\"post\"><center><input type=\"text\" size=\"20\" name=\"jmeno_uzivatele\"></center></form></body></html>");
                
                out.close();
            } //java sama zavolá close
            x++;
        }
    }
}
