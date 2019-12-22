import org.bouncycastle.cms.*
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.InputStream
import java.security.KeyStore
import java.security.PrivateKey
import java.security.Security
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

class Encryptor {
    fun initialise() {
        Security.setProperty("crypto.policy", "unlimited");
        Security.addProvider(BouncyCastleProvider());
    }

    fun demonstrateOperations() {
        val maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        println("Max Key Size for AES : $maxKeySize");

        val certFactory = CertificateFactory.getInstance("X.509", "BC");
        val certificateInputStream = getResource("public.cer")
        val certificate = certFactory.generateCertificate(certificateInputStream);
        val keystorePassword = "password".toCharArray();
        val keyPassword = "password".toCharArray();
        val keystore = KeyStore.getInstance("PKCS12")
        val keyStoreStream = getResource("private.p12")
        keystore.load(keyStoreStream, keystorePassword)
        val privateKey = keystore.getKey(
            "baeldung",
            keyPassword
        ) as PrivateKey

        val secretMessage = "My password is 123456Seven"
        val encryptedData = encryptData(secretMessage.toByteArray(), certificate as X509Certificate)
        val rawData = decryptData(encryptedData, privateKey)

        println("Original Message : $secretMessage")

        println("Encrypted: ")
        println(String(encryptedData))

        println("Decrypted: ${String(rawData)}")

        val signProvider = SignProvider()
        val signedData = signProvider.signData(rawData, certificate, privateKey)
        val isVerified = signProvider.verifySignedData(signedData)
        println("Is Verified: $isVerified")
    }

    private fun encryptData(dataForEncryption: ByteArray, encryptionCertificate: X509Certificate):ByteArray {
        val cmsEnvelopeddatagenerator = CMSEnvelopedDataGenerator()
        val jceKey = JceKeyTransRecipientInfoGenerator(encryptionCertificate)

        cmsEnvelopeddatagenerator.addRecipientInfoGenerator(jceKey)
        val message = CMSProcessableByteArray(dataForEncryption)

        val encryptor = JceCMSContentEncryptorBuilder(CMSAlgorithm.AES128_CBC)
            .setProvider("BC")
            .build()

        val cmsEnvelopedData = cmsEnvelopeddatagenerator.generate(message, encryptor)

        return cmsEnvelopedData.encoded
    }

    private fun decryptData(encrypteData:ByteArray, decryptionKey: PrivateKey):ByteArray {
        val envelopedData = CMSEnvelopedData(encrypteData)
        val recipients:Collection<RecipientInformation> = envelopedData.recipientInfos.recipients as Collection<RecipientInformation>
        val recipientInfo = recipients.iterator().next()
        val recipient = JceKeyTransEnvelopedRecipient(decryptionKey)

        return recipientInfo.getContent(recipient)
    }

    private fun getResource(resourceName: String): InputStream =
        this::class.java.classLoader.getResource(resourceName).openStream()
}