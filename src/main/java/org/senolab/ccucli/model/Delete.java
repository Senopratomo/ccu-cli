package org.senolab.ccucli.model;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.senolab.ccucli.service.OpenAPICallService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.stream.Stream;

public class Delete {

    private final String DELETE_URL_STAG = "/ccu/v3/delete/url/staging";
    private final String DELETE_URL_PROD = "/ccu/v3/delete/url/production";
    private final String DELETE_CPCODE_STAG = "/ccu/v3/delete/cpcode/staging";
    private final String DELETE_CPCODE_PROD = "/ccu/v3/delete/cpcode/production";
    private final String DELETE_TAG_STAG = "/ccu/v3/delete/tag/staging";
    private final String DELETE_TAG_PROD = "/ccu/v3/delete/tag/production";

    private OpenAPICallService openAPICallService;

    public Delete(String edgerc, String type, String network, String body) throws IOException, ParseException {
        //Parse JSON body
        boolean isListFile;

        if(new File(body).isFile()) {
            isListFile = true;
        } else {
            isListFile = false;
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray listOfObjects = new JSONArray();
        if (isListFile) {
            try (Stream<String> stream = Files.lines(Paths.get(body))) {
                stream.forEach(x -> listOfObjects.add(x));
            }
            jsonObject.put("objects", listOfObjects);
        } else {
            listOfObjects.add(body);
            jsonObject.put("objects", listOfObjects);
        }
        String requestBody = jsonObject.toJSONString();
        switch (type) {
            case "url":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_URL_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_URL_PROD, requestBody);
                }
                break;
            case "cpcode":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_CPCODE_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_CPCODE_PROD, requestBody);
                }
                break;
            case "tag":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_TAG_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, DELETE_TAG_PROD, requestBody);
                }
                break;
            default:
                System.out.println("Invalid options at arg[2]. Please specify the correct option!");
        }


    }

    public void execute() throws IOException, RequestSigningException {
        openAPICallService.execute();
    }

}
