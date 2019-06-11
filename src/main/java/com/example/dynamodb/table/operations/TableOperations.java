package com.example.dynamodb.table.operations;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.model.*;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

public class TableOperations extends DynamoClientConfig{

    public void CreateTable(String tableName){

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

    public void DeleteTable(String tableName){
        final String USAGE = "\n" +
                "Usage:\n" +
                "    DeleteTable <table>\n\n" +
                "Where:\n" +
                "    table - the table to delete.\n\n" +
                "Example:\n" +
                "    DeleteTable Greetings\n\n" +
                "**Warning** This program will actually delete the table\n" +
                "            that you specify!\n";

        System.out.format("Deleting table %s...\n", tableName);

        try {
            DeleteTableResult result = client.deleteTable(tableName);
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        // snippet-end:[dynamodb.java2.delete_table.main]
        System.out.println("Done!");
    }
}
