package server;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    public static void main(String[]args){

        // ServerSocket oprettes og port 8001 angives som den der skal lyttes på
        ServerSocket ss;


        try {
            // ServerSocket oprettes og port 8001 angives som den der skal lyttes på
            ss = new ServerSocket(8001);
            System.out.println("Server kører..!");
            while (true) {
                //så længe der ikke er oprettet en forbindelse , venter serveren her
                //så snart der anmodes om en forbindelse accepteres den med accept()
                Socket s = ss.accept();
                System.out.println("Klient forbundet");


                /*
                Den nye forbindelse sendes til vores ClientConnection object,
                hvor al logik for serveren bliver håndteret.
                Der oprettes en ny tråd, som modtager ClientConnection objectet
                og tråden startes
                 */
                Runnable r = new ClientConnection(incoming);


                /*
                vi har behov for at kommunikere med klienten. Vi opretter derfor en input og en
                output stream, og binder gver især til Socket'ens input og output stream
                Sockets kører i full-dublex og der er dermed tovejs kommunikation til rådighed

                 */
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                /*
                til at læse inputstreamen med bruger vi her en scanner.
                Det kunne lige så godt være en buffer reader.
                (hvis forbindelsen der modtages FRA lukker efter sig.
                ellers stopper den aldrig med at læse)
                 */

                Scanner in = new Scanner(input);

                // Når vi skriver til output streamen bruger vi her en PrintWriter.
                PrintWriter out = new PrintWriter(output, true);

                /*
                Lad os sige velkommen til den der har forbundet til serveren,
                så den ved der er hul igennem.
                 */
                out.println("Velkommen");

                /*
                Vi ønsker at have kontrol over hvornår forbindelsen skal lukkes
                fra denne side
                Vi ønsker kun at lukke forbindelsen, npr brugeren skriver "luk ned"

                 */
                boolean done = false;
                while (!done && in.hasNextLine())

                {


                /*
                Her Starter Scannerens arbejde. Hvis der ikke er nogle
                linjer, afventer den til der kommer en
                 */
                    String stream = in.nextLine();
                    if (stream.equals("luk ned")) {
                        done = true;

                    } else {
                        //når vi skriver, sender vi en linie med PrintWriter
                        out.println(stream);
                    }
                }

                s.close();
                System.out.println("Forbindelsen til klienten blev lukket");

             Thread t = new Thread(r);
             t.start();
            }

        }
        catch (IOException ex){
            ex.printStackTrace();
            }


        }



    }















