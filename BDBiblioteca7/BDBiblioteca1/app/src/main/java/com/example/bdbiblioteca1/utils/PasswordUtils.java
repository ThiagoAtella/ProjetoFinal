package com.example.bdbiblioteca1.utils;

import android.os.Build;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

	private static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String hashPassword(String password, String salt) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Erro ao gerar hash da senha", e);
		}
	}

	public static String generateSecurePassword(String password) {
		String salt = generateSalt();
		String hashedPassword = hashPassword(password, salt);
		return salt + ":" + hashedPassword;
	}

	public static boolean verifyPassword(String inputPassword, String storedPassword) {
		if (storedPassword == null || !storedPassword.contains(":")) return false;
		String[] parts = storedPassword.split(":");
		String salt = parts[0];
		String hash = parts[1];
		return hash.equals(hashPassword(inputPassword, salt));
	}
}
