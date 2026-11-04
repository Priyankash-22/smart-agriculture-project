package com.example.smartagriculturebackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.JsonNode;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarketApiService {

    @Value("${market.api.url}")
    private String apiUrl;

    @Value("${market.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    private static final int PAGE_SIZE = 100;

    public JsonNode fetchPage(int offset) {

        URI uri = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("api-key", apiKey)
                .queryParam("format", "json")
                .queryParam("limit", PAGE_SIZE)
                .queryParam("offset", offset)
                .queryParam("filters[State]", "Karnataka")
                .queryParam("sort[Arrival_Date]", "desc")
                .build()
                .encode()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(JsonNode.class);
    }

    public List<JsonNode> fetchLatestDailyRecords() {

        List<JsonNode> latestRecords = new ArrayList<>();

        int offset = 0;
        String latestDate = null;

        while (true) {

            JsonNode response = fetchPage(offset);
            JsonNode records = response.get("records");

            if (records == null || !records.isArray() || records.isEmpty()) {
                break;
            }

            for (JsonNode record : records) {

                String recordDate =
                        record.get("Arrival_Date").asString();

                if (latestDate == null) {
                    latestDate = recordDate;
                }

                if (!latestDate.equals(recordDate)) {
                    return latestRecords;
                }

                latestRecords.add(record);
            }

            if (records.size() < PAGE_SIZE) {
                break;
            }

            offset += PAGE_SIZE;
        }

        return latestRecords;
    }
}