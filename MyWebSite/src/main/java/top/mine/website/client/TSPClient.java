package top.mine.website.client;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.PushPromiseHandler;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

public class TSPClient extends HttpClient {

    @Override
    public Optional<CookieHandler> cookieHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Duration> connectTimeout() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Redirect followRedirects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ProxySelector> proxy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SSLContext sslContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SSLParameters sslParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Authenticator> authenticator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Version version() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Executor> executor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
            throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, BodyHandler<T> responseBodyHandler,
            PushPromiseHandler<T> pushPromiseHandler) {
        // TODO Auto-generated method stub
        return null;
    }

}