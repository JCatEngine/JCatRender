package JCat.RenderSystem.Display;

import java.security.acl.LastOwnerException;
import java.util.function.Function;

import JCat.GameCore.GameObject.Component.Anime.AnimeComponent;
import JCat.RenderSystem.Display.Calculation.Bound;
import JCat.RenderSystem.Display.Calculation.Transform;
import JCat.RenderSystem.Math.Matrix;
import JCat.RenderSystem.Math.Rect;
import JCat.RenderSystem.Math.Vector2;

/**
 * baseobject that can be render to screen
 * @author Administrator
 *
 */
public abstract class DisplayObject extends EventDispatcher{

	/**
	 * x position of objecy
	 */
	public double x;
	/**
	 * y position of object
	 */
	public double y;
	/**
	 * the origin width(before scale and rotate,be notice rotation also change the final width) 
	 */
	protected double width;
	/**
	 * the origin height(before scale and rotate,be notice rotation also change the final height) 
	 */
	protected double height;
	/**
	 * The rotation value of the object, in angle
	 */
	public double rotation;
	/**
	 * scale x
	 */
	protected double scaleX=1;
	/**
	 * scale y
	 */
	protected double scaleY=1;
	/**
	 * alpha 0~1
	 */
	public double alpha=1;
	/**
	 * name of the object
	 */
	public String name;
	/**
	 * is this object visible?
	 */
	public boolean visible=true;
	/**
	 * stage reference
	 */
	public Stage stage;
	/**
	 * localTransform,will be recalcued per frame
	 */
	private Transform localTransform;
	/**
	 * worldTransform,will be recalcued per frame
	 */
	private Transform worldTransform;
	/**
	 * parent
	 */
	public DisplayObjectContainer parent;
	/**
	 * world alpha of the object
	 * it's will be auto updated before render
	 */
	private double worldAlpha=1;
	/**
	 * localBound
	 */
	protected Bound localBound;
	
	

	public double getWidth() {
		
		return getBound(parent).width;
	}


	public void setWidth(double width) {
	
		
		//if it's width==0,change it's width was useless
		if(getWidth()==0)
		{
			return;
		}
		else
		{
		
			scaleX=(width/(getWidth()/scaleX));
			
		}
		
	}


	public double getHeight() {
		
		return getBound(parent).height;
	}


	
	public void setHeight(double height) {
		
		//if it's height==0,change it's height was useless
		if(getHeight()==0)
		{
			return;
		}
		else
		{
			scaleY=(height/(getHeight()/scaleY));
		}
	}


	public double getScaleX() {
		return scaleX;
	}


	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}


	public double getScaleY() {
		return scaleY;
	}


	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}



	public DisplayObject() {
		
		updateTransform();
		

	}

	
	/**
	 * 
	 * @return
	 */
	public Transform getLocalTransform()
	{
		updateLocalTransform();
		return localTransform;
		
	}
	
	private void updateLocalTransform() {
		
		//update localTransform
		Transform transform=new Transform(x,y,rotation,scaleX,scaleY);
		this.localTransform=transform;
		
	}


	/**
	 * 
	 * @return
	 */
	public Transform getWorldTransform()
	{
		//update transform to new and return
		//consider updateTransform is a complex calculation
		//This may not be a good solution because the transform may not change(same as last update)
		//An alternative method is use id
		//every time it's x,y and other field change,add the id
		//if the id don't equal old id,it means the transform exactly change,then update it
		//but for simplicity,i just update it every call;

		updateTransform();
		return worldTransform;
		
	}

	void updateAlpha()
	{
		
		if(parent!=null)
		{
			this.worldAlpha=parent.getWorldAlpha()*this.alpha;
		}
		else
		{
			this.worldAlpha=this.alpha;
		}
		
	}

	
	
	public double getWorldAlpha() {
		
		updateAlpha();
		return worldAlpha;
	}


	/**
	 * auto called before render
	 */
	void updateTransform() {
		
		updateLocalTransform();
		
		
		//update worldTransform
		if(parent!=null)
		{
			
			Transform parentTransform=parent.getWorldTransform();
			
			//though these value can just decompose from matrix in the transform
			//but i konw little about matrix calculation,
			//so i just use this way to calculate;
			this.worldTransform=localTransform.clone();
			this.worldTransform.x+=parentTransform.x;
			this.worldTransform.y+=parentTransform.y;
			this.worldTransform.rotation+=parentTransform.rotation;
			this.worldTransform.scaleX*=parentTransform.scaleX;
			this.worldTransform.scaleY*=parentTransform.scaleY;
			
			//also append the matrix to ensure data are consistent with
			//matrix 
			this.worldTransform.append(parentTransform);
			
		}
		else
		{
			this.worldTransform=this.localTransform.clone();
		}
		
	}
	
	
	/**
	 * update local bound
	 */
	abstract void updateBound();
	
	
	/**
	 * 
	 * @param displayObject 
	 * @return
	 */
	public Rect getBound(DisplayObject displayObject)
	{
		//update localBound
		updateBound();
		
		Bound bound=new Bound();
		
		Rect rect=localBound.toRect();
		
		//transform rect to target Coordinate
		
		Transform transform=displayObject.getLocalTransform();
	
		Vector2 vector1=rect.getLeftTopPoint();
		vector1=localToGlobal(vector1);
		vector1=displayObject.globalToLocal(vector1);
		bound.addVector2(vector1);
		
		Vector2 vector2=rect.getRightTopPoint();
		vector2=localToGlobal(vector2);
		vector2=displayObject.globalToLocal(vector2);
		bound.addVector2(vector2);

		Vector2 vector3=rect.getLeftDownPoint();
		vector3=localToGlobal(vector3);
		vector3=displayObject.globalToLocal(vector3);
		bound.addVector2(vector3);
		
		Vector2 vector4=rect.getRightDownPoint();
		vector4=localToGlobal(vector4);
		vector4=displayObject.globalToLocal(vector4);
		bound.addVector2(vector4);
		
		
		
		return bound.toRect();
	}


	/**
	 * 
	 * @param vector2 the point in world coordinate
	 * @return 
	 */
	public Vector2 globalToLocal(Vector2 vector2)
	{
		return getWorldTransform().applyInverse(vector2);	
	}

	/**
	 * 
	 * @param displayObject
	 * @return
	 */
	public Boolean hitTestObject(DisplayObject displayObject)
	{
		Rect rect=displayObject.getBound(stage);
		Rect rect2=this.getBound(stage);
		
		return rect.hitTest(rect2);
		
	}
	
	/**
	 * 
	 * @param vector2
	 * @return
	 */
	public Boolean hitTestPoint(Vector2 vector2)
	{
		return getBound(stage).contains(vector2);
		
	}
	
	/**
	 * 
	 * @param vector2
	 * @return
	 */
	public Vector2 localToGlobal(Vector2 vector2)
	{
		return getWorldTransform().apply(vector2);
	}


	public void updateStage(Stage stage) {
		
		this.stage=stage;
		if(this instanceof DisplayObjectContainer)
		{
			DisplayObjectContainer container=(DisplayObjectContainer) this;
			for (DisplayObject displayObject : container.getChilds()) {
				
				displayObject.updateStage(stage);
			}
		}
		
	}
	

	
	
}