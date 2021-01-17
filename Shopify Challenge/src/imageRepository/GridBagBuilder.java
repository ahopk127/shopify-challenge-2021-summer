/**
 * Copyright (C) 2018 Adrien Hopkins
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

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * A builder for Java's {@link java.awt.GridBagConstraints} class.
 * <p>
 * Note: this class was made by me for personal projects, before this challenge,
 * and is being reused.
 * 
 * @author Adrien Hopkins
 */
final class GridBagBuilder {
	/**
	 * The built {@code GridBagConstraints}'s {@code gridx} property.
	 * <p>
	 * Specifies the cell containing the leading edge of the component's display
	 * area, where the first cell in a row has <code>gridx=0</code>. The leading
	 * edge of a component's display area is its left edge for a horizontal,
	 * left-to-right container and its right edge for a horizontal, right-to-left
	 * container. The value <code>RELATIVE</code> specifies that the component be
	 * placed immediately following the component that was added to the container
	 * just before this component was added.
	 * <p>
	 * The default value is <code>RELATIVE</code>. <code>gridx</code> should be a
	 * non-negative value.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#gridy
	 * @see java.awt.ComponentOrientation
	 */
	private final int gridx;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code gridy} property.
	 * <p>
	 * Specifies the cell at the top of the component's display area, where the
	 * topmost cell has <code>gridy=0</code>. The value <code>RELATIVE</code>
	 * specifies that the component be placed just below the component that was
	 * added to the container just before this component was added.
	 * <p>
	 * The default value is <code>RELATIVE</code>. <code>gridy</code> should be a
	 * non-negative value.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#gridx
	 */
	private final int gridy;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code gridwidth} property.
	 * <p>
	 * Specifies the number of cells in a row for the component's display area.
	 * <p>
	 * Use <code>REMAINDER</code> to specify that the component's display area
	 * will be from <code>gridx</code> to the last cell in the row. Use
	 * <code>RELATIVE</code> to specify that the component's display area will be
	 * from <code>gridx</code> to the next to the last one in its row.
	 * <p>
	 * <code>gridwidth</code> should be non-negative and the default value is 1.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#gridheight
	 */
	private final int gridwidth;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code gridheight} property.
	 * <p>
	 * Specifies the number of cells in a column for the component's display
	 * area.
	 * <p>
	 * Use <code>REMAINDER</code> to specify that the component's display area
	 * will be from <code>gridy</code> to the last cell in the column. Use
	 * <code>RELATIVE</code> to specify that the component's display area will be
	 * from <code>gridy</code> to the next to the last one in its column.
	 * <p>
	 * <code>gridheight</code> should be a non-negative value and the default
	 * value is 1.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#gridwidth
	 */
	private final int gridheight;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code weightx} property.
	 * <p>
	 * Specifies how to distribute extra horizontal space.
	 * <p>
	 * The grid bag layout manager calculates the weight of a column to be the
	 * maximum <code>weightx</code> of all the components in a column. If the
	 * resulting layout is smaller horizontally than the area it needs to fill,
	 * the extra space is distributed to each column in proportion to its weight.
	 * A column that has a weight of zero receives no extra space.
	 * <p>
	 * If all the weights are zero, all the extra space appears between the grids
	 * of the cell and the left and right edges.
	 * <p>
	 * The default value of this field is <code>0</code>. <code>weightx</code>
	 * should be a non-negative value.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#weighty
	 */
	private double weightx;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code weighty} property.
	 * <p>
	 * Specifies how to distribute extra vertical space.
	 * <p>
	 * The grid bag layout manager calculates the weight of a row to be the
	 * maximum <code>weighty</code> of all the components in a row. If the
	 * resulting layout is smaller vertically than the area it needs to fill, the
	 * extra space is distributed to each row in proportion to its weight. A row
	 * that has a weight of zero receives no extra space.
	 * <p>
	 * If all the weights are zero, all the extra space appears between the grids
	 * of the cell and the top and bottom edges.
	 * <p>
	 * The default value of this field is <code>0</code>. <code>weighty</code>
	 * should be a non-negative value.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#weightx
	 */
	private double weighty;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code anchor} property.
	 * <p>
	 * This field is used when the component is smaller than its display area. It
	 * determines where, within the display area, to place the component.
	 * <p>
	 * There are three kinds of possible values: orientation relative, baseline
	 * relative and absolute. Orientation relative values are interpreted
	 * relative to the container's component orientation property, baseline
	 * relative values are interpreted relative to the baseline and absolute
	 * values are not. The absolute values are: <code>CENTER</code>,
	 * <code>NORTH</code>, <code>NORTHEAST</code>, <code>EAST</code>,
	 * <code>SOUTHEAST</code>, <code>SOUTH</code>, <code>SOUTHWEST</code>,
	 * <code>WEST</code>, and <code>NORTHWEST</code>. The orientation relative
	 * values are: <code>PAGE_START</code>, <code>PAGE_END</code>,
	 * <code>LINE_START</code>, <code>LINE_END</code>,
	 * <code>FIRST_LINE_START</code>, <code>FIRST_LINE_END</code>,
	 * <code>LAST_LINE_START</code> and <code>LAST_LINE_END</code>. The baseline
	 * relative values are: <code>BASELINE</code>, <code>BASELINE_LEADING</code>,
	 * <code>BASELINE_TRAILING</code>, <code>ABOVE_BASELINE</code>,
	 * <code>ABOVE_BASELINE_LEADING</code>, <code>ABOVE_BASELINE_TRAILING</code>,
	 * <code>BELOW_BASELINE</code>, <code>BELOW_BASELINE_LEADING</code>, and
	 * <code>BELOW_BASELINE_TRAILING</code>. The default value is
	 * <code>CENTER</code>.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.ComponentOrientation
	 */
	private int anchor;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code fill} property.
	 * <p>
	 * This field is used when the component's display area is larger than the
	 * component's requested size. It determines whether to resize the component,
	 * and if so, how.
	 * <p>
	 * The following values are valid for <code>fill</code>:
	 *
	 * <ul>
	 * <li><code>NONE</code>: Do not resize the component.
	 * <li><code>HORIZONTAL</code>: Make the component wide enough to fill its
	 * display area horizontally, but do not change its height.
	 * <li><code>VERTICAL</code>: Make the component tall enough to fill its
	 * display area vertically, but do not change its width.
	 * <li><code>BOTH</code>: Make the component fill its display area entirely.
	 * </ul>
	 * <p>
	 * The default value is <code>NONE</code>.
	 * 
	 * @serial
	 * @see #clone()
	 */
	private int fill;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code insets} property.
	 * <p>
	 * This field specifies the external padding of the component, the minimum
	 * amount of space between the component and the edges of its display area.
	 * <p>
	 * The default value is <code>new Insets(0, 0, 0, 0)</code>.
	 * 
	 * @serial
	 * @see #clone()
	 */
	private Insets insets;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code ipadx} property.
	 * <p>
	 * This field specifies the internal padding of the component, how much space
	 * to add to the minimum width of the component. The width of the component
	 * is at least its minimum width plus <code>ipadx</code> pixels.
	 * <p>
	 * The default value is <code>0</code>.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#ipady
	 */
	private int ipadx;
	
	/**
	 * The built {@code GridBagConstraints}'s {@code ipady} property.
	 * <p>
	 * This field specifies the internal padding, that is, how much space to add
	 * to the minimum height of the component. The height of the component is at
	 * least its minimum height plus <code>ipady</code> pixels.
	 * <p>
	 * The default value is 0.
	 * 
	 * @serial
	 * @see #clone()
	 * @see java.awt.GridBagConstraints#ipadx
	 */
	private int ipady;
	
	/**
	 * @param gridx x position
	 * @param gridy y position
	 */
	public GridBagBuilder(final int gridx, final int gridy) {
		this(gridx, gridy, 1, 1);
	}
	
	/**
	 * @param gridx      x position
	 * @param gridy      y position
	 * @param gridwidth  number of cells occupied horizontally
	 * @param gridheight number of cells occupied vertically
	 */
	public GridBagBuilder(final int gridx, final int gridy, final int gridwidth,
			final int gridheight) {
		this(gridx, gridy, gridwidth, gridheight, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0);
	}
	
	/**
	 * @param gridx      x position
	 * @param gridy      y position
	 * @param gridwidth  number of cells occupied horizontally
	 * @param gridheight number of cells occupied vertically
	 * @param weightx
	 * @param weighty
	 * @param anchor
	 * @param fill
	 * @param insets
	 * @param ipadx
	 * @param ipady
	 */
	private GridBagBuilder(final int gridx, final int gridy, final int gridwidth,
			final int gridheight, final double weightx, final double weighty,
			final int anchor, final int fill, final Insets insets, final int ipadx,
			final int ipady) {
		super();
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
		this.weightx = weightx;
		this.weighty = weighty;
		this.anchor = anchor;
		this.fill = fill;
		this.insets = (Insets) insets.clone();
		this.ipadx = ipadx;
		this.ipady = ipady;
	}
	
	/**
	 * @return {@code GridBagConstraints} created by this builder
	 */
	public GridBagConstraints build() {
		return new GridBagConstraints(this.gridx, this.gridy, this.gridwidth,
				this.gridheight, this.weightx, this.weighty, this.anchor, this.fill,
				this.insets, this.ipadx, this.ipady);
	}
	
	/**
	 * @return anchor
	 */
	public int getAnchor() {
		return this.anchor;
	}
	
	/**
	 * @return fill
	 */
	public int getFill() {
		return this.fill;
	}
	
	/**
	 * @return gridheight
	 */
	public int getGridheight() {
		return this.gridheight;
	}
	
	/**
	 * @return gridwidth
	 */
	public int getGridwidth() {
		return this.gridwidth;
	}
	
	/**
	 * @return gridx
	 */
	public int getGridx() {
		return this.gridx;
	}
	
	/**
	 * @return gridy
	 */
	public int getGridy() {
		return this.gridy;
	}
	
	/**
	 * @return insets
	 */
	public Insets getInsets() {
		return this.insets;
	}
	
	/**
	 * @return ipadx
	 */
	public int getIpadx() {
		return this.ipadx;
	}
	
	/**
	 * @return ipady
	 */
	public int getIpady() {
		return this.ipady;
	}
	
	/**
	 * @return weightx
	 */
	public double getWeightx() {
		return this.weightx;
	}
	
	/**
	 * @return weighty
	 */
	public double getWeighty() {
		return this.weighty;
	}
	
	/**
	 * @param anchor anchor to set
	 */
	public GridBagBuilder setAnchor(final int anchor) {
		this.anchor = anchor;
		return this;
	}
	
	/**
	 * @param fill fill to set
	 */
	public GridBagBuilder setFill(final int fill) {
		this.fill = fill;
		return this;
	}
	
	/**
	 * @param insets insets to set
	 */
	public GridBagBuilder setInsets(final Insets insets) {
		this.insets = insets;
		return this;
	}
	
	/**
	 * @param ipadx ipadx to set
	 */
	public GridBagBuilder setIpadx(final int ipadx) {
		this.ipadx = ipadx;
		return this;
	}
	
	/**
	 * @param ipady ipady to set
	 */
	public GridBagBuilder setIpady(final int ipady) {
		this.ipady = ipady;
		return this;
	}
	
	/**
	 * @param weightx weightx to set
	 */
	public GridBagBuilder setWeightx(final double weightx) {
		this.weightx = weightx;
		return this;
	}
	
	/**
	 * @param weighty weighty to set
	 */
	public GridBagBuilder setWeighty(final double weighty) {
		this.weighty = weighty;
		return this;
	}
}
