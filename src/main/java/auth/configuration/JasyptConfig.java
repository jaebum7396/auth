package auth.configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableEncryptableProperties
//@EncryptablePropertySource(name="EncryptedProperties", value = "classpath:application-{profile_name}.yml")
@Configuration
public class JasyptConfig {
    public static final String JASYPT_STRING_ENCRYPTOR = "jasyptStringEncryptor";
    /*
       복호화 키값(jasypt.encryptor.password)는
       Application 실행 시 외부 Environment 를 통해 주입 받는다.
      # JAR 예
      -Djasypt.encryptor.password=jasypt_password.!
      # WAR 예
      -Djasypt.encryptor.password="jasypt_password.!"
     */
    @Value("${jasypt.encryptor.password}")
    private static String encryptKey;
    @Bean(JASYPT_STRING_ENCRYPTOR)
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        log.info("Jasypt Config Completed.");
        return encryptor;
    }
    public static StringEncryptor createEncryptor(String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndTripleDES"); // 권장되는 기본 알고리즘
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
    // 암호화
    public static String encryptUsingRequest(String data) {
        log.info(data);

        StringEncryptor newStringEncryptor = createEncryptor(encryptKey);
        String resultText = newStringEncryptor.encrypt(data);
        log.info(resultText);

        return resultText;
    }
    // 복호화
    public static String decryptUsingRequest(String data) {
        log.info(data);

        StringEncryptor newStringEncryptor = createEncryptor(encryptKey);
        String resultText = newStringEncryptor.decrypt(data);
        log.info(resultText);

        return resultText;
    }
}