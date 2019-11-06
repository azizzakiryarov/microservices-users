package se.azza.userservice.resttemplates;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import se.azza.userservice.model.Issue;

public class RestTemplates {

	public RestTemplates() {
		super();
	}

	public static List<Issue> getIssuesForUser(RestTemplate restTemplate, Long userId) {
		return Arrays.asList(restTemplate.getForObject("http://microservices-issues/issues/getAllIssuesFor/" + userId,
				Issue[].class));
	}
}