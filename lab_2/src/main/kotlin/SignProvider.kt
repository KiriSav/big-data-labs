import org.bouncycastle.asn1.ASN1InputStream
import org.bouncycastle.asn1.cms.ContentInfo
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.cert.jcajce.JcaCertStore
import org.bouncycastle.cms.CMSProcessableByteArray
import org.bouncycastle.cms.CMSSignedData
import org.bouncycastle.cms.CMSSignedDataGenerator
import org.bouncycastle.cms.SignerInformation
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder
import java.io.ByteArrayInputStream
import java.security.PrivateKey
import java.security.cert.X509Certificate
import java.util.*


class SignProvider {
    fun signData(
        data: ByteArray,
        signingCertificate: X509Certificate,
        signingKey: PrivateKey
    ): ByteArray {
        val certList = ArrayList<X509Certificate>().apply { add(signingCertificate) }
        val contentSigner = JcaContentSignerBuilder("SHA256withRSA").build(signingKey)
        val cmsGenerator = CMSSignedDataGenerator()
            .apply {
                addSignerInfoGenerator(
                    JcaSignerInfoGeneratorBuilder(
                        JcaDigestCalculatorProviderBuilder().setProvider("BC")
                            .build()
                    ).build(contentSigner, signingCertificate)
                )

                val certs = JcaCertStore(certList)
                addCertificates(certs)
            }

        val cmsData = CMSProcessableByteArray(data)
        val cmsSignedData = cmsGenerator.generate(cmsData, true)
        return cmsSignedData.encoded
    }

    fun verifySignedData(signedData: ByteArray): Boolean {
        val inputStream = ByteArrayInputStream(signedData)
        val asnInputStream = ASN1InputStream(inputStream)
        val cmsSignedData = CMSSignedData(
            ContentInfo.getInstance(asnInputStream.readObject())
        )

        val signers = cmsSignedData.signerInfos
        val signer: SignerInformation = signers.signers.first() as SignerInformation
        val certCollection = cmsSignedData.certificates.getMatches(signer.sid)
        val certHolder = certCollection.first() as X509CertificateHolder

        return signer.verify(
            JcaSimpleSignerInfoVerifierBuilder()
                .build(certHolder)
        )
    }

}