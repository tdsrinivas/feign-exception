package com.srini

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.srini.controller.UserController
import com.srini.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@SpringBootTest(classes = FeignExceptionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeignExceptionTest extends Specification {
    private static WireMockServer userService = new WireMockServer(9999);

    @Autowired
    private UserController controller

    @Autowired
    private ObjectMapper mapper

    @Autowired
    private TestRestTemplate restTemplate;

    def setup() {
        userService.start()

        userService.resetMappings();
        userService.resetRequests();
    }

    def cleanupSpec() {
        userService.stop();
    }

    def "test when feign client responds with 200, service returns 200"() {
        setup:
        userService.stubFor(get(urlEqualTo("/users/guid1"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(mapper.writeValueAsString(new User(firstName: "Srinivas")))))

        when:
        ResponseEntity<User> response = restTemplate.getForEntity("/users/guid1", User.class);

        then:
        userService.verify(getRequestedFor(urlEqualTo("/users/guid1")));
        response.getStatusCode().value() == 200
    }

    def "test when feign client responds with 503, service returns 503"() {
        setup:
        userService.stubFor(get(urlEqualTo("/users/guid1"))
                .willReturn(aResponse()
                .withStatus(503)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(mapper.writeValueAsString(new User(firstName: "Srinivas")))))

        when:
        ResponseEntity<User> response = restTemplate.getForEntity("/users/guid1", User.class);

        then:
        response.getStatusCode().value() == 503
    }

    def "test when feign client is not available, service returns 503"() {
        setup:
        userService.stop()

        when:
        ResponseEntity<User> response = restTemplate.getForEntity("/users/guid1", User.class);

        then:
        response.getStatusCode().value() == 503
    }
}
