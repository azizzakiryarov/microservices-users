package se.azza.userservice.resttemplates;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.web.client.RestTemplate;

import se.azza.userservice.model.Issue;

import static se.azza.userservice.constants.Urls.GET_ISSUES_FOR_USER;

public class RestTemplates {

	public RestTemplates() {
		super();
	}

	public static List<Issue> getIssuesForUser(RestTemplate restTemplate, Long userId) {
		return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject(GET_ISSUES_FOR_USER + userId, Issue[].class)));
	}
}