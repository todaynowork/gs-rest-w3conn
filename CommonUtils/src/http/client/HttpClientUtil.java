package http.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
  
  
public class HttpClientUtil {  
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);  
    private static int SocketTimeout = 3000;//3秒  
    private static int ConnectTimeout = 3000;//3秒  
    private static Boolean SetTimeOut = true;  
  
    private static CloseableHttpClient getHttpClient() {  
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();  
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();  
        registryBuilder.register("http", plainSF);  
//指定信任密钥存储对象和连接套接字工厂  
        try {  
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            //信任任何链接  
            TrustStrategy anyTrustStrategy = new TrustStrategy() {  
                @Override  
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {  
                    return true;  
                }  
            };  
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore,
                    		anyTrustStrategy)
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//            CloseableHttpClient httpclient = HttpClients.custom()
//                    .setSSLSocketFactory(sslsf)
//                    .build();
//            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();  
//            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
            registryBuilder.register("https", sslsf);  
        } catch (KeyStoreException e) {  
            throw new RuntimeException(e);  
        } catch (KeyManagementException e) {  
            throw new RuntimeException(e);  
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e);  
        }  
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();  
        //设置连接管理器  
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);  
//      connManager.setDefaultConnectionConfig(connConfig);  
//      connManager.setDefaultSocketConfig(socketConfig);  
        //构建客户端  
        return HttpClientBuilder.create().setConnectionManager(connManager).build();  
    }  
  
    /** 
     * get 
     * 
     * @param url     请求的url 
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null 
     * @return 
     * @throws IOException 
     */  
    public static String get(String url, Map<String, String> queries) throws IOException {  
        String responseBody = "";  
        //CloseableHttpClient httpClient=HttpClients.createDefault();  
        //支持https  
        CloseableHttpClient httpClient = getHttpClient();  
  
        StringBuilder sb = new StringBuilder(url);  
  
        if (queries != null && queries.keySet().size() > 0) {  
            boolean firstFlag = true;  
            Iterator iterator = queries.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();  
                if (firstFlag) {  
                    sb.append("?" + (String) entry.getKey() + "=" + (String) entry.getValue());  
                    firstFlag = false;  
                } else {  
                    sb.append("&" + (String) entry.getKey() + "=" + (String) entry.getValue());  
                }  
            }  
        }  
  
        HttpGet httpGet = new HttpGet(sb.toString());  
        if (SetTimeOut) {  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(SocketTimeout)  
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间  
            httpGet.setConfig(requestConfig);  
        }  
        try {  
        	logger.info("Executing request " + httpGet.getRequestLine());  
            //请求数据  
            CloseableHttpResponse response = httpClient.execute(httpGet);  
            logger.info(response.getStatusLine().toString());  
            int status = response.getStatusLine().getStatusCode();  
            if (status == HttpStatus.SC_OK) {  
                HttpEntity entity = response.getEntity();  
                // do something useful with the response body  
                // and ensure it is fully consumed  
                responseBody = EntityUtils.toString(entity);  
                //EntityUtils.consume(entity);  
            } else {  
            	logger.info("http return status error:" + status);  
                throw new ClientProtocolException("Unexpected response status: " + status);  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            httpClient.close();  
        }  
        return responseBody;  
    }  
  
    /** post 
     * @param url     请求的url 
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null 
     * @param params  post form 提交的参数 
     * @return 
     * @throws IOException 
     */  
    public static String post(String url, Map<String, String> queries, Map<String, String> params) throws IOException {  
        String responseBody = "";  
        //CloseableHttpClient httpClient = HttpClients.createDefault();  
        //支持https  
        CloseableHttpClient httpClient = getHttpClient();  
        
        
        HttpHost targetHost = new HttpHost("gateway.watsonplatform.net", 443, "https");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("bf01f54b-cef2-4ecf-80af-f853f8d62222", "VcL70SYDiPFc"));

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);
  
        StringBuilder sb = new StringBuilder(url);  
  
        if (queries != null && queries.keySet().size() > 0) {  
            boolean firstFlag = true;  
            Iterator iterator = queries.entrySet().iterator();  
            while (iterator.hasNext()) {  
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();  
                if (firstFlag) {  
                    sb.append("?" + (String) entry.getKey() + "=" + (String) entry.getValue());  
                    firstFlag = false;  
                } else {  
                    sb.append("&" + (String) entry.getKey() + "=" + (String) entry.getValue());  
                }  
            }  
        }  
  
        //指定url,和http方式  
        HttpPost httpPost = new HttpPost(sb.toString());  
        if (SetTimeOut) {  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(SocketTimeout)  
                    .setConnectTimeout(ConnectTimeout).build();//设置请求和传输超时时间  
            httpPost.setConfig(requestConfig);  
        }  
//        //添加参数  
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
//        if (params != null && params.keySet().size() > 0) {  
//            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();  
//            while (iterator.hasNext()) {  
//                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();  
//                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));  
//            }  
//        }
        
        StringEntity myEntity = new StringEntity("important message", 
        		   ContentType.create("text/plain", "UTF-8"));
        httpPost.setEntity(myEntity);
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));  
        //请求数据  
        CloseableHttpResponse response = httpClient.execute(targetHost,httpPost,context);  
        try {  
        	logger.info(response.getStatusLine().toString());  
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = response.getEntity();  
                // do something useful with the response body  
                // and ensure it is fully consumed  
                responseBody = EntityUtils.toString(entity);  
                //EntityUtils.consume(entity);  
            } else {  
            	logger.info("http return status error:" + response.getStatusLine().getStatusCode());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            response.close();  
        }  
        return responseBody;  
    }  
}  