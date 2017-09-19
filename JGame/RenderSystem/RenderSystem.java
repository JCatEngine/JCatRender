package JGame.RenderSystem;


import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import JGame.RenderSystem.Canvas.Canvas;
import JGame.RenderSystem.Canvas.CanvasType;
import JGame.RenderSystem.Canvas.SwingCanvas;
import JGame.RenderSystem.Display.Bitmap;
import JGame.RenderSystem.Display.DisplayObject;
import JGame.RenderSystem.Display.DisplayObjectContainer;
import JGame.RenderSystem.Display.EventDispatcher;
import JGame.RenderSystem.Display.Stage;
import JGame.RenderSystem.Render.Renderer;
import JGame.RenderSystem.Textures.Texture;

public class RenderSystem extends EventDispatcher{

	/**
	 * draw texture on canvas
	 */
	private Canvas canvas;
	/**
	 * root display object
	 */
	private Stage root;
	/**
	 * renderer
	 */
	private Renderer renderer;


	
	public RenderSystem(int width, int height,CanvasType type) 
	{
		
		this.root=new Stage();

		this.renderer=new Renderer();
		
		if(type==CanvasType.swing)
		{

			this.canvas=new SwingCanvas(width, height);
		}
		else
		{
			throw new RuntimeException("unsupport canvas currently");
		}
		
	}

	
	public RenderSystem(int width, int height) {
		
		//use swing as default canvas
		this(width,height,CanvasType.swing);
		
	}


	/**
	 * render once
	 */
	public void render()
	{
		
		renderObject(root);
		
		
	}
	
	private void renderObject(DisplayObject displayObject) {

		displayObject.updateTransform();
		
		if(displayObject instanceof Bitmap)
		{
			Bitmap bitmap=(Bitmap) displayObject;
			Texture texture=bitmap.getTexture();
		}
		
		if(displayObject instanceof DisplayObjectContainer)
		{
			DisplayObjectContainer displayObjectContainer=(DisplayObjectContainer) displayObject;
			for(DisplayObject child:displayObjectContainer.getChilds())
			{
				renderObject(child);
			}
		}
		
	
	}


	public Canvas getCanvas() {
		return canvas;
	}
	

	public Stage getStage() {
		return root;
	}


	public Renderer getRenderer() {
		return renderer;
	}

	
	
}
