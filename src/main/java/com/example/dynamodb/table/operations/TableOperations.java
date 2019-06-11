package com.example.dynamodb.table.operations;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class TableOperations extends DynamoClientConfig{

    public void CreateTable(String tableName){

        //Caminho do arquivo de credenciais
        String pathJSON = "D:\\Workspaces\\IntelliJ\\ProjectTestDynamoDB\\src\\main\\java\\com\\example\\dynamodb\\table\\resources\\awsCreds.json";
        //Setando credenciais a partir de um arquivo JSON
        setCredetialsJSONFromFile(pathJSON);

        if (tableName != null && tableName != ""){

            System.out.format(
                    "Creating table \"%s\" with a simple primary key: \"Name\".\n",
                    tableName);

            CreateTableRequest request = new CreateTableRequest()
                    .withAttributeDefinitions(new AttributeDefinition(
                            "Name", ScalarAttributeType.S))
                    .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
                    .withProvisionedThroughput(new ProvisionedThroughput(
                            new Long(10), new Long(10)))
                    .withTableName(tableName);

            try {
                CreateTableResult result = client.createTable(request);
                System.out.println(result.getTableDescription().getTableName());
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
            System.out.println("Done!");

        }else{
            final String USAGE = "\n" +
                    "Usage:\n" +
                    "    CreateTable <table>\n\n" +
                    "Where:\n" +
                    "    table - the table to create.\n\n" +
                    "Example:\n" +
                    "    CreateTable HelloTable\n";

            System.out.println(USAGE);
        }
    }
}
