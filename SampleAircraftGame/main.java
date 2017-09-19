package SampleAircraftGame;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.lang.model.element.VariableElement;

import JGame.JGame;
import JGame.SwingStage;
import JGame.Common.Ticker;
import JGame.Common.Ticker.OnResponceListener;
import JGame.RenderSystem.RenderSystem;
import JGame.RenderSystem.Display.EventDispatcher;
import JGame.RenderSystem.Display.Stage;
import SampleAircraftGame.Util.Util;

public class main {

	public static void main(String[] args) throws IOException {
		
		
		RenderSystem system=new RenderSystem(600, 800);
		Stage stage=system.getStage();

		Aircraft aircraft=new Aircraft();
		
		system.render();
		
		
//		jGame.addObject(aircraft);
//		jGame.addToRoot(aircraft);
//		aircraft.x=300;
//		aircraft.y=600;
//		aircraft.width=50;
//		aircraft.height=50;
//		aircraft.getCom_anime().addImage(Util.LoadImg("aircraft.png"));
//		
//		
//		//添加到顶层渲染中 这和上面的add不一样 上面的只是为了一种资源绑定关系
//		//而也不是所有的对象都在顶层渲染中 
//		
//		
//		Money money=new Money();
//		jGame.addObject(money);
//		jGame.addToRoot(money);
//		money.x=100;
//		money.y=100;
//		money.getCom_anime().CreateAnime("test", 12, Util.LoadImg("testAnime1.png"));
//		money.getCom_anime().addKeyFrame("test", 4, Util.LoadImg("testAnime2.png"));
//		money.getCom_anime().addKeyFrame("test", 7, Util.LoadImg("testAnime3.png"));
//		money.getCom_anime().addKeyFrame("test", 10, Util.LoadImg("testAnime4.png"));
//		
//		
//		jGame.getRoot().getCom_anime().addImage(Util.LoadImg("back.jpeg"));
	}
}
