import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.net.URL;

public class main {

    private static Elements filterParagraph = null;//A array that each position is a Paragraph.


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
    public static int LineCount(String UrlInput) throws IOException {
        int Lines = 0;
        Connection.Response Conection = Jsoup.connect(UrlInput).execute();
        Lines = Conection.body().split("\n").length;
        return Lines;
    }
    //Function to Count <p> on Document
    public static int paraCount(Document Input) {
       try {
           return (filterParagraph = Input.getElementsByTag("p")).size();
       }catch (Exception e){
           return 0;
       }
    }
    //Function to Count <img> on Document
    public static int imgCount() {
        int NumImg = 0;
        if(filterParagraph.size() > 1) {
            for (Element s : filterParagraph) {
                NumImg += (s.getElementsByTag("img")).size();
            }
            return NumImg;
        }
        else {
            return 0;
        }
    }
    //Function to Count FORM GET
    public static int formGET(Document Input) {
        return Input.getElementsByAttributeValue("method", "get").size();
    }
    //Function to Count FORM POST
    public static int formPOST(Document Input) {
        return Input.getElementsByAttributeValue("method", "post").size();
    }
    //Functions form Input
    public static String formInputGET(Document In) {
        Elements forms = In.getElementsByAttributeValue("method", "get");
        String inputs = "";
        for (Element f: forms){
            inputs.concat(f.select("input").toString());
        }
        return inputs;
    }
    public static String formInputPOST(Document In) {
        Elements forms = In.getElementsByAttributeValue("method", "post");
        String inputs = "";
        for (Element f: forms){
            inputs = " "+ f.select("input").toString();
        }
        return inputs;
    }

    //Function of set Request to server
    public static String SendPOST(Document Input) throws IOException {
        Elements Forms = Input.getElementsByTag("form");
        String send = "";
        for (Element form: Forms){
            if (form.getElementsByAttributeValue("method", "post").hasAttr("action")){
                Connection.Response Request = ((FormElement) form)
                        .submit().data("asignatura", "Practica#1").header("matricula", "20160415").execute();
                send.concat(" | " + Request.body() + " | ");
            }
        }
        return send;
    }
        //CONTROL MAIN
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
        System.out.println("El Documento tiene: ["+LineCount(url)+"] Lineas de codigo.");
        //Print Num of <p>
        System.out.println("El Documento tiene: [" + paraCount(docHTML)+ "] Parrafos <p>");
        //Print Num of <img>
        System.out.println("El Documento tiene: [" + imgCount()+"] Imagenes dentro de los Parrafos");
        //Print Num of form method: GET & POST
        System.out.println("El Documento tiene: [" +formGET(docHTML)+ "] Formulario que implementan el metodo GET y [" +
               formPOST(docHTML)+ "] que implementan el metodo POST");
        //Print form Input and type of input
        System.out.println("\nEn los form que implementan [GET] se tiene los siguientes input: \n["
                +formInputGET(docHTML)+ "]\n");
        System.out.println("\nEn los form que implementan [POST] se tiene los siguientes input: \n["
                +formInputPOST(docHTML)+ "]\n");
        //Print message sent
        System.out.println("Al servidor se le ha enviado la siguiente petición: " + SendPOST(docHTML));

    }


}

