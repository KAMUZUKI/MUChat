package com.mu.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.io.File;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.dao.DBHelper;
import com.mu.client.chat_room.backstage.ClienManage;
import com.mu.client.chat_room.backstage.ClientConServer;
import com.mu.client.chat_room.tools.ClientThreadCollection;
import com.mu.client.chat_room.tools.ManageClientCollction;
import com.mu.client.chat_room.view.Client_Frame;
import com.mu.common.MessageType;
import com.mu.common.User;
import com.mu.config.Config;
import com.mu.utils.Constents;
import com.mu.utils.IniFileOpration;
import com.mu.utils.MD5;
import com.mu.utils.SwtUtils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

/**
 * ��¼����  
 * �迪��server.chat_server.view����Server_Frame����
 * @author MUZUKI
 */
public class Login {
	protected Display display;
	protected Shell shell;
	private Text username;
	private Text password;
	public static ClienManage cm;
	public static User user;
	private static Client_Frame client;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
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
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.MIN);
		
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setText("\u767B\u5F55");
		shell.setImage(SWTResourceManager.getImage(Login.class, "/com/mu/utils/favicon.ico"));
		shell.setSize(532, 375);
		// ���ھ���
		SwtUtils.centerShell(display, shell);
		shell.setLayout(new FormLayout());

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		composite.setLayout(new FormLayout());
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		fd_composite.bottom = new FormAttachment(100);
		fd_composite.right = new FormAttachment(100);
		composite.setLayoutData(fd_composite);
		composite.setBackgroundImage(SWTResourceManager.getImage(Login.class, "/com/mu/image/background/loginBack.png"));

		Label label = new Label(composite, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.bottom = new FormAttachment(0, 103);
		fd_label.top = new FormAttachment(0, 63);
		fd_label.left = new FormAttachment(0, 157);
		label.setLayoutData(fd_label);
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 20, SWT.BOLD));
		label.setText("\u4F01\u4E1A\u5185\u90E8\u901A\u8BAF\u7CFB\u7EDF");

		Label usernameLabel = new Label(composite, SWT.CENTER);
		FormData fd_usernameLabel = new FormData();
		fd_usernameLabel.top = new FormAttachment(0, 139);
		fd_usernameLabel.left = new FormAttachment(0, 157);
		usernameLabel.setLayoutData(fd_usernameLabel);
		usernameLabel.setImage(SWTResourceManager.getImage(Login.class, "/com/mu/view/pic/loginAdmin.png"));
		usernameLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		usernameLabel.setText("\u8D26\u53F7:");

		username = new Text(composite, SWT.BORDER);
		FormData fd_username = new FormData();
		fd_username.top = new FormAttachment(usernameLabel, 1, SWT.TOP);
		fd_username.right = new FormAttachment(label, 0, SWT.RIGHT);
		fd_username.left = new FormAttachment(0, 209);
		username.setLayoutData(fd_username);
		username.setText("zhangsan");

		Label passwordLabel = new Label(composite, SWT.NONE);
		FormData fd_passwordLabel = new FormData();
		fd_passwordLabel.top = new FormAttachment(0, 178);
		fd_passwordLabel.left = new FormAttachment(0, 157);
		passwordLabel.setLayoutData(fd_passwordLabel);
		passwordLabel.setImage(SWTResourceManager.getImage(Login.class, "/com/mu/view/pic/loginPassword.png"));
		passwordLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		passwordLabel.setText("\u5BC6\u7801:");

		password = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		FormData fd_password = new FormData();
		fd_password.bottom = new FormAttachment(0, 205);
		fd_password.right = new FormAttachment(0, 373);
		fd_password.top = new FormAttachment(0, 179);
		fd_password.left = new FormAttachment(0, 209);
		password.setLayoutData(fd_password);
		password.setText("123");

		Button loginButton = new Button(composite, SWT.NONE);
		FormData fd_loginButton = new FormData();
		fd_loginButton.bottom = new FormAttachment(0, 280);
		fd_loginButton.right = new FormAttachment(0, 360);
		fd_loginButton.top = new FormAttachment(0, 240);
		fd_loginButton.left = new FormAttachment(0, 275);
		loginButton.setLayoutData(fd_loginButton);
		loginButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		loginButton.setText("\u767B\u5F55");

		Button exitButton = new Button(composite, SWT.NONE);
		FormData fd_exitButton = new FormData();
		fd_exitButton.bottom = new FormAttachment(0, 280);
		fd_exitButton.right = new FormAttachment(0, 242);
		fd_exitButton.top = new FormAttachment(0, 240);
		fd_exitButton.left = new FormAttachment(0, 157);
		exitButton.setLayoutData(fd_exitButton);
		exitButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		exitButton.setText("\u9000\u51FA");

		loadConfig();
		
		shell.setAlpha(Config.WINDOW_ALPHA);
		
		// ��¼��ť�¼�
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 1.��ȡ�û���
				String uname = username.getText().trim();
				Constents.uname = uname;// ʹ��һ������,��������,��ʾ������ҳ��
				// 2.��ȡ����
				// 3.��������
				String pwd = MD5.getInstance().getMD5(password.getText().trim());
				
				// 4.�������ݿ�

				String sql = "select ID,NAME,AGE,DEPTNAME,COM_DEPT.DEPTNO,USERNAME,IMAGE,ISLOGIN from COM_PERSON,COM_DEPT WHERE COM_DEPT.DEPTNO=COM_PERSON.DEPTNO and USERNAME=? and PASSWORD=?";
				System.out.println("���Ϊ:" + sql);

				// �·���
				DBHelper db = new DBHelper();
				
				//*************************************
				user = new User(uname, pwd);
				List<Map<String, Object>> list= db.select(sql,uname,pwd);
				
				if (list!=null&&list.size()>0) {
//					String tmpStr=list.get(0).get("ISLOGIN").toString();
					cm = new ClienManage();
					if(cm.IsConnect()){
						if(!cm.Check_isLogin(user)){
							if(cm.Login()){
								//
								chatWindow();
								db.updateStatus(uname,1);
								MessageDialog.openInformation(shell, "��ӭ!", "��ӭ��!  " + uname);
								Constents.password = pwd;
								Constents.user.setName(uname);
								Constents.user.setPassWords(pwd);
								Constents.user.setType(MessageType.UserLogin);
								Constents.user.setImage(list.get(0).get("IMAGE").toString());
								Constents.user.setDeptname(list.get(0).get("DEPTNAME").toString());
								Constents.user.setDeptno(Integer.parseInt(list.get(0).get("DEPTNO").toString()));
								Constents.user.setAge(Integer.parseInt(list.get(0).get("AGE").toString()));
								Constents.user.setId(Integer.parseInt(list.get(0).get("ID").toString()));
								Config.DOWNLOAD_PATH += list.get(0).get("ID").toString() + "/";
								Config.MESSAGE_PATH += list.get(0).get("ID").toString() + "/";
								createDir();
								Login.this.shell.close();// �رյ�¼����
								MainWindow window = new MainWindow();
								window.open();
								//
							}else{
								MessageDialog.openError(shell, "����","�û������������");
								return ;
							}
						} else{
							MessageDialog.openError(shell, "����",uname + "�ѵ�½��");
							return ;
						}
					} else{
						MessageDialog.openError(shell, "����", "���Ӳ���������");
					}
				} else {
					MessageDialog.openError(shell, "����", "δ�ҵ����˺ţ��������Ա��ϵ");
				}
			}
		});
		
		// �˳�
		exitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean r = MessageDialog.openConfirm(shell, "��ȷ��", "ȷ���˳�ϵͳ��?");
				if (r) {
					System.exit(0);
				}
			}
		});

	}
	
	public static void chatWindow() {
		//����������
		client = new Client_Frame(user.getName(),cm);
		//���µ�¼״̬
		String name = user.getName();
		//�����������漯��
		ManageClientCollction.addClientCollction(name, client);
		ClientConServer clientConServer = new ClientConServer(cm,client);
		//�����̶߳�������߳��򼯺�
		ClientThreadCollection.addClientThreadCollection(user.getName(), clientConServer);
		//�����ͻ��˼������������ͨ�ŵĺ�̨�߳�
		Thread t = new Thread(clientConServer);
		t.start();
	}
	
	//���������ҿɼ���
	public static void setAvisiable(boolean visible) {
		client.setVisible(visible);
	}
	
	//���������ļ�
	public static void loadConfig() {
		IniFileOpration iniFileOpration = new IniFileOpration();
		Map<String, String> map = iniFileOpration.getIniFile();

		Config.DOWNLOAD_PATH = map.get("DOWNLOAD_PATH");
		Config.MESSAGE_PATH = map.get("MESSAGE_PATH");
		Config.WINDOW_ALPHA = Integer.parseInt(map.get("WINDOW_ALPHA"));
	}
	
	//���������¼�ļ���
	public void createDir() {
		File messageFile = new File(Config.MESSAGE_PATH);
		File downloadFile = new File(Config.DOWNLOAD_PATH);
		if (!messageFile.exists()) messageFile.mkdir();
		if (!downloadFile.exists()) downloadFile.mkdir();
	}
}
