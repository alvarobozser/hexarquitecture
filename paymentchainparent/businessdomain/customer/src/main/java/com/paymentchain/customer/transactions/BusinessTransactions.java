package com.paymentchain.customer.transactions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProducts;
import com.paymentchain.customer.repository.CustomerRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Service
public class BusinessTransactions {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;

	/*
	 * private final WebClient.Builder webClientBuilder;
	 * 
	 * public CustomerController(WebClient.Builder webClientBuilder) {
	 * this.webClientBuilder=webClientBuilder; }
	 */

	HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(EpollChannelOption.TCP_KEEPIDLE, 300)
			.option(EpollChannelOption.TCP_KEEPINTVL, 60)
			.responseTimeout(Duration.ofSeconds(1))
			.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
					.addHandlerLast(new WriteTimeoutHandler(5, TimeUnit.SECONDS)));

	public Customer getByCode(String code) {
		Customer customer = customerRepository.findByCode(code);
		List<CustomerProducts> products = customer.getProducts();
		products.forEach(x -> {
			x.setProductName(getProductName(x.getId()));
		});
		List<?> transactions = getTransactions(customer.getIban());
		customer.setTransactions(transactions);

		return customer;
	}

	private String getProductName(Long id) {
		WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
				.baseUrl("http://BUSINESSDOMAIN-PRODUCTS/products")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		return build.method(HttpMethod.GET).uri("/" + id).retrieve().bodyToMono(JsonNode.class)
				.map(jsonNode -> jsonNode.get("name").asText()).block();
	}

	private List<?> getTransactions(String iban) {
		WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(httpClient))
				.baseUrl("http://BUSINESSDOMAIN-TRANSACTIONS/transactions")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		List<?> transactions = build.method(HttpMethod.GET)
				.uri(uriBuilder -> uriBuilder.path("/getAccount").queryParam("iban", iban).build()).retrieve()
				.bodyToFlux(Object.class).collectList().block();

		return transactions;
	}

}