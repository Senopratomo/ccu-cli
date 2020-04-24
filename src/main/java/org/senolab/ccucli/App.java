package org.senolab.ccucli;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import com.google.api.client.http.HttpResponseException;
import org.senolab.ccucli.model.Delete;
import org.senolab.ccucli.model.Invalidate;

import java.io.IOException;
import java.text.ParseException;

public class App {

    public static void main(String[] args) {
        try {
            if(args.length == 5) {
                switch(args[1]) {
                    case "delete":
                        Delete delete = new Delete(args[0], args[2], args[3], args[4]);
                        delete.execute();
                        break;
                    case "invalidate":
                        Invalidate invalidate = new Invalidate(args[0], args[2], args[3], args[4]);
                        invalidate.execute();
                        break;
                    default:
                        System.out.println("Invalid options in args[1]. Please specify the correct option");
                }
            } else {
                printInstructions();
            }
        } catch (ParseException e) {
            System.out.println("Something wrong when parsing JSON body to the request. Details:");
            e.printStackTrace();
        } catch (HttpResponseException e) {
            System.out.println("Response code: "+e.getStatusCode());
            System.out.println("Response headers: \n"+e.getHeaders());
            System.out.println("Response body: \n"+e.getContent());
        } catch (IOException e) {
            System.out.println("Something wrong during I/O process. Details:");
            e.printStackTrace();
        } catch (RequestSigningException e) {
            System.out.println("Something wrong during authentication process. Details:");
            e.printStackTrace();
        }


    }

    private static void printInstructions() {
        System.out.println("CCU CLI takes 5 arguments separated by single space. These arguments are: \n" +
                "args[0] is location of .edgerc file. " +
                "This file contain Akamai API client credentials (client token, \n" +
                "access token, secret, host, and max body size) which necessary for EdgeGrid lib \n" +
                "sample: \n" +
                "host = https://akab-xxxxx.luna.akamaiapis.net \n" +
                "client_token = akab-xxxxx \n" +
                "client_secret = xxxxx \n" +
                "access_token = xxxx \n" +
                "\n" +
                "args[1] is type of purge - options are: 'delete' or 'invalidate'\n" +
                "args[2] is object to be purge - options are: 'url', 'cpcode', 'tag'\n" +
                "args[3] is target network - options are: 'staging' or 'production'\n" +
                "args[4] is the object to purge - options are:\n" +
                "1) for 'url' object: either single URL surrounded by double-quotes (\") or full path to a file containing list of URL (one URL per line)\n" +
                "2) for 'cpcode' object: either single or multiple cpcode separated by comma or full path to a file containing list of cpcode (one cpcode per line)\n" +
                "3) for 'tag' object: either single cache tag surrounded by double-quotes(\") or full path to a file containing list of cpcode (one cpcode per line)\n" +
                "\n" +
                "Example:\n" +
                "1) java -jar ccu.jar delete cpcode 12345\n" +
                "2) java -jar ccu.jar delete url \"https:\\\\www.example.com\\index.html\" \n" +
                "3) java -jar ccu.jar delete url /home/user/listof_URL.txt \n" +
                "   where listof_URL.txt contain:\n" +
                "   https:\\\\www.example.com\\index.html \n" +
                "   https:\\\\www.example.com\\style.css \n" +
                "   https:\\\\www.example.com\\app.js \n");
    }
}
