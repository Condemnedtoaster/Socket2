package server;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientConnection implements Runnable {

    private Socket s;


    public ClientConnection(Socket s) throws SocketException, IOException {
        this.s = s;
    }


    public void run() {

        try {
            try {

                /*Vi har behov for at kommunikere med serveren. Vi opretter derfor
                en input og output stream, og binder hver isæt til Socket'ens
                input og output stream.
                Sockets kører i full-duplex og der er dermed tovejs kommunikation
                til rådighed
                 */

                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();



                /*
                til at læse input streamen med bruger vi her en scanner.
                det kunne lige så godt have været en Bufferreader
                (hvis forbindelsen der modtages fra lukker efter sig,
                ellers stopper den aldrig med at læse)
                 */
                Scanner in = new Scanner(input);

                //Når vi skriver til output streamen bruger vi her en PrintWriter.
                PrintWriter out = new PrintWriter(output, true);

                /*
                Lad os sige velkommen til der der har forbundet til serveren,
                så den ved der er hul igennem.
                 */

                out.println("Velkommen");

                /*
                Vi ønsker at have kontrol over hvornår forbindelsen skal lukkes
                fra denne side.
                Vi ønsker kun at lukke forbindelse, når brugeren skriver "luk ned"
                 */

                boolean done = false;
                while (!done && in.hasNextLine()) {

                    /*
                    Her starter scannerens arbejde. Hvis der ikke er nogle
                    linier, afventer den til der kommer en.
                     */

                    String stream = in.nextLine();
                    if (stream.equals("luk ned")) {

                        done = true;


                    } else {
                        //Når vi skriver, sender vi en linie med PrintWriter
                        out.println(stream);
                    }
                }
            } finally {

                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






    }





}
