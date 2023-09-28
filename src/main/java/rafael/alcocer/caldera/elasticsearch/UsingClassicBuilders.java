package rafael.alcocer.caldera.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class UsingClassicBuilders {

    public static void main(String[] args) throws ElasticsearchException, IOException {
        indexEmployee();
        System.exit(0);
    }

    public static void indexEmployee() throws ElasticsearchException, IOException {
        // Employee: int id, String name, int deptid, double salary, String status
        Employee employee = new Employee(5000, "Employee One", 44, 55000.00, "active");

        IndexRequest.Builder<Employee> indexReqBuilder = new IndexRequest.Builder<>();
        indexReqBuilder.index("employee");
        indexReqBuilder.id("" + employee.getId());
        indexReqBuilder.document(employee);

        IndexResponse response = createClient().index(indexReqBuilder.build());

        System.out.println("##### " + response);
    }

    public static ElasticsearchClient createClient() {
        // Create the low-level client
        RestClient restClient = RestClient.builder(HttpHost.create("localhost:9200")).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        return esClient;
    }
}
