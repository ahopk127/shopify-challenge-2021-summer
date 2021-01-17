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

/**
 * An image in the image repository.
 *
 * @since 2021-01-17
 */
public final class ImageEntry {
	public static final ImageEntry loadImage(String filename) {
//		final Image image = ImageIO.read(file);
		return new ImageEntry(filename);
	}
	
	/**
	 * The filename of the image, relative to the image directory. For example,
	 * 'my-image.png'.
	 */
	private final String filename;
	
	/**
	 * @param filename filename of image, relative to directory
	 * @param image    image object
	 * @since 2021-01-17
	 */
	private ImageEntry(String filename) {
		this.filename = filename;
	}
}