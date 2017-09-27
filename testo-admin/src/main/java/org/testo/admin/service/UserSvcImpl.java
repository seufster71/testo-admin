package org.testo.admin.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.activemq.broker.SslContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testo.admin.repository.UserDao;
import org.testo.core.service.ServiceProcessor;
import org.testo.core.utils.Request;
import org.testo.core.utils.Response;

@Service("UserSvc")
public class UserSvcImpl implements ServiceProcessor, UserSvc {

	@Autowired
	protected UserDao userDao;
	
	@Override
	public void process(Request request, Response response) {
		String action = (String) request.getParams().get("action");
		switch (action) {
			case "LIST":
				this.list(request, response);
				break;
			case "ITEM":
				this.item(request, response);
				break;
			case "SAVE":
				this.save(request, response);
				break;
		}
	}

	@Override
	public void item(Request request, Response response){
		try {
			userDao.item(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void list(Request request, Response response) {
		try {
			userDao.list(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void save(Request request, Response response) {
		try {
			userDao.save(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void hot(Request request, Response response) {
		try {
			String keyStoreFileName = "";
			String keyStorePassword = "";
			String alias = "";
			String trustStoreFileName = "";
			String trustStorePassword = "";
			KeyManager[] keyManagers = createKeyManagers(keyStoreFileName, keyStorePassword, alias);
			TrustManager[] trustManagers = createTrustManagers(trustStoreFileName,trustStorePassword);
			
			SSLSocketFactory sslFactory = initAll(keyManagers, trustManagers);
			
			HttpsURLConnection.setDefaultSSLSocketFactory(sslFactory);
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static SSLSocketFactory initAll(KeyManager[] keyManagers, TrustManager[] trustManagers) throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagers, trustManagers, null);
		SSLSocketFactory socketFactory = context.getSocketFactory();
		return socketFactory;
	}
	
	private static KeyManager[] createKeyManagers(String keyStoreFileName, String keyStorePassword, String alias)
	        throws Exception, CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
	        //create Inputstream to keystore file
	        java.io.InputStream inputStream = new java.io.FileInputStream(keyStoreFileName);
	        //create keystore object, load it with keystorefile data
	        KeyStore keyStore = KeyStore.getInstance("JKS");
	        keyStore.load(inputStream, keyStorePassword == null ? null : keyStorePassword.toCharArray());

	        KeyManager[] managers;
	        if (alias != null) {
	            managers = new KeyManager[] { new ClientCert().new AliasKeyManager(keyStore, alias, keyStorePassword)};
	        } else {
	            //create keymanager factory and load the keystore object in it 
	            KeyManagerFactory keyManagerFactory =
	                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	            keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
	            managers = keyManagerFactory.getKeyManagers();
	        }
	        //return 
	        return managers;
	    }
	
	private static TrustManager[] createTrustManagers(String trustStoreFileName, String trustStorePassword)
	        throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
	        //create Inputstream to truststore file
	        java.io.InputStream inputStream = new java.io.FileInputStream(trustStoreFileName);
	        //create keystore object, load it with truststorefile data
	        KeyStore trustStore = KeyStore.getInstance("JKS");
	        trustStore.load(inputStream, trustStorePassword == null ? null : trustStorePassword.toCharArray());

	        //create trustmanager factory and load the keystore object in it 
	        TrustManagerFactory trustManagerFactory =
	            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        trustManagerFactory.init(trustStore);
	        //return 
	        return trustManagerFactory.getTrustManagers();
	    }
	
	private class AliasKeyManager implements X509KeyManager {

        private KeyStore _ks;
        private String _alias;
        private String _password;

        public AliasKeyManager(KeyStore ks, String alias, String password) {
            _ks = ks;
            _alias = alias;
            _password = password;
        }

        @Override
        public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
            return _alias;
        }

        @Override
        public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public X509Certificate[] getCertificateChain(String alias) {
            try {
                java.security.cert.Certificate[] certificates = this._ks.getCertificateChain(alias);
                if(certificates == null){throw new FileNotFoundException("no certificate found for alias:" + alias);}
                X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
                System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
                return x509Certificates;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String[] getClientAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }

        @Override
        public PrivateKey getPrivateKey(String alias) {
            try {
                return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String[] getServerAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }

    }
}

