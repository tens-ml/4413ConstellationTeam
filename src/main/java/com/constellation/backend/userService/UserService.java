package com.constellation.backend.userService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/UserService")
public class UserService {
	UserDao userDao = new UserDao();

	/**
	 * Authenticates the user based on the username and password provided and
	 * returns the user with valid id and parameters
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UsernamePasswordMismatchException
	 */
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User authenticateUser(String username, String password) throws UsernamePasswordMismatchException {
		password = UserService.encrypt(password); // encrypt the password using SHA-512 hash encryption
		User user = UserDao.getUser(username, password); // get the user
		if (!UserDao.isUserInDatabase(username) && user != null) { // if the user does not exist or if the password is
																	// not matched
			throw new UsernamePasswordMismatchException("Username and password do not match."); // sends authentication
																								// missmatch
		}
		return user; // returns the user
	}

	/**
	 * Creates a user based on the user and password, if the user already exists,
	 * sends an exception
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws UserAlreadyExistsException
	 */
	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(User user, String password) throws UserAlreadyExistsException {
		password = UserService.encrypt(password); // encrypt the password using SHA-512 hash encryption
		if (UserDao.isUserInDatabase(user.getUsername())) { // if the user already exists throw an exception declaring
			throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
		} else {
			UserDao.insertUser(user, password); // add the user so that its id can be auto incremented
		}
		return UserDao.getUser(user.getUsername(), password); // get the user with the incremented id
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
