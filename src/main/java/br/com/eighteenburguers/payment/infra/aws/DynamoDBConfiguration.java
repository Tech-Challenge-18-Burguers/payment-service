package br.com.eighteenburguers.payment.infra.aws;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

@Configuration
public class DynamoDBConfiguration {

	@Value("${aws.region}")
	private String region;

	@Value("${spring.cloud.local:false}")
	private boolean local;

	@Bean
	DynamoDbClient dynamoDbClient() {
		DynamoDbClientBuilder builder = DynamoDbClient.builder().region(Region.of(region));

		if (local) {
			builder.endpointOverride(URI.create("http://localhost:4566"));
		}
		return builder.build();
	}
}
