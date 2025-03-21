package top.cheesetree.btx.framework.web.http;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.cert.X509Certificate;

/**
 * @Author: van
 * @Date: 2021/8/27 10:05
 * @Description: TODO
 */
@Slf4j
public class CustomSSLSocketFactory extends SSLSocketFactory {
    private SSLSocketFactory delegate;

    public CustomSSLSocketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            this.delegate = sslContext.getSocketFactory();
        } catch (Exception e) {
            log.error("init  error", e);
        }

    }

    public CustomSSLSocketFactory(SSLSocketFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(final Socket socket, final String host, final int port, final boolean autoClose)
            throws IOException {
        final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port) throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort)
            throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port) throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(final InetAddress host, final int port, final InetAddress localAddress,
                               final int localPort) throws IOException {
        final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
        return overrideProtocol(underlyingSocket);
    }

    private Socket overrideProtocol(final Socket socket) {
        if (!(socket instanceof SSLSocket)) {
            throw new RuntimeException("An instance of SSLSocket is expected");
        }
        ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"});
        return socket;
    }
}
