package com.test.jwt;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWTOptions;

import java.util.logging.Logger;

public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String args[]) {

//        JWTAuthOptions jwtOpt = new JWTAuthOptions();

        JWTOptions jwtOpt = new JWTOptions();
        jwtOpt.setExpiresInMinutes(5);
        jwtOpt.setAlgorithm("ES256");

        JWTAuth provider = JWTAuth.create(Vertx.vertx(), new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("ES256")
                        .setPublicKey(
                                "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEraVJ8CpkrwTPRCPluUDdwC6b8+m4\n" +
                                        "dEjwl8s+Sn0GULko+H95fsTREQ1A2soCFHS4wV3/23Nebq9omY3KuK9DKw==\n")
                        .setSecretKey(
                                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgeRyEfU1NSHPTCuC9\n" +
                                        "rwLZMukaWCH2Fk6q5w+XBYrKtLihRANCAAStpUnwKmSvBM9EI+W5QN3ALpvz6bh0\n" +
                                        "SPCXyz5KfQZQuSj4f3l+xNERDUDaygIUdLjBXf/bc15ur2iZjcq4r0Mr")
                ));

        String token = provider.generateToken(new JsonObject(), jwtOpt);
        logger.info(token);

        logger.info(String.valueOf(decode(token)));

//        provider.authenticate();
    }

    public static JsonObject decode(String jwtToken) {

        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)
        String payloadJson = new String(decoder.decode(parts[1]));
        return new JsonObject(payloadJson);
    }

}
