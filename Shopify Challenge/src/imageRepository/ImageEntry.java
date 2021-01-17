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

import java.util.Optional;

/**
 * An image in the image repository.
 *
 * @since 2021-01-17
 */
public final class ImageEntry {
	/**
	 * Gets an image entry, uploaded by an anonymous user.
	 *
	 * @param filename filename of image
	 * @return image entry
	 * @since 2021-01-17
	 */
	public static final ImageEntry loadImage(String filename) {
		return new ImageEntry(filename, Optional.empty(), true);
	}
	
	/**
	 * Gets an image entry, uploaded by a logged in user.
	 *
	 * @param filename filename of image
	 * @param user     user that uploaded the image
	 * @param isPublic whether image is public or not
	 * @return image entry
	 * @since 2021-01-17
	 */
	public static final ImageEntry loadImage(String filename, String user,
			boolean isPublic) {
		return new ImageEntry(filename, Optional.of(user), isPublic);
	}
	
	/**
	 * The filename of the image, relative to the image directory. For example,
	 * 'my-image.png'.
	 */
	private final String filename;
	
	/**
	 * The user that uploaded this image. If empty, this was uploaded by a
	 * non-logged-in user.
	 */
	private final Optional<String> user;
	
	/**
	 * If true, all users can see this image. If false, only the uploader can see
	 * it.
	 */
	private final boolean isPublic;
	
	/**
	 * @param filename filename of image, relative to directory
	 * @param image    image object
	 * @since 2021-01-17
	 */
	private ImageEntry(String filename, Optional<String> user,
			boolean isPublic) {
		this.filename = filename;
		this.user = user;
		this.isPublic = isPublic;
	}
	
	/**
	 * @return the filename
	 * @since 2021-01-17
	 */
	public final String getFilename() {
		return this.filename;
	}
	
	/**
	 * @return the user
	 * @since 2021-01-17
	 */
	public final Optional<String> getUser() {
		return this.user;
	}
	
	/**
	 * @return the isPublic
	 * @since 2021-01-17
	 */
	public final boolean isPublic() {
		return this.isPublic;
	}
	
	@Override
	public String toString() {
		return this.filename + ":" + this.user.orElse("") + ":"
				+ (this.isPublic ? "public" : "private");
	}
}