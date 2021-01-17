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
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

/**
 * The GUI for the image repository
 *
 * @since 2021-01-17
 */
public final class ImageRepositoryGUI {
	/**
	 * @param args commandline arguments
	 * @since 2021-01-17
	 */
	public static void main(String[] args) {
		new ImageRepositoryGUI().init();
	}
	
	private final JFrame frame;
	private final JFileChooser fileChooser;
	private final List<String> imageList;
	
	private ImageRepository repository;
	
	/**
	 * 
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
			
			final JList<String> list = new JList<>(model);
			masterPanel.add(new JScrollPane(list), BorderLayout.CENTER);
		}
		
		{
			final JPanel rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
			
			masterPanel.add(rightPanel, BorderLayout.EAST);
			
			// various buttons to alter settings
			final JLabel username = new JLabel("Not logged in");
			rightPanel.add(username, BorderLayout.NORTH);
			
			final JPanel addRemove = new JPanel(new GridLayout(2, 1));
			rightPanel.add(addRemove, BorderLayout.SOUTH);
			
			final JButton addButton = new JButton("Add image(s)");
			addRemove.add(addButton);
			final JButton removeButton = new JButton("Remove selected");
			addRemove.add(removeButton);
			
			// image preview
			final ImageIcon icon = new ImageIcon();
			final JLabel imageLabel = new JLabel(icon);
			rightPanel.add(imageLabel, BorderLayout.CENTER);
		}
		
		this.fileChooser = new JFileChooser();
		this.fileChooser.setDialogTitle("Choose a repository directory.");
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		this.frame.pack();
	}
	
	public void init() {
		this.frame.setVisible(true);
		
		this.loadDirectory();
	}
	
	private void loadDirectory() {
		this.fileChooser.showOpenDialog(this.frame);
		this.repository = ImageRepository
				.fromDirectory(this.fileChooser.getSelectedFile());
		
		this.update();
	}
	
	private void update() {
		this.imageList.clear();
		this.imageList.addAll(this.repository.imageNames());
	}
}
