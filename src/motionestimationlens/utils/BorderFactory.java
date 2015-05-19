package motionestimationlens.utils;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public final class BorderFactory {
	
	public final static Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
	
	public final static Border createTitledBorder(String title, Border border) {
		return javax.swing.BorderFactory.createCompoundBorder(
			new TitledBorder(title),
			border
		);
	}
	
	public final static Border createEmptyTitledBorder(String title, Border border) {
		return javax.swing.BorderFactory.createCompoundBorder(
			new TitledBorder(EMPTY_BORDER, title),
			border
		);
	}

}
