package lt.buzzard.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureManager {
	
	private Texture texture;
	private static Map<String, Texture> textures;
	private Map<String, String> texturePaths;
	
	public TextureManager(){
		TextureManager.textures = new HashMap<String, Texture>();
		this.texturePaths = new HashMap<String, String>();
	}
	
	public TextureManager(Map<String, String> texturePaths){
		TextureManager.textures = new HashMap<String, Texture>();
		this.texturePaths = new HashMap<String, String>();
		
		this.texturePaths = texturePaths;
		
		reloadTextures();
	}
	
	public void reloadTextures(){
		textures.clear();
		for(String key : texturePaths.keySet()){
			try {
				texture = TextureLoader.getTexture("PNG",
						getClass().getResourceAsStream(texturePaths.get(key)));
				textures.put(key, texture);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("file missing " + texturePaths.get(key));
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("I/O exception on " + texturePaths.get(key));
			}
		}
	}
	
	public void clearBlockTextures(){
		texturePaths.clear();
	}
	
	public static Texture getTexture(String name){
		return textures.get(name);
	}
	
	public void addTexture(String name, String path){
		texturePaths.put(name, path);
	}
	
	public Map<String, Texture> getTextures() {
		return textures;
	}

	public void setTextures(Map<String, Texture> textures) {
		TextureManager.textures = textures;
	}

	public Map<String, String> getTextureFiles() {
		return texturePaths;
	}

	public void setTextureFiles(Map<String, String> texturePaths) {
		this.texturePaths = texturePaths;
	}
	
}
