package blockchainedu.blockchainedu.helper;

import blockchainedu.blockchainedu.models.Block;
import blockchainedu.blockchainedu.models.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.sun.media.jfxmedia.track.Track;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSAKCalculator;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.stereotype.Component;
import sun.security.ec.ECKeyPairGenerator;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

@Component
public class HashHelper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String hashBlock(Block block) {
        try {
            return hash(objectMapper.writeValueAsString(block));
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String hash(String content) {
        String hash = Hashing
                .sha256()
                .hashString(content, StandardCharsets.UTF_8)
                .toString();

        return hash;

    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String encodeECPointHexCompressed(ECPoint point) {
        BigInteger x = point.getXCoord().toBigInteger();
        BigInteger y = point.getYCoord().toBigInteger();
        return x.toString(16);
    }

    private static String CalcRipeMD160(String text) {
        byte[] bytes = null;
        try {
            bytes = text.getBytes("UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return bytesToHex(result);
    }

    private static byte[] CalcSHA256(String text) {
        byte[] bytes = null;
        try {
            bytes = text.getBytes("UTF-8");
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SHA256Digest digest = new SHA256Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return result;
    }

    private static String generatePubKeyToAddress(ECPoint pubKey) {
        String pubKeyCompressed = encodeECPointHexCompressed(pubKey);
//        String pubKeyCompressed = "0460a159937c242b2964cbbcfa3cf7b53022a8758d32808369d177157bfc117a37b33cb4c6bad472a965db8cc865cd35b267f324480540ce48e0b93db988aa6dad";
        String addr = CalcRipeMD160(pubKeyCompressed);
        return addr;
    }

    public static byte[] hexToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


//    public static ECPoint getPublicKeyFromPrivateKey(BigInteger privKey) {
//        SECNamedCurves secNamedCurves = new SECNamedCurves();
//        ECPoint pubKey = secNamedCurves..getCgetG().multiply(privKey).normalize();
//        return pubKey;
//    }

    private static BigInteger[] signData(BigInteger privateKey, byte[] data) {

        SECNamedCurves secNamedCurves = new SECNamedCurves();
        X9ECParameters curve = secNamedCurves.getByName("secp256k1");
        ECDomainParameters ecSpec = new ECDomainParameters(curve.getCurve(), curve.getG(), curve.getN(), curve.getH());
        ECPrivateKeyParameters keyParameters = new ECPrivateKeyParameters(privateKey, ecSpec);
        DSAKCalculator kCalculator = new HMacDSAKCalculator(new SHA256Digest());
        ECDSASigner signer = new ECDSASigner(kCalculator);
        signer.init(true, keyParameters);
        BigInteger[] signature = signer.generateSignature(data);
        return signature;
    }

    // byte[] sigR, byte sigS[], byte[] publicKey, byte[] message
    public static boolean verifySignature() {
        ECKeyPairGenerator gen = new ECKeyPairGenerator();
        SecureRandom secureRandom = new SecureRandom();
        KeyGenerationParameters keyGenParam =
                new KeyGenerationParameters(secureRandom, 256);
//        BigInteger privateKey = gen.generateKeyPair().getPrivate();


        BigInteger privateKey = new BigInteger("e9c4358732ae1c1dd8a94b24936114518b075284830ab80ab09935ada16dfc", 16);
//
//        ECPoint pubKey = getPublicKeyFromPrivateKey(privateKey);
//        String senderPubKeyCompressed = encodeECPointHexCompressed(pubKey);
//
//        String senderAddress = CalcRipeMD160(senderPubKeyCompressed);
        byte[] message = hexToByte("chikichiki");

        BigInteger[] tranSignature = signData(privateKey, message);
System.out.printf(tranSignature[0].toString(16) +"\n");
        System.out.printf(tranSignature[1].toString(16));







        byte[] sigR = hexToByte("<BN: 2cb861f8d138d38de3a8aac0bb7b6ffcab07ce99c4312fa9a668bd30761f4194>");
        byte[] sigS = hexToByte("<BN: 6b12b387a32b5758a2f13fbdeed671ea22f41d9da04d41905740a85601f47696>");
        byte[] publicKey = hexToByte("0460a159937c242b2964cbbcfa3cf7b53022a8758d32808369d177157bfc117a37b33cb4c6bad472a965db8cc865cd35b267f324480540ce48e0b93db988aa6dad");
//        byte[] message = hexToByte("chikichiki");

        try {
            Security.addProvider(new BouncyCastleProvider());
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
            ECDomainParameters domain = new ECDomainParameters(spec.getCurve(), spec.getG(), spec.getN());
            ECPublicKeyParameters publicKeyParams =
                    new ECPublicKeyParameters(spec.getCurve().decodePoint(publicKey), domain);

            ECDSASigner signer = new ECDSASigner();
            signer.init(false, publicKeyParams);
            return signer.verifySignature(message, new BigInteger(1, sigR), new BigInteger(1, sigS));
        } catch (Exception e) {
            return false;
        }
    }
}
