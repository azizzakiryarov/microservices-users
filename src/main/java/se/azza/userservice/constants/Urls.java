package se.azza.userservice.constants;

public final class Urls {

    public static final String BASE_URL = "microservices-issues-statefulset-0.microservices-issues.microservices.svc.cluster.local:8869";

    public static final String GET_ISSUES_FOR_USER = BASE_URL + "/issues/getAllIssuesFor/";

    private Urls() {}
}
