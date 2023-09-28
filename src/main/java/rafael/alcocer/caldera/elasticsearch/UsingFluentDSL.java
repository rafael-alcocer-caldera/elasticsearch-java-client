/**
 * Copyright [2023] [RAFAEL ALCOCER CALDERA]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

public class UsingFluentDSL {

    public static void main(String[] args) throws ElasticsearchException, IOException {
        indexEmployee1001();
        System.out.println("--------------------------------------------------------");
        indexEmployee2002();
        System.exit(0);
    }

    public static void indexEmployee1001() throws ElasticsearchException, IOException {
        // Employee: int id, String name, int deptid, double salary, String status
        Employee employee = new Employee(1001, "Employee One", 33, 10000.00, "active");

        IndexResponse response = createClient()
                .index(i -> i
                        .index("employee")
                        .id("" + employee.getId())
                        .document(employee));

        System.out.println("##### " + response);
    }

    public static void indexEmployee2002() throws ElasticsearchException, IOException {
        // Employee: int id, String name, int deptid, double salary, String status
        Employee employee = new Employee(2002, "Employee One", 66, 13000.00, "inactive");

        IndexRequest<Employee> request = IndexRequest
                .of(i -> i
                        .index("employee")
                        .id("" + employee.getId())
                        .document(employee));

        IndexResponse response = createClient().index(request);

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
