package persistence.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import persistence.LyricsDAO;
import persistence.exceptions.PersistenceException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

/**
 * DAO class to obtain Lyrics from an external API.
 *
 * @author Group 6
 * @version 1.0
 */
public class APILyricsDAO implements LyricsDAO {

    /**
     * SSL Socket Factory to establish a secure connection to the API.
     */
    private SSLSocketFactory sslSocketFactory;

    /**
     * Constructor method that generates the SSL Socket Factory to establish a secure connection to the API.
     *
     * @throws PersistenceException if there was an error in the secure connection process.
     */
    public APILyricsDAO() throws PersistenceException {
        generateSSLSocketFactory();
    }

    /**
     * Method that fetches the lyrics of a given song.
     *
     * @param artist artist of the song.
     * @param title title of the song.
     * @return lyrics of the song, null if not found.
     * @throws PersistenceException if there was an error in the persistence layer.
     */
    @Override
    public String fetchLyrics(String artist, String title) throws PersistenceException {

        try {
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/lyrics/%s/%s"
                    .formatted(
                            artist.replaceAll(" ", "%20"),
                            title.replaceAll(" ", "%20")
                    )
            );
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslSocketFactory);

            switch (connection.getResponseCode()) {
                case 200 -> {
                }
                case 404 -> {
                    return null;
                }
                default -> throw new IOException(connection.getResponseMessage());
            }

            StringBuilder response = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            JsonObject responseObj = JsonParser.parseString(response.toString()).getAsJsonObject();
            return responseObj.get("lyrics").getAsString();

        } catch (IOException e) {
            throw new PersistenceException("An error occurred while fetching the lyrics", e);
        }
    }

    /**
     * Method that marks a certificate as trusted to establish a secure connection to the API.
     *
     * @throws PersistenceException if there was an error reading or processing the certificate.
     */
    private void generateSSLSocketFactory() throws PersistenceException {

        try {

            byte[] certBytes = Base64.getDecoder().decode(readCertificate());

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(certBytes));

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("lasalle_cert", cert);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            this.sslSocketFactory = context.getSocketFactory();

        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException |
                 KeyManagementException e) {
            throw new PersistenceException("Failed to prepare a secure connection to the API", e);
        }
    }

    /**
     * Method that reads a certificate from the resources folder.
     *
     * @return certificate in String format.
     * @throws IOException if there was an error reading the certificate.
     */
    private String readCertificate() throws IOException {

        BufferedReader certReader = new BufferedReader(new InputStreamReader(new FileInputStream("data/api_cert.crt")));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = certReader.readLine()) != null) {
            if (!line.startsWith("-----")) {
                builder.append(line);
            }
        }
        return builder.toString();
    }
}