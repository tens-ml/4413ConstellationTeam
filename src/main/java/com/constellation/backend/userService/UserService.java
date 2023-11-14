package com.constellation.backend.userService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
	/**
	 * Authenticates the user based on the username and password provided
	 * 
	 * @param username Login username
	 * @param password Login password
	 * @return user if login success, null otherwise
	 */
	public User authenticateUser(String username, String password) {
		password = encrypt(password);
		return UserDao.getUser(username, password);
	}

	/**
	 * Create a user if the username doesn't already exist in db
	 * 
	 * @param user User to create
	 * @param password Password of user
	 * @return User if created successfully (one doesn't already exist), null otherwise
	 */
	public User createUser(User user, String password) {
		password = UserService.encrypt(password); // encrypt the password using SHA-512 hash encryption
		if (UserDao.isUserInDatabase(user.getUsername())) { // if the user already exists throw an exception declaring
			return null;
		} else {
			UserDao.insertUser(user, password); // add the user so that its id can be auto incremented
		}
		return UserDao.getUser(user.getUsername(), password); // get the user with the incremented id
	}

	/**
	 * Change the password of the user
	 *
	 * @param newPassword the new password
	 * @return the user with the new password
	 */
	public boolean changePassword(String username, String newPassword) {
		String password = encrypt(newPassword);
		return UserDao.updatePassword(username, password);

	}
	/**
	 * Encrypts input string using getSHA and toHexString
	 * 
	 * @param input the input string
	 * @return the encrypted version of the input string
	 */
	private static String encrypt(String input) {
		try {
			return toHexString(getSHA(input));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "ERROR";
	}

	/**
	 * Converts an input string into an array of bytes using SHA-512 encryption
	 * 
	 * @param input is the input string that is to be converted into byte
	 * @return the byte form of the input string
	 * @throws NoSuchAlgorithmException
	 */
	private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return md.digest(input.getBytes(StandardCharsets.UTF_8)); // convert the input string into bytes and SHA-512
	}

	/**
	 * Converts an input array of bytes and then converts it to a string
	 * 
	 * @param hash is the array of input bytes
	 * @return the encrypted string
	 */
	private static String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));

		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}

		return hexString.toString();
	}
}
