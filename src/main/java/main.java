import java.util.Scanner;
import java.net.URL;

public class main {

    //Function to Validate the URL
    public static boolean URLisCorrect(String in){
        try{
            new URL(in).toURI();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    //Function to Read console
    public static String ReadURL(){
        System.out.println("Introduzca una URL: ");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    public static void main (String[] args) {
        String url = ReadURL();
        boolean key = false;
        do{
            if(URLisCorrect(url))
            {
                System.out.println("Haz introducido: "+url);
                key = true;
            }
            else {
                System.out.println("La siguiente URL: "+url+ " No es valida.");
                url = ReadURL();
            }
        } while (!key);


    }


}

