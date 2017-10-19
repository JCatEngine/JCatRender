package JCat.Graphics.Shape;



import JCat.Display.AnchorAble;
import JCat.Display.Calculation.Bound;
import JCat.Graphics.Color;
import JCat.Interaction.InteractiveObject;
import JCat.Utils.MathTool;

public class BaseShape extends InteractiveObject implements AnchorAble{

	private Color color=Color.BISQUE;

	@Override
	protected void updateLocalBound() {

		
		//for displayobject,bound is base on origin width,height,and rotation,scale
		Bound bound=new Bound();
		
		//localbound x,y always is 0
		bound.minX=0;
		bound.minY=0;
		bound.maxX=width+bound.minX;
		bound.maxY=height+bound.minY;
		
		localBound=bound;
	

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * anchor the rotate center
	 * 0,0 equal left-top corner of the object
	 * 1,1 equal right-down corner of the object
	 */
	protected double anchorX;
	protected double anchorY;


	public double getAnchorX() {
		return anchorX;
	}


	public void setAnchorX(double anchorX) {
		MathTool.checkRange(0, 1, anchorX);
		this.anchorX = anchorX;
	}


	public double getAnchorY() {
		return anchorY;
	}


	public void setAnchorY(double anchorY) {
		MathTool.checkRange(0, 1, anchorY);
		this.anchorY = anchorY;
	}
	
	
	/**
	 * return the width before any rotate and scale
	 * @return
	 */
	public double getRawWidth() {

		return width;
	}

	/**
	 * return the height before any rotate and scale
	 * @return
	 */
	public double getRawHeight() {

		return height;
	}
		

}
