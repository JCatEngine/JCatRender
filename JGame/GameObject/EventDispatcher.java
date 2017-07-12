package JGame.GameObject;


import JGame.JGame;
import JGame.GameObject.Component.Event.EventHandler;
import JGame.GameObject.Component.Event.EventType;

/**
 * 最基础的游戏对象
 * @author Administrator
 *
 */
public class EventDispatcher {

	/**
	 * 组件 事件系统
	 */
	protected EventHandler com_eventhandler;
	/**
	 * 
	 */
	private JGame game;
	
	
	
	public EventDispatcher(JGame game) {
		this.game = game;
		initComponent();
	}
	
	

	private void initComponent() {
		
		this.com_eventhandler=new EventHandler(this);
		//注册消息处理器
		game.getEventManager().registerEventdealer(this);	
		
		
	
	}



	public EventHandler getCom_eventhandler() {
		return com_eventhandler;
	}


	
	
}
