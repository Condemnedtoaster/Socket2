package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static void main(String[] args) {

        try {
            Socket s = new Socket("127.0.0.1", 8001);


            while (true) {



                /*
                Vi har behov for at kommunikere med serveren. Vi opretter derfor en input
                og en output stream, og binder hver idær til Socket'ens
                input og output stream.
                Sockets kører i full-dublex og der er dermed tovejs kommunikation
                til rådighed.
                 */
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                /*
                Til at læse input streamen med bruger vi her en scanner.
                Det kunne lige så godt have været en Bufferreader
                (hvis forbindelsen der modrages fra lukker efter sig,
                ellers stopper den aldrig med at læse)
                 */
                Scanner in = new Scanner(input);

                // Når vi skriver til output streamen bruger vi heren printwriter.
                PrintWriter out = new PrintWriter(output, true);

                /*
                Når man arbejder med netværk, er det vigtigt at klient og server har en protokol
                som klart fortæller hvordan kommunikationen mellem de to skal foregå

                i dette tilfælde ved vi, at serveren sender en velkomst
                og vi ved, at den derefter retunerer det som den modtager.
                Vi skal derfor overholde den protokol, ved først at modtage velkomstbeskeden
                Derefter overholder serveren request/response designmønster
                 */

                //Modtag Velkomst
                String Welcome = in.nextLine();
                System.out.println("welcome");

                //nu kan vi så modtage og sende respektivt
                out.println("Første besked");
                // Vi udskriver nu bare direkte
                System.out.println(in.nextLine());

                out.println("anden besked");
                // Vi udskriver nu bare direkte
                System.out.println(in.nextLine());


                out.println("tredie besked");
                // Vi udskriver nu bare direkte
                System.out.println(in.nextLine());

                //denne gang lukker vi selv forbindelsen fordi vi kan
                //vi kunne også ha bruge "luk ned"
                s.close();
                System.out.print("Forbindelsen lukket.");
            }
        } catch (IOException ex) {
            //we ignore this
        }
    }
}
















