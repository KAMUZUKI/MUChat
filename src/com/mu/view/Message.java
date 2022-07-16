package com.mu.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.client.chat_room.tools.MessageSave;
import com.mu.client.chat_room.view.Client_Frame;
import com.mu.dao.DBHelper;
import com.mu.utils.Constents;
import com.mu.utils.Utils;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * 消息列表
 * @author MUZUKI
 *
 */
public class Message extends Composite {
	private static Map<String, Object> map;
	private static Composite messageComposite;
	private static MessageSave messageSave = new MessageSave();
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Message(Composite parent, int style) {
		super(parent, SWT.NONE);
		setEnabled(true);
		setToolTipText("message");
		setLayout(null);
					
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.V_SCROLL);
		scrolledComposite.setLocation(10, 10);
		scrolledComposite.setSize(284, 484);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		messageComposite = new Composite(scrolledComposite, SWT.NONE);
		messageComposite.setLayout(new GridLayout(1, false));
					
		//TODO:动态地给界面加上n个 label
//		Control[] cs=messageComposite.getChildren();
//		if (cs!=null&&cs.length>0) {
//			for (int i = 0; i < cs.length; i++) {
//				Control control=cs[i];
//				if (control instanceof Group) {
//					
//				}
//				Group group=(Group)control;
//				control.dispose();
//			}
//		}
		
		createList();
		
		scrolledComposite.setContent(messageComposite);
		scrolledComposite.setMinSize(messageComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			
		
	}

	public static void createList() {
		map=new HashMap<String,Object>();
		
		DBHelper db = new DBHelper();
		System.out.println(Constents.user.getDeptname());
		String sql="SELECT ID,NAME,DEPTNAME,USERNAME,IMAGE,ISLOGIN from COM_PERSON,COM_DEPT WHERE COM_DEPT.DEPTNO=COM_PERSON.DEPTNO and COM_DEPT.DEPTNAME=?";
		List<Map<String,Object>> list = db.select(sql,Constents.user.getDeptname());
		
		Map<String,Object> map;
		for (int i=0;i<list.size();i++) {
			map = list.get(i);
			if(map.get("USERNAME").toString().equals(Constents.uname)) continue;
			if(map.get("ISLOGIN").toString().equals("0")) continue;
			createPerson(map);
		}
		for (int i=0;i<list.size();i++) {
			map = list.get(i);
			if(map.get("USERNAME").toString().equals(Constents.uname)) continue;
			if(map.get("ISLOGIN").toString().equals("1")) continue;
			createPerson(map);
		}
	}
	
	public static void createPerson(Map<String,Object> map) {
		String usernameString=map.get("USERNAME").toString();
		Group group = new Group(messageComposite, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_group.heightHint = 74;
		group.setLayoutData(gd_group);
		group.setLayout(null);
		
		Label avatarLabel = new Label(group, SWT.NONE);
		avatarLabel.setBounds(9, 20, 60, 60);
		avatarLabel.setText("\u5934\u50CF");
		
		Label message = new Label(group, SWT.NONE);
		message.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		message.setBounds(75, 43, 199, 37);
		message.setText("\u4FE1\u606F");
		
		//加载最后一条聊天记录
		String lastRowMessage = messageSave.getLastMessage(usernameString,4);
		String tmpStr = lastRowMessage.replace(usernameString + ":   ", "");
		message.setText(tmpStr);
		
		Label username = new Label(group, SWT.NONE);
		username.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		username.setBounds(75, 20, 199, 17);
		String status = map.get("ISLOGIN").toString();
		String isOnline;
		if(status.equals("1")) {
			isOnline="[在线]";
			avatarLabel.setImage(SWTResourceManager.getImage(MainWindow.class,Utils.pathInvert(map.get("IMAGE").toString())));
		}else {
			isOnline="[离线]";
			ImageData imageData = Utils.setImageAlpha(SWTResourceManager.getImage(MainWindow.class,Utils.pathInvert(map.get("IMAGE").toString())));
			avatarLabel.setImage(new Image(avatarLabel.getDisplay(), imageData));
		}
		username.setText(usernameString + " " + map.get("DEPTNAME").toString() + " " + isOnline);
		
		username.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
				    String[] tmpStr = {" ",usernameString,isOnline};
				    Client_Frame.openClient(tmpStr);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	public static void refresh() {
		Control[] cs=messageComposite.getChildren();
		if (cs!=null&&cs.length>0) {
			for (int i = 0; i < cs.length; i++) {
				Control control=cs[i];
				if (control instanceof Group) {
				}
				Group group=(Group)control;
				control.dispose();
			}
		}
		createList();
		//重新布局
		messageComposite.layout();
	}
	
	
	@Override
	protected void checkSubclass() {
		
	}
}
