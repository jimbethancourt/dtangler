//This product is provided under the terms of EPL (Eclipse Public License) 
//version 2.0.
//
//The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.swingui.windowmanager;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

public interface SwingView {

	Dimension getPreferredSize();

	JComponent getViewComponent();

	JMenuBar getMenuBar();

	String getTitle();

	void setWindowInteractionProvider(
			WindowInteractionProvider windowInteractionProvider);

	Component getFirstComponentToFocus();

}
