package org.senolab.ccucli.model;

import com.akamai.edgegrid.signer.exceptions.RequestSigningException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.senolab.ccucli.service.OpenAPICallService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.stream.Stream;

public class Invalidate {

    private final String INVALIDATE_URL_STAG = "/ccu/v3/invalidate/url/staging";
    private final String INVALIDATE_URL_PROD = "/ccu/v3/invalidate/url/production";
    private final String INVALIDATE_CPCODE_STAG = "/ccu/v3/invalidate/cpcode/staging";
    private final String INVALIDATE_CPCODE_PROD = "/ccu/v3/invalidate/cpcode/production";
    private final String INVALIDATE_TAG_STAG = "/ccu/v3/invalidate/tag/staging";
    private final String INVALIDATE_TAG_PROD = "/ccu/v3/invalidate/tag/production";

    private OpenAPICallService openAPICallService;

    public Invalidate(String edgerc, String type, String network, String body) throws IOException, ParseException, org.json.simple.parser.ParseException {
        //Parse JSON body
        boolean isListFile;

        if(new File(body).isFile()) {
            isListFile = true;
        } else {
            isListFile = false;
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray listOfObjects = new JSONArray();
        JSONParser parser = new JSONParser();
        if (isListFile) {
            try {
                Object obj = parser.parse(new FileReader(body));
                jsonObject = (JSONObject) obj;
            } catch (org.json.simple.parser.ParseException e) {
                try (Stream<String> stream = Files.lines(Paths.get(body))) {
                    stream.forEach(x -> listOfObjects.add(x));
                }
                jsonObject.put("objects", listOfObjects);
            }
        } else {
            listOfObjects.add(body);
            jsonObject.put("objects", listOfObjects);
        }
        String requestBody = jsonObject.toJSONString();
        switch (type) {
            case "url":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_URL_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_URL_PROD, requestBody);
                }
                break;
            case "cpcode":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_CPCODE_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_CPCODE_PROD, requestBody);
                }
                break;
            case "tag":
                if(network.equalsIgnoreCase("staging")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_TAG_STAG, requestBody);
                } else if(network.equalsIgnoreCase("production")) {
                    openAPICallService = new OpenAPICallService(edgerc, INVALIDATE_TAG_PROD, requestBody);
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
