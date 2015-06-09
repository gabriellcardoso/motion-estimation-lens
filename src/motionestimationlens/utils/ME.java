package motionestimationlens.utils;

public class ME {

	public static final String HD = "1280x720";
	public static final String FULL_HD = "1920x1080";
	public static final String QHD = "2560x1440";
	public static final String UHD = "3840x2160";
	
	public static final String S_444 = "4:4:4";
	public static final String S_422 = "4:2:2";
	public static final String S_420 = "4:2:0";
	public static final String S_411 = "4:1:1";
	public static final String S_400 = "4:0:0";
	
	public static final String FS = "Full Search";
	public static final String DS = "Diamond Search";
	public static final String TSS = "Three Step Search";

	public static final String SA_16 = "16x16";
	public static final String SA_32 = "32x32";
	public static final String SA_64 = "64x64";
	public static final String SA_128 = "128x128";
	public static final String SA_256 = "256x256";
	
	public static final String BS_8 = "8x8";
	public static final String BS_16 = "16x16";
	public static final String BS_32 = "32x32";
	public static final String BS_64 = "64x64";
	
	public static final int FIRST_FRAME = 0;
	public static final int SECOND_FRAME = 1;
	public static final int FIRST_BLOCK_X = 0;
	public static final int FIRST_BLOCK_Y = 0;
	
	public static final String[] RESOLUTION_ITEMS = { UHD, QHD, FULL_HD, HD };
	public static final String[] SAMPLING_ITEMS = { S_444, S_422, S_420, S_411, S_400 };
	public static final String[] ALGORITHM_ITEMS = { FS, DS, TSS };
	public static final String[] SEARCH_AREA_ITEMS = { SA_16, SA_32, SA_64, SA_128, SA_256 };
	public static final String[] BLOCK_SIZE_ITEMS = { BS_8, BS_16, BS_32, BS_64 };
	
	public static final String MAIN_PANEL = "Main Panel";
	public static final String SETUP_PANEL = "Setup Panel";
	
	public static int getWidth(String resolution) {
		String width = resolution.split("x")[0];
		return Integer.parseInt(width);
	}
	
	public static int getHeight(String resolution) {
		String height = resolution.split("x")[1];
		return Integer.parseInt(height);
	}
	
	public static int getX(String vector) {
		String x = vector.split(",")[0];
		return Integer.parseInt(x);
	}
	
	public static int getY(String vector) {
		String y = vector.split(",")[1];
		return Integer.parseInt(y);
	}
	
	public static int byteToInt(byte b) {
		return (int) b & 0xFF;
	}
	
}
