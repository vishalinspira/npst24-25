package nps.email.service.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.osgi.service.component.annotations.Component;

import nps.email.api.api.EncryptionServiceApi;

@Component(immediate = true, property = {}, service = EncryptionServiceApi.class)
public class EncryptionServiceImpl implements EncryptionServiceApi {
	Log LOG = LogFactoryUtil.getLog(getClass());

	@Override
	public String encrypt(String secretMessage) {
		
		
		String encodedMessage = secretMessage;
		try {
			PublicKey publicKey  = getPublicKey();
			Cipher encryptCipher = Cipher.getInstance("RSA");
			encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
			byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
			encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
		} catch (InvalidKeyException e) {
			LOG.error(e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		} catch (InvalidKeySpecException e) {
			LOG.error(e);
		} catch (NoSuchPaddingException e) {
			LOG.error(e);
		} catch (IllegalBlockSizeException e) {
			LOG.error(e);
		} catch (BadPaddingException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		
		return encodedMessage;
	}

	@Override
	public String decrypt(String encodedMessage) {
		LOG.info(encodedMessage);
		String decryptedMessage = encodedMessage;
		try {
			PrivateKey privateKey = getPrivateKey();
			Cipher decryptCipher = Cipher.getInstance("RSA");
			decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] encryptedMessageBytes = Base64.getDecoder().decode(encodedMessage);
			byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
			decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		} catch (InvalidKeyException e) {
			LOG.error(e);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		} catch (InvalidKeySpecException e) {
			LOG.error(e);
		} catch (NoSuchPaddingException e) {
			LOG.error(e);
		} catch (IllegalBlockSizeException e) {
			LOG.error(e);
		} catch (BadPaddingException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		
		LOG.info(decryptedMessage);
		return decryptedMessage;
	}
	@Override
	public String getPK() {
		File publicKeyFile = new File("public.key");
		File privateKeyFile = new File("private.key");
		if (!publicKeyFile.exists() || !privateKeyFile.exists()) {
			try {
				generateKey();
			} catch (NoSuchAlgorithmException e) {
				LOG.error(e);
			} catch (FileNotFoundException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		
		
		
		try {
			
			//PublicKey publicKey = getPublicKey();
			//String pk = Base64.getMimeEncoder().encodeToString(publicKey.getEncoded());
			
			PrivateKey privateKey = getPrivateKey();
			StringBuffer pkBuffer = new StringBuffer("-----BEGIN RSA PRIVATE KEY-----");
			pkBuffer.append(System.getProperty("line.separator"));
			String pk = Base64.getMimeEncoder().encodeToString(privateKey.getEncoded());
			pkBuffer.append(pk);
			pkBuffer.append(System.getProperty("line.separator"));
			pkBuffer.append("-----END RSA PRIVATE KEY-----");
			return pkBuffer.toString();
			
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		} catch (InvalidKeySpecException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		return "";
	}
	private void generateKey() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
		
		try (FileOutputStream fos = new FileOutputStream("public.key")) {
			//LOG.info(Base64.getMimeEncoder().encodeToString(publicKey.getEncoded()));
		    fos.write(publicKey.getEncoded());
		    fos.flush();
	        fos.close();
		}
		
		
		try (FileOutputStream fos = new FileOutputStream("private.key")) {
		    fos.write(privateKey.getEncoded());
		    fos.flush();
	        fos.close();
		}
	}
	
	private PublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File publicKeyFile = new File("public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey  = keyFactory.generatePublic(publicKeySpec);
		//LOG.info(Base64.getMimeEncoder().encodeToString(publicKey.getEncoded()));
		return publicKey;
	}
	
	private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File privateKeyFile = new File("private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		//EncodedKeySpec privateKeySpec = new  P //new X509EncodedKeySpec(publicKeyBytes);
		PrivateKey publicKey  = keyFactory.generatePrivate(privateKeySpec);
		return publicKey;
	}

}
