//This product is provided under the terms of EPL (Eclipse Public License) 
//version 2.0.
//
//The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.swingui.aboutinfodisplayer.impl;

import static org.junit.Assert.assertNull;

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.hjug.dtangler.swingui.windowmanager.MockWindowManager;
import org.junit.Before;
import org.junit.Test;

public class AboutInfoFeatureTest {

	private MockWindowManager windowManager;
	private AboutInfoViewDriver view;

	@Before
	public void setUp() {
		windowManager = new MockWindowManager();
		new AboutInfoDisplayerImpl(windowManager).displayAboutInfo();
		view = new AboutInfoViewDriver(windowManager.getLastShownView());
	}

	@Test
	public void testCloseOnOkButtonClicked() {
		view.okButton.click();
		assertNull(windowManager.getLastShownView());
	}

	@Test
	public void testCloseOnEnterPressed() {
		view.pressKey(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		assertNull(windowManager.getLastShownView());
	}

	@Test
	public void testCloseOnEscapePressed() {
		view.pressKey(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		assertNull(windowManager.getLastShownView());
	}

}
