/**
 * Copyright (C) 2021 Adrien Hopkins
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package imageRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A user of the repository.
 *
 * @since 2021-01-17
 */
public final class User {
	/**
	 * Creates a new user with username {@code username} and password
	 * {@code password}.
	 *
	 * @return user
	 * @since 2021-01-17
	 */
	public static final User createUser(String username, String password) {
		final byte[] salt = newSalt();
		final String passwordHash = hash(password, salt);
		return new User(username, passwordHash, salt);
	}
	
	/**
	 * Gets a user from an encoded string.
	 *
	 * @since 2021-01-17
	 */
	public static final User fromString(String userString) {
		final String[] parts = userString.split(":");
		if (parts.length != 3)
			throw new IllegalArgumentException("Incorrectly formatted string.");
		
		final String username = parts[0];
		final String passwordHash = parts[1];
		final byte[] salt = Base64.getDecoder().decode(parts[2]);
		
		return new User(username, passwordHash, salt);
	}
	
	/**
	 * Hashes a password with a given salt.
	 *
	 * @param password password to hash
	 * @param salt     salt to use
	 * @return password hash
	 * @since 2021-01-17
	 */
	private static final String hash(String password, byte[] salt) {
		String generatedHash = null;
		try {
			// create hasher
			final MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			
			// hash password
			final byte[] bytes = md.digest(password.getBytes());
			
			// convert hash to hexadecimal string
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(
						Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedHash = sb.toString();
		} catch (final NoSuchAlgorithmException e) {
			throw new AssertionError();
		}
		
		return generatedHash;
	}
	
	/**
	 * @return a new, randomly generated, salt
	 * @since 2021-01-17
	 */
	private static final byte[] newSalt() {
		SecureRandom rng;
		try {
			rng = SecureRandom.getInstance("SHA1PRNG");
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		final byte[] salt = new byte[16];
		rng.nextBytes(salt);
		return salt;
	}
	
	private final String username;
	
	private final String passwordHash;
	
	private final byte[] salt;
	
	/**
	 * @param username     user's username
	 * @param passwordHash salted hash of user's password
	 * @param salt         salt used for hash
	 * @since 2021-01-17
	 */
	public User(String username, String passwordHash, byte[] salt) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.salt = salt;
	}
	
	/**
	 * Accepts a password and tests it against the user's password hash.
	 *
	 * @param password password to test
	 * @return true if password is correct, false otherwise
	 * @since 2021-01-17
	 */
	public final boolean authenticatePassword(String password) {
		return hash(password, this.salt).equals(this.passwordHash);
	}
	
	/**
	 * @return user's username
	 * @since 2021-01-17
	 */
	public final String getUsername() {
		return this.username;
	}
	
	@Override
	public final String toString() {
		return this.username + ":" + this.passwordHash + ":"
				+ Base64.getEncoder().encodeToString(this.salt);
	}
}
