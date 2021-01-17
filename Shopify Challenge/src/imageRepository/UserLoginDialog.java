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

import java.awt.GridLayout;
import java.util.function.BiPredicate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * A dialog that can be used to set a username and password.
 *
 * @since 2021-01-17
 */
public final class UserLoginDialog {
	private final BiPredicate<String, String> OKAction;
	private final JTextField usernameField;
	private final JPasswordField passwordField;
	
	/**
	 * Creates the login dialog
	 *
	 * @param parent   frame parent
	 * @param OKAction action to do on OK press, arguments are username and
	 *                 password. Window will only close if function returns true.
	 * @since 2021-01-17
	 */
	public UserLoginDialog(JFrame parent, BiPredicate<String, String> OKAction) {
		this.OKAction = OKAction;
		
		final JDialog dialog = new JDialog(parent, "Log in or register", true);
		
		dialog.setLayout(new GridLayout(3, 2));
		
		// init components
		{
			final JLabel username = new JLabel("Username:");
			dialog.add(username);
			
			this.usernameField = new JTextField();
			dialog.add(this.usernameField);
			
			final JLabel password = new JLabel("Password:");
			dialog.add(password);
			
			this.passwordField = new JPasswordField();
			dialog.add(this.passwordField);
			
			final JButton OKButton = new JButton("Log in/Register");
			dialog.add(OKButton);
			OKButton.addActionListener(e -> {
				if (this.OKAction.test(this.getUsername(), this.getPassword())) {
					dialog.setVisible(false);
				}
			});
			
			final JButton cancelButton = new JButton("Cancel");
			dialog.add(cancelButton);
			cancelButton.addActionListener(e -> dialog.setVisible(false));
		}
		
		dialog.pack();
		dialog.setVisible(true);
	}
	
	/**
	 * @return password entered in field
	 * @since 2021-01-17
	 */
	public String getPassword() {
		return String.valueOf(this.passwordField.getPassword());
	}
	
	/**
	 * @return username entered in field
	 * @since 2021-01-17
	 */
	public String getUsername() {
		return this.usernameField.getText();
	}
}
