package com.example.dynamodb.table.operations;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class DynamoClientConfig {

    //Credenciais AWS
    AWSCredentialsProvider awsCreds;

    //Caminho do arquivo de credenciais
    String pathJSON = "D:\\Workspaces\\IntelliJ\\ProjectTestDynamoDB\\src\\main\\java\\com\\example\\dynamodb\\table\\resources\\awsCreds.json";

    //Construção do client
    final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(setCredetialsJSONFromFile(pathJSON))   //Setando credenciais a partir de arquivo JSON
            .withRegion(Regions.SA_EAST_1)
            .withClientConfiguration(clientConfig())
            .build();

    // Método para configurações do client
    public ClientConfiguration clientConfig(){

        // Variável com as configurações do cliente
        ClientConfiguration cli_config = new ClientConfiguration();

        // Setando configurações de proxy
        cli_config.setProxyHost("proxylatam.indra.es");
        cli_config.setProxyPort(8080);

        return cli_config;
    }

    //Método para usar credenciais de um arquivo JSON
    public AWSCredentialsProvider setCredetialsJSONFromFile(String path) {

        //JSON parser object to parse read file
        JSONParser parser = new JSONParser();
        try {

            //Read JSON file
            Object object = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) object;    //Creating JSON object
            String id = (String) jsonObject.get("id");             //Getting credential id
            String password = (String) jsonObject.get("password"); //Getting credential password

            //Setando valores das credenciais
            this.awsCreds = new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(id, password)
            );

        } catch (ParseException | IOException e) {
            System.out.println(e);
        }

        return this.awsCreds;
    }

}
