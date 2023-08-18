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
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

public class NewElasticsearchJavaClient {

    public static void main(String[] args) throws ElasticsearchException, IOException {
        ElasticsearchClient esClient = createClient();
        IndexResponse indexResponse = createIndexResponse(esClient, "myindex", "1");
        System.out.println(indexResponse);

        System.out.println("-----------------------------------------------------");

        getResponse(esClient, "myindex", "1");

        System.exit(0);
    }

    public static ElasticsearchClient createClient() {
        // Create the low-level client
        RestClient restClient = RestClient.builder(HttpHost.create("localhost:9200"))
                // .setDefaultHeaders(new Header[]{
                // new BasicHeader("Authorization", "ApiKey " + apiKey)
                // })
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        return esClient;
    }

    public static IndexResponse createIndexResponse(ElasticsearchClient esClient, String index, String id)
            throws ElasticsearchException, IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("nombre", "Abi");
        jsonMap.put("edad", 23);
        jsonMap.put("activo", true);
        jsonMap.put("fecha", "10-12-1996");

        IndexResponse response = esClient.index(i -> i.index(index).id(id).document(jsonMap));

        return response;
    }

    public static void getResponse(ElasticsearchClient esClient, String index, String id)
            throws ElasticsearchException, IOException {
        GetResponse<Object> response = esClient.get(g -> g.index(index).id(id), Object.class);

        if (response.found()) {
            Object o = response.source();
            System.out.println(o);
        } else {
            System.out.println("Object not found");
        }
    }
}
