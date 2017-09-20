package JGame.RenderSystem.Display;

import JGame.RenderSystem.Display.Calculation.Bound;
import JGame.RenderSystem.Textures.Texture;

public class Bitmap extends DisplayObject{

	private Texture texture;

	public Bitmap(Texture texture) {
		this.texture=texture;
		width=texture.getWidth();
		height=texture.getHeight();
	}

	public Texture getTexture() {
		// TODO Auto-generated method stub
		return texture;
	}

	@Override
	void updateBound() {
		
		//for displayobject,bound is base on origin width,height,and rotation,scale
		Bound bound=new Bound();
		
		//localbound x,y always is 0
		bound.x=0;
		bound.y=0;
		bound.width=width;
		bound.height=height;
		
		localBound=bound;
		
	}

	

}
