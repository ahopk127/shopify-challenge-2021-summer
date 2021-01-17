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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import imageRepository.libraries.DelegateListModel;

/**
 * The GUI for the image repository
 *
 * @since 2021-01-17
 */
public final class ImageRepositoryGUI {
	private static final Pattern UNIQUE_FILE = Pattern
			.compile("(.+)\\((\\d+)\\)\\.(\\w+)");
	
	/**
	 * @param args commandline arguments
	 * @since 2021-01-17
	 */
	public static void main(String[] args) {
		new ImageRepositoryGUI().init();
	}
	
	/**
	 * @return a "unique" filename derived from 'filename'
	 * @since 2021-01-17
	 */
	private static final String unique(String filename) {
		final Matcher matcher = UNIQUE_FILE.matcher(filename);
		if (matcher.matches()) {
			final int currentNum = Integer.parseInt(matcher.group(2));
			return matcher.group(1) + "(" + Integer.toString(currentNum + 1) + ")."
					+ matcher.group(3);
		} else {
			final String[] parts = filename.split("\\.");
			return parts[0] + "(1)." + parts[1];
		}
	}
	
	private final JFrame frame;
	
	private final JFileChooser fileChooser;
	
	private final JList<String> imageJList;
	
	private final List<String> imageList;
	
	private final ImageIcon imageIcon;
	
	private final JLabel iconLabel;
	
	private final JLabel usernameLabel;
	
	private final JButton loginRegisterButton;
	
	private final JButton addButton;
	
	private User currentUser = null;
	
	private ImageRepository repository;
	
	/**
	 * @since 2021-01-17
	 */
	public ImageRepositoryGUI() {
		this.frame = new JFrame("Image Repository");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		final JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new BorderLayout());
		masterPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		this.frame.add(masterPanel);
		
		{
			final DelegateListModel<String> model = new DelegateListModel<>();
			this.imageList = model;
			
			this.imageJList = new JList<>(model);
			masterPanel.add(new JScrollPane(this.imageJList), BorderLayout.CENTER);
			this.imageJList.addListSelectionListener(e -> this.updateImageView());
		}
		
		{
			final JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
			
			masterPanel.add(rightPanel, BorderLayout.EAST);
			
			// user logon button
			final JPanel userPanel = new JPanel(new BorderLayout());
			rightPanel.add(userPanel, BorderLayout.NORTH);
			
			this.usernameLabel = new JLabel("Not logged in");
			userPanel.add(this.usernameLabel, BorderLayout.CENTER);
			
			this.loginRegisterButton = new JButton("Log in/Register");
			userPanel.add(this.loginRegisterButton, BorderLayout.EAST);
			this.loginRegisterButton.addActionListener(e -> {
				if (this.currentUser == null) {
					new UserLoginDialog(this.frame, this::loginOrRegister);
				} else {
					this.logOut();
				}
			});
			
			// action buttons
			final JPanel addRemove = new JPanel(new GridLayout(0, 1));
			rightPanel.add(addRemove, BorderLayout.SOUTH);
			
			this.addButton = new JButton("Add private image(s)");
			this.addButton.addActionListener(e -> this.addFiles(false));
			this.addButton.setEnabled(false);
			addRemove.add(this.addButton);
			final JButton addPublicButton = new JButton("Add public image(s)");
			addPublicButton.addActionListener(e -> this.addFiles(true));
			addRemove.add(addPublicButton);
			final JButton removeButton = new JButton("Remove selected");
			removeButton.addActionListener(e -> this.removeSelected());
			addRemove.add(removeButton);
			final JButton saveButton = new JButton("Save selected");
			saveButton.addActionListener(e -> this.saveSelected());
			addRemove.add(saveButton);
			
			// image preview
			this.imageIcon = new ImageIcon();
			this.iconLabel = new JLabel();
			this.iconLabel.setDisabledIcon(this.imageIcon);
			this.iconLabel.setBorder(new LineBorder(Color.BLACK));
			this.iconLabel.setPreferredSize(new Dimension(240, 160));
			rightPanel.add(this.iconLabel, BorderLayout.CENTER);
		}
		
		this.fileChooser = new JFileChooser();
		
		this.frame.pack();
	}
	
	/**
	 * Add one or more files to the repository. Files are chosen by the user with
	 * a dialog.
	 * 
	 * @param isPublic whether or not the images are public
	 * 
	 * @since 2021-01-17
	 */
	public void addFiles(boolean isPublic) {
		this.fileChooser.setDialogTitle("Choose file(s) to add.");
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.fileChooser.setMultiSelectionEnabled(true);
		this.fileChooser.showOpenDialog(this.frame);
		
		final File[] files = this.fileChooser.getSelectedFiles();
		
		for (final File f : files) {
			String newPath = f.getName();
			
			while (Files.exists(Path.of(
					this.repository.getDirectory().getAbsolutePath(), newPath))) {
				newPath = unique(newPath);
			}
			
			this.repository.addImage(f, newPath, this.currentUser.getUsername(),
					isPublic);
			this.imageList.add(newPath);
		}
	}
	
	public void init() {
		this.frame.setVisible(true);
		
		this.loadDirectory();
	}
	
	/**
	 * Lets the user choose a directory, and sets that directory as the
	 * repository.
	 * 
	 * @since 2021-01-17
	 */
	private void loadDirectory() {
		this.fileChooser.setDialogTitle("Choose a repository directory.");
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.fileChooser.setMultiSelectionEnabled(false);
		this.fileChooser.showOpenDialog(this.frame);
		this.repository = ImageRepository
				.fromDirectory(this.fileChooser.getSelectedFile());
		
		this.imageList.clear();
		this.imageList.addAll(this.repository.imageNames(null));
	}
	
	/**
	 * Logs on or offers to register if there is no user with the provided
	 * username.
	 *
	 * @param username provided username
	 * @param password provided password
	 * @return true iff the log on or registration was successful
	 * @since 2021-01-17
	 */
	private boolean loginOrRegister(String username, String password) {
		final User user = this.repository.getUser(username);
		
		if (user == null) { // register
			final int result = JOptionPane.showConfirmDialog(this.frame,
					"There is no user with username \"" + username
							+ "\".  Register as \"" + username + "\"?",
					"New User Registration", JOptionPane.YES_NO_OPTION);
			
			switch (result) {
			case JOptionPane.YES_OPTION:
				final User newUser = User.createUser(username, password);
				this.repository.addUser(newUser);
				this.currentUser = newUser;
				
				this.usernameLabel.setText("Logged in as " + username);
				this.loginRegisterButton.setText("Log out");
				
				this.repository.saveUsers(); // user list updated, save users
				this.addButton.setEnabled(true);
				return true; // logged on as new user
				
			default:
				return false; // did not log in
			}
		} else { // log on
			if (user.authenticatePassword(password)) {
				this.currentUser = user;
				
				this.usernameLabel.setText("Logged in as " + username);
				this.loginRegisterButton.setText("Log out");
				
				this.imageList.clear();
				this.imageList
						.addAll(this.repository.imageNames(user.getUsername()));
				
				this.addButton.setEnabled(true);
				
				return true; // logged on as existing user with correct password
			} else {
				JOptionPane.showMessageDialog(this.frame, "Incorrect password.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return false; // logged on as existing user with incorrect password
			}
		}
	}
	
	/**
	 * Logs out of the system.
	 * 
	 * @since 2021-01-17
	 */
	public void logOut() {
		this.currentUser = null;
		this.loginRegisterButton.setText("Log in/Register");
		this.usernameLabel.setText("Not logged in");
		this.addButton.setEnabled(false);
		
		this.imageList.clear();
		this.imageList.addAll(this.repository.imageNames(null));
	}
	
	/**
	 * Removes the selected images, warning the user if multiple images are
	 * selected.
	 * 
	 * @since 2021-01-17
	 */
	public void removeSelected() {
		final int[] selectedIndices = this.imageJList.getSelectedIndices();
		
		if (selectedIndices.length > 1) {
			final int result = JOptionPane.showConfirmDialog(this.frame,
					"You are about to delete " + selectedIndices.length
							+ " images.  Are you sure you want to continue?",
					"Multiple Image Deletion Warning", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION)
				return;
		}
		
		// check you can remove files
		int removable = 0;
		for (final int i : selectedIndices) {
			final Optional<String> imageOwner = this.repository
					.getImageData(this.imageList.get(i)).getUser();
			if (imageOwner.isPresent() && this.currentUser != null
					&& imageOwner.get().equals(this.currentUser.getUsername())) {
				removable++;
			}
		}
		
		// display error if you cannot remove any of the selected files.
		if (removable == 0) {
			JOptionPane.showMessageDialog(this.frame,
					"You do not own any of the selected images.  You can only remove your own images.",
					"Unowned Image Deletion Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else if (removable < selectedIndices.length) {
			final int result = JOptionPane.showConfirmDialog(this.frame,
					"You do not own all of the selected images, you can only remove the "
							+ removable + " that you own.  Continue?",
					"Unowned Image Deletion Warning", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION)
				return;
		}
		
		// remove files
		for (final int i : selectedIndices) {
			if (this.repository.removeImage(this.imageList.get(i),
					this.currentUser.getUsername())) {
				this.imageList.remove(i);
			}
		}
	}
	
	/**
	 * Saves the selected files, prompting the user for save location
	 * 
	 * @since 2021-01-17
	 */
	public void saveSelected() {
		final int[] selectedIndices = this.imageJList.getSelectedIndices();
		
		// select save location
		this.fileChooser.setDialogTitle(
				selectedIndices.length > 1 ? "Choose a directory to save to."
						: "Choose a location to save to.");
		this.fileChooser.setFileSelectionMode(
				selectedIndices.length > 1 ? JFileChooser.DIRECTORIES_ONLY
						: JFileChooser.FILES_AND_DIRECTORIES);
		this.fileChooser.setMultiSelectionEnabled(false);
		this.fileChooser.showOpenDialog(this.frame);
		
		final File saveTo = this.fileChooser.getSelectedFile();
		
		// save files
		for (final int i : selectedIndices) {
			this.repository.saveImage(this.imageList.get(i), saveTo);
		}
	}
	
	/**
	 * Updates the image view, called when the selection on the list changes.
	 * 
	 * @since 2021-01-17
	 */
	public void updateImageView() {
		final int[] selectedIndices = this.imageJList.getSelectedIndices();
		
		if (selectedIndices.length == 1) {
			this.imageIcon.setImage(this.repository
					.getImage(this.imageList.get(selectedIndices[0])));
			this.iconLabel.setEnabled(false);
			this.iconLabel.setText("");
		} else if (selectedIndices.length == 0) {
			this.iconLabel.setEnabled(true);
			this.iconLabel.setText("");
		} else {
			this.iconLabel.setEnabled(true);
			this.iconLabel.setText(selectedIndices.length + " images selected.");
		}
	}
}
