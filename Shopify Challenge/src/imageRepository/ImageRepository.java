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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * A repository of images. Images are identified by their filename.
 * 
 * @author Adrien Hopkins
 *
 * @since 2021-01-17
 */
public final class ImageRepository {
	/**
	 * Loads an image repository from a directory {@code imageDir}.
	 *
	 * @since 2021-01-17
	 */
	public static final ImageRepository fromDirectory(File imageDir) {
		final Map<String, ImageEntry> data = new HashMap<>();
		for (final File f : imageDir.listFiles()) {
			data.put(f.getName(), ImageEntry.loadImage(f.getName()));
		}
		return new ImageRepository(imageDir, data);
	}
	
	private final File directory;
	private final Map<String, ImageEntry> data;
	
	/**
	 * @param directory directory where images and data are stored
	 * @param data      image data
	 * @since 2021-01-17
	 */
	private ImageRepository(File directory, Map<String, ImageEntry> data) {
		this.directory = directory;
		this.data = data;
	}
	
	/**
	 * Adds an image to the directory
	 *
	 * @param originalFilepath place where image was originally stored
	 * @param newFilepath      place where the image will be stored, relative to
	 *                         directory, must be in repository directory.
	 * @since 2021-01-17
	 */
	public final void addImage(File originalFilepath, String newFilepath) {
		this.data.put(newFilepath, ImageEntry.loadImage(newFilepath));
		try {
			Files.copy(Path.of(originalFilepath.getAbsolutePath()),
					this.getPath(newFilepath));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the directory
	 * @since 2021-01-17
	 */
	public final File getDirectory() {
		return this.directory;
	}
	
	/**
	 * Gets the image with name {@code name} from the repository.
	 *
	 * @since 2021-01-17
	 */
	public final Image getImage(String name) {
		try {
			return ImageIO.read(this.getPath(name).toFile());
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private final Path getPath(String imageFilename) {
		return Path.of(this.directory.getAbsolutePath(), imageFilename);
	}
	
	/**
	 * @return set of names of all images in repository
	 * @since 2021-01-17
	 */
	public final Set<String> imageNames() {
		return Collections.unmodifiableSet(this.data.keySet());
	}
	
	/**
	 * Removes an image from the directory
	 *
	 * @param name name of image to remove
	 * @since 2021-01-17
	 */
	public final void removeImage(String name) {
		this.data.remove(name);
		try {
			Files.delete(this.getPath(name));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves an image to a file outside the repository.
	 *
	 * @param imageName name of image
	 * @param saveTo    path to save to. If this is a directory, saves to the
	 *                  directory with the original filename.
	 * @since 2021-01-17
	 */
	public final void saveImage(String imageName, File saveTo) {
		if (saveTo.isDirectory()) {
			this.saveImage(imageName, new File(saveTo, imageName));
			return;
		}
		
		// copy file to filepath
		try {
			Files.copy(this.getPath(imageName), Path.of(saveTo.getAbsolutePath()));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
