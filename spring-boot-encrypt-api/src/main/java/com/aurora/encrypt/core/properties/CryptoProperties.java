package com.aurora.encrypt.core.properties;

import com.aurora.encrypt.core.enums.AlgorithmType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 加密配置参数
 * @author xzbcode
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.crypt")
@SuppressWarnings("all")
public class CryptoProperties {

    private AlgorithmType type = AlgorithmType.AES;

    private AesProperties aes = new AesProperties();

    private RsaProperties rsa = new RsaProperties();

    private RsaAesProperties rsaAes = new RsaAesProperties();


    @Getter
    @Setter
    public class AesProperties {
        // AES密钥
        private String key = "ABCDEFGHIJKL0123";
        // AES偏移量
        private String iv = "1234567890123456";
    }

    @Getter
    @Setter
    public class RsaProperties {
        // RSA私钥
        private String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6K" +
                "7kQd+rTtlDfpFcFw9BBempm69CIBf1OarIB8StmDHaKk4T9Vt16PA1SjzYusIVWf5A3LQZn9qGwfT27jCU0QID" +
                "AQABAkA0pw7D0UdEiml5hI8gMLV4Dw3wStJdzM4TBJBLIbhejcrkS4GJ2Ej0xls+AUGWmA" +
                "CRB/jmXTv/DO5Gdk3DzrZ5AiEA+MKIlj1XQAtYH5F83XMrZSEo7vnSgo/zbpY7NpBWMF8CIQDvdJK+Jy" +
                "d7EbQeWLp28wt/53vm/gPmvXEirUB/QFOIzwIhAMNoRNUIAHbNsn6x0Y0/gBIj1zzKP+PR4l63YnI9NYc" +
                "vAiEApPHQA1xFPek8AYttJnLVAQ4bs0pWtaLZQ+HEA+PzptECIBfxaSFBVBUuRYm53lLNQAU8XCTXZenvbxSq4KTDZcpY";

        // RSA公钥
        private String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOiu5EHfq07ZQ36RXBcPQQXpqZuvQiAX9" +
                "TmqyAfErZgx2ipOE/VbdejwNUo82LrCFVn+QNy0GZ/ahsH09u4wlNECAwEAAQ==";
    }

    @Getter
    @Setter
    public class RsaAesProperties {
        // AES加密后的内容的密钥
        private String aesHeaderKey = "Data-AESKey";
        // AES偏移量
        private String aesIv = "1234567890123456";
        // RSA私钥
        private String rsaPrivateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6K" +
                "7kQd+rTtlDfpFcFw9BBempm69CIBf1OarIB8StmDHaKk4T9Vt16PA1SjzYusIVWf5A3LQZn9qGwfT27jCU0QID" +
                "AQABAkA0pw7D0UdEiml5hI8gMLV4Dw3wStJdzM4TBJBLIbhejcrkS4GJ2Ej0xls+AUGWmA" +
                "CRB/jmXTv/DO5Gdk3DzrZ5AiEA+MKIlj1XQAtYH5F83XMrZSEo7vnSgo/zbpY7NpBWMF8CIQDvdJK+Jy" +
                "d7EbQeWLp28wt/53vm/gPmvXEirUB/QFOIzwIhAMNoRNUIAHbNsn6x0Y0/gBIj1zzKP+PR4l63YnI9NYc" +
                "vAiEApPHQA1xFPek8AYttJnLVAQ4bs0pWtaLZQ+HEA+PzptECIBfxaSFBVBUuRYm53lLNQAU8XCTXZenvbxSq4KTDZcpY";

        // RSA公钥
        private String rsaPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOiu5EHfq07ZQ36RXBcPQQXpqZuvQiAX9" +
                "TmqyAfErZgx2ipOE/VbdejwNUo82LrCFVn+QNy0GZ/ahsH09u4wlNECAwEAAQ==";
    }

}
