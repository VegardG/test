package no.kristiania.pgr200.database;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpServerTest {

  private static HttpServer server;

  @BeforeClass
  public static void startServer() throws IOException {
    server = new HttpServer(0);
    server.startServer();
  }

  @Test
  public void shouldHandleRequest() throws IOException {
    HttpReq request = new HttpReq("localhost", server.getPort(), "/echo?status=200");
    HttpRes response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  public void shouldEchoStatusCode() throws IOException {
    HttpReq request = new HttpReq("localhost", server.getPort(), "/echo?status=404");
    HttpRes response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(404);
  }

  @Test
  public void shouldEchoResponseHeaders() throws IOException {
    HttpReq request = new HttpReq("localhost", server.getPort(),
            "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
    HttpRes response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(307);
    assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
  }

  @Test
  public void shouldEchoResponseBody() throws IOException {
    HttpReq request = new HttpReq("localhost", server.getPort(),
            "/echo?body=Hello+Kristiania!");
    HttpRes response = request.execute();

    assertThat(response.getStatusCode()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
  }
}
