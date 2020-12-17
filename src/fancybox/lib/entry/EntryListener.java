package fancybox.lib.entry;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EntryListener implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
		((EntryOnBar)e.getSource()).plugin.main();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
