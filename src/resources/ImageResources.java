package resources;

public class ImageResources {
	
	//to get directory path for images
	public String getImagePath() {
		return getClass().getResource(".").getPath();
	}
	
	public static void main(String[] args) {
		ImageResources IR = new ImageResources();
		System.out.println(IR.getImagePath());
	}

}
