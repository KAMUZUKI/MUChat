package com.mu.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.client.chat_room.backstage.ClientConServer;
import com.mu.client.chat_room.tools.MessageSave;
import com.mu.client.chat_room.view.ClientFrame;
import com.mu.client.chat_room.view.Client_Frame;
import com.mu.common.User;
import com.mu.config.Config;
import com.mu.dao.DBHelper;
import com.mu.server.chat_server.backstage.DatabaseManage;
import com.mu.utils.Constents;
import com.mu.utils.SwtUtils;
import com.mu.utils.Utils;

import oracle.net.aso.s;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.RGBA;

/**
 * 主窗口
 * @author MUZUKI
 *
 */
public class MainWindow {

	protected Shell shell;
	private static Display display;
	private Text searchText;
	private static MessageSave messageSave = new MessageSave();
	private static DBHelper db = new DBHelper();
	private static Text announcetionText;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		db=new DBHelper();
		db.updateStatus(Constents.uname,0);
		System.out.println("系统关闭");
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.MIN);
		shell.setAlpha(Config.WINDOW_ALPHA);
		shell.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/utils/favicon.ico"));
		shell.setSize(320, 730);
		shell.setText("\u5185\u90E8\u901A\u8BAF\u5BA2\u6237\u7AEF");
		shell.setLayout(null);
//		shell.setBackgroundImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/image/background/4.bmp"));
		// 窗口居中
		SwtUtils.centerShell(display, shell);

		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		sashForm.setFont(SWTResourceManager.getFont("Microsoft JhengHei UI", 14, SWT.NORMAL));
		sashForm.setBounds(0, 0, 304, 691);
		sashForm.setBackgroundMode(SWT.INHERIT_FORCE);
		ImageData imageData = Utils.setImageAlpha(SWTResourceManager.getImage(MainWindow.class, "/com/mu/image/background/4.bmp"));
		sashForm.setBackgroundImage(new Image(display, imageData));
		Composite header = new Composite(sashForm, SWT.NONE);
		
		SashForm headerSashForm = new SashForm(header, SWT.VERTICAL);
		headerSashForm.setBounds(0, 0, 303, 110);
		
		Composite topComposite = new Composite(headerSashForm, SWT.NONE);
		topComposite.setLayout(new GridLayout(2, false));
		
		//header组
		Group group = new Group(topComposite, SWT.SHADOW_OUT);
		group.setLayout(null);
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_group.heightHint = 68;
		gd_group.widthHint = 158;
		group.setLayoutData(gd_group);
		
		//用户名
		Label usernameLabel = new Label(group, SWT.NONE);
		usernameLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		usernameLabel.setAlignment(SWT.CENTER);
		usernameLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		usernameLabel.setBounds(61, 21, 100, 17);
		usernameLabel.setText(Constents.uname);
		
		//部门名
		Label deptLabel = new Label(group, SWT.NONE);
		deptLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		deptLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		deptLabel.setAlignment(SWT.CENTER);
		deptLabel.setBounds(61, 44, 100, 17);
		deptLabel.setText(Constents.user.getDeptname());
		
		//头像
		Label avatarLabel = new Label(group, SWT.NONE);
		avatarLabel.setBounds(15, 21, 40, 40);
		avatarLabel.setImage(SWTResourceManager.getImage(MainWindow.class,Utils.pathInvert(Constents.user.getImage())));
		
		//公告
		announcetionText = new Text(topComposite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		announcetionText.setBackground(new Color(new RGBA(230,230,230,1)));
		announcetionText.setTouchEnabled(true);
		announcetionText.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.NORMAL));
		announcetionText.setToolTipText("\u7CFB\u7EDF\u901A\u77E5");
		GridData gd_announcetionText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_announcetionText.heightHint = 63;
		announcetionText.setLayoutData(gd_announcetionText);
		
		// 搜索Composite
		Composite searchComposite = new Composite(headerSashForm, SWT.NONE);
		searchText = new Text(searchComposite,SWT.BORDER);
		searchText.setBounds(0, 0, 245, 23);
		Button searchButton = new Button(searchComposite, SWT.NONE);

		searchButton.setBounds(244, 0, 59, 23);
		searchButton.setText("\u641C\u7D22");
		headerSashForm.setWeights(new int[] {4, 1});
		
		Composite body = new Composite(sashForm, SWT.NONE);
		body.setLayout(null);
		
		SashForm bodySashForm = new SashForm(body, SWT.VERTICAL);
		bodySashForm.setBounds(1, 1, 302, 537);
		
		Composite selectComposite = new Composite(bodySashForm, SWT.NONE);
		selectComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button messageButton = new Button(selectComposite, SWT.NONE);
		messageButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/chat.png"));
		
		messageButton.setText("\u4FE1\u606F");
		
		Button linkedButton = new Button(selectComposite, SWT.NONE);
		linkedButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/linked.png"));
		
		linkedButton.setText("\u8054\u7CFB\u4EBA");
		
		Composite messageContain = new Composite(bodySashForm, SWT.NONE);
//		messageContain.setBackgroundMode(SWT.INHERIT_FORCE);
		//创建布局方式
		StackLayout s1=new StackLayout();
		messageContain.setLayout(s1);//设置当前窗口的布局为  stacklayout
		//创建两块要用的composite
		Message message = new Message(messageContain, SWT.NO_BACKGROUND);
		Linked linked = new Linked(messageContain, SWT.BACKGROUND); 
		//指定当前显示的面板
		s1.topControl=message;
				
		message.setBounds(0, 1, 304, 501);
		message.setLayout(new FillLayout(SWT.HORIZONTAL));
		bodySashForm.setWeights(new int[] {10, 200});
		
		Composite bottom = new Composite(sashForm, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(7, false);
		gl_bottom.marginHeight = 1;
		bottom.setLayout(gl_bottom);
		
		Button settingButton = new Button(bottom, SWT.NONE);
		
		settingButton.setToolTipText("\u8BBE\u7F6E");
		settingButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/setting.png"));
		
		Button logoutButton = new Button(bottom, SWT.NONE);
		
		logoutButton.setToolTipText("\u9000\u51FA");
		logoutButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/logout.png"));
		logoutButton.setBounds(0, 0, 36, 27);
		
		Button moreButton = new Button(bottom, SWT.NONE);
		
		moreButton.setToolTipText("\u6253\u5F00\u7FA4\u804A");
		moreButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/add.png"));
		
		Button refreshButton = new Button(bottom, SWT.NONE);
		refreshButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));

		refreshButton.setToolTipText("\u5237\u65B0\u5217\u8868\u72B6\u6001");
		refreshButton.setImage(SWTResourceManager.getImage(MainWindow.class, "/com/mu/view/pic/refresh.png"));
		new Label(bottom, SWT.NONE);
		
		Button onlineButton = new Button(bottom, SWT.NONE);
		
		onlineButton.setText("\u5728\u7EBF");
		
		Button offlineButton = new Button(bottom, SWT.NONE);
		
		offlineButton.setText("\u79BB\u7EBF");
		
		updateAnnouncement();
		
		//更新列表
		refreshButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Message.refresh();
				Linked.refresh();
				updateAnnouncement();
			}
		});
		
		//在线
		onlineButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				db.updateStatus(Constents.uname,1);
			}
		});
		
		//离线
		offlineButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				db.updateStatus(Constents.uname,0);
			}
		});
		
		//群聊
		moreButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//启动登陆界面
//				Login_Frame LoginFrame = new Login_Frame();
				Login.setAvisiable(true);
			}
		});
		
		// 搜索
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String searchString = searchText.getText();
				String online = Constents.online;
//				String[] onlineStrings = online.split(" ");
				if(searchText.equals(Constents.uname)) {
					MessageDialog.openError(shell, "错误", "不要选择自己");
					return;
				}
				if (online.contains(searchString)) {
					String[] tmpStr = {" ",searchString,"[在线]"};
					Client_Frame.openClient(tmpStr);
				} else {
					if (messageSave.existMessage(searchString)) {
						ClientFrame clientFrame = new ClientFrame(Constents.uname, searchString, null);
						clientFrame.jta2.setEditable(false);
						clientFrame.jb1.setEnabled(false);
						clientFrame.jb2.setEnabled(false);
						clientFrame.jb3.setEnabled(false);
						JOptionPane.showMessageDialog(clientFrame,"正在查看聊天记录");
					}else {
						MessageDialog.openError(shell, "搜索错误", "搜索用户不在线或不存在，且没有聊天记录");
					}
					return;
				}
			}
		});
		
		// 信息查看
		messageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				s1.topControl=message;
				messageContain.layout();
			}
		});
		
		// 联系人查看
		linkedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				s1.topControl=linked;
				messageContain.layout();
			}
		});
		
		//设置
		settingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//
				Setting setting = new Setting(shell, SWT.NO_TRIM|SWT.APPLICATION_MODAL); 
				setting.open();
			}
		});
		
		//退出
		logoutButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean r = MessageDialog.openConfirm(shell, "请确认", "确认退出系统吗?");
				if (r) {
					System.exit(0);
				}
				
			}
		});
		sashForm.setWeights(new int[] {111, 539, 35});
	}
	
	//更新公告
	public static void updateAnnouncement() {
		Runnable runnable= new Runnable(){
			public void run(){
				String str = messageSave.loadLastMessage("System");
				String lastMessage = "";
				if (str!="") {
					lastMessage = str;
				}else {
					lastMessage="暂无系统消息！";
				}
				try {
					announcetionText.setText(lastMessage);
				} catch (Exception e) {
					db.updateStatus(Constents.uname, 0);
				}
			}
		};
		display.syncExec(runnable); // 关键在这一句上（同步调用，等待主界面线程处理完成之后）
	}
}
