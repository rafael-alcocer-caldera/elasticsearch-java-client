package rafael.alcocer.caldera.elasticsearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

public class OldElasticsearchJavaClient {

    public static void main(String[] args) {
        RestHighLevelClient client = createClient();
        
        IndexRequest request1 = createIndexMap();
        IndexResponse response1 = createIndexResponse(client, request1);
        System.out.println("index1: " + response1.getIndex());
        System.out.println("id1: " + response1.getId());
        
        System.out.println("-------------------------");
        
        IndexRequest request2 = createIndexString();
        IndexResponse response2 = createIndexResponse(client, request2);
        System.out.println("index2: " + response2.getIndex());
        System.out.println("id2: " + response2.getId());
        
        System.out.println("-------------------------");

        IndexRequest request3 = creatIndexXContentBuilder();
        IndexResponse response3 = createIndexResponse(client, request3);
        System.out.println("index3: " + response3.getIndex());
        System.out.println("id3: " + response3.getId());

        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestHighLevelClient createClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")));

        return client;
    }

    public static IndexRequest createIndexString() {
        IndexRequest request = new IndexRequest("myindex2").id("1");
        request.id("1");

        String json = "{" + "\"nombre\": \"Abi\"," + "\"edad\": 23," + "\"activo\": true," + "\"fecha\": \"10-12-1996\""
                + "}";

        return request.source(json, XContentType.JSON);
    }

    public static IndexRequest createIndexMap() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("nombre", "Abi");
        jsonMap.put("edad", 23);
        jsonMap.put("activo", true);
        jsonMap.put("fecha", "10-12-1996");

        return new IndexRequest("myindex").id("1").source(jsonMap);
    }

    public static IndexRequest creatIndexXContentBuilder() {
        XContentBuilder builder = null;

        try {

            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.field("nombre", "abi");
            builder.field("edad", 23);
            builder.field("activo", true);
            builder.field("fecha", "10-12-1996");
            builder.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new IndexRequest("myindex3").id("1").source(builder);
    }

    public static IndexResponse createIndexResponse(RestHighLevelClient client, IndexRequest request) {
        IndexResponse indexResponse = null;

        try {
            indexResponse = client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return indexResponse;
    }

    public static GetRequest createRequest() {
        GetRequest request = new GetRequest("myindex", "1");

        System.out.println(request);
        System.out.println("index: " + request.index());
        System.out.println("id: " + request.id());
        System.out.println("version: " + request.version());

        return request;
    }

    public static GetResponse createResponse(RestHighLevelClient client, GetRequest request) {
        GetResponse response = null;

        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
