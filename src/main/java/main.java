import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Scanner;
import java.net.URL;

public class main {

    private static String[] filter = null;//A string that each position is a Paragraph.


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
    //Function to Count lines on Document
    public static int LineCount(Document Input) { ;
        return (Input.html().split("\n")).length;
    }
    //Function to Count <p> on Document
    public static int paraCount(Document Input) {
        int num = 0;
        filter = Input.html().split("<p>");
        num = (filter.length)/2;
        return num;
    }
    //Function to Count <img> on Document
    public static int imgCount() {
        String[] filter2 = null;
        if(filter.length > 1) {
            for (int i = 0; i < filter.length; i++) {
                filter2 = filter[i].split("img");
            }
            return filter2.length;
        }
        else {
            return 0;
        }
    }
    //Function to Count FORM GET
    public static int formGET(Document Input) {
        int num = 0;
        num = Input.getElementsByTag("form").attr("method", "GET").toArray().length;
        return num;
    }
    //Function to Count FORM POST
    public static int formPOST(Document Input) {
        int num = 0;
        num = Input.getElementsByTag("form").attr("method", "POST").toArray().length;
        return num;
    }
    //Function form Input
    public static String formInputGET(Document In) {
        return In.getElementsByTag("form").attr("method", "GET").
                select("input").toString();
    }
    public static String formInputPOST(Document In) {
        return In.getElementsByTag("form").attr("method", "POST").
                select("input").toString();
    }

    public static void main (String[] args) throws IOException {
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
        //URL EXAMPLES
        //"https://www.example.org" | "https://www.google.com <- Img but 0 paragraph |
        // https://www.jotunheim-ragnarok.net/?module=account&action=create <- FORMS

        //GET Document HTML to URL
        Document docHTML = Jsoup.connect(url).get();
        //Print Num of Line
        System.out.println("El Documento tiene: ["+LineCount(docHTML)+"] Lineas de codigo.");
        //Print Num of <p>
        System.out.println("El Documento tiene: [" + paraCount(docHTML)+ "] Parrafos <p>");
        //Print Num of <img>
        System.out.println("El Documento tiene: [" + imgCount()+"] Imagenes dentro de los Parrafos");
        //Print Num of form method: GET & POST
        System.out.println("El Documento tiene: [" +formGET(docHTML)+ "] Formulario que implementan el metodo GET y [" +
               formPOST(docHTML)+ "] que implementan el metodo POST");
        //Print form Input and type of input
        System.out.println("\nEn los form que implementan [GET] se tiene los siguientes input: \n[" +formInputGET(docHTML)+ "]\n");
        System.out.println("\nEn los form que implementan [POST] se tiene los siguientes input: \n[" +formInputPOST(docHTML)+ "]\n");
    }


}

