package com.mu.view;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import java.awt.Container;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.config.Config;
import com.mu.dao.DBHelper;
import com.mu.server.chat_server.view.EmployeeImpl;
import com.mu.server.chat_server.view.UpdateEmployeeDialog;
import com.mu.utils.Constents;
import com.mu.utils.IniFileOpration;
import com.mu.utils.MD5;
import com.mu.utils.SwtUtils;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

/**
 * 个人信息设置窗口
 * @author MUZUKI
 *
 */
public class Setting extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text IDText;
	private Text nameText;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text usernameText;
	private Text pathText;
	private Text ageText;
	private Text downloadPathText;
	private Text messageText;
	private Text passwordText;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Setting(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE | SWT.MIN | SWT.TITLE);
		shell.setImage(SWTResourceManager.getImage(Setting.class, "/com/mu/utils/favicon.ico"));
		shell.setSize(465, 326);
		shell.setText("\u8BBE\u7F6E");

		Label IDLabel = new Label(shell, SWT.NONE);
		IDLabel.setLocation(113, 26);
		IDLabel.setSize(25, 17);
		IDLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		IDLabel.setText("ID\uFF1A");
		
		IDText = new Text(shell, SWT.BORDER);
		IDText.setEnabled(false);
		IDText.setEditable(false);
		IDText.setLocation(144, 23);
		IDText.setSize(73, 23);
		
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setLocation(242, 26);
		nameLabel.setSize(36, 17);
		nameLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nameLabel.setText("\u540D\u5B57\uFF1A");
		
		nameText = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		nameText.setText(Constents.user.getName());
		nameText.setLocation(286, 25);
		nameText.setSize(73, 23);
		
		Label usernameLabel = new Label(shell, SWT.NONE);
		usernameLabel.setLocation(92, 57);
		usernameLabel.setSize(48, 17);
		formToolkit.adapt(usernameLabel, true, true);
		usernameLabel.setText("\u7528\u6237\u540D\uFF1A");
		
		usernameText = new Text(shell, SWT.BORDER);
		usernameText.setText(Constents.uname);
		usernameText.setLocation(145, 53);
		usernameText.setSize(73, 23);
		formToolkit.adapt(usernameText, true, true);
		
		Label ageLabel = new Label(shell, SWT.NONE);
		ageLabel.setLocation(242, 57);
		ageLabel.setSize(36, 17);
		formToolkit.adapt(ageLabel, true, true);
		ageLabel.setText("\u5E74\u9F84\uFF1A");
		
		ageText = new Text(shell, SWT.BORDER);
		ageText.setText(Constents.user.getAge()+"");
		ageText.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				// 几种情况，输入控制键，输入中文，输入字符，输入数字   
				// 正整数验证   
				Pattern pattern = Pattern.compile("[0-9]\\d*");   
				Matcher matcher = pattern.matcher(e.text);   
				if (matcher.matches()) e.doit = true; // 处理数字   
				else if (e.text.length() > 0) e.doit = false;// 有字符情况,包含中文、空格   
				else e.doit = true;  // 控制键  	
			}
		});
		
		ageText.setLocation(286, 54);
		ageText.setSize(73, 23);
		formToolkit.adapt(ageText, true, true);
		
		Label passwordLabel = new Label(shell, SWT.NONE);
		passwordLabel.setBounds(105, 87, 35, 17);
		formToolkit.adapt(passwordLabel, true, true);
		passwordLabel.setText("\u5BC6\u7801\uFF1A");
		
		passwordText = new Text(shell, SWT.BORDER);
		passwordText.setBounds(145, 83, 155, 23);
		formToolkit.adapt(passwordText, true, true);
		
		pathText = new Text(shell, SWT.BORDER);
		pathText.setText("src/com/mu/image/avatar/1.bmp");
		pathText.setLocation(145, 112);
		pathText.setSize(155, 23);
		formToolkit.adapt(pathText, true, true);
		
		Label pathLabel = new Label(shell, SWT.NONE);
		pathLabel.setLocation(81, 115);
		pathLabel.setSize(60, 17);
		formToolkit.adapt(pathLabel, true, true);
		pathLabel.setText("\u5934\u50CF\u8DEF\u5F84\uFF1A");
		
		Button pathButton = new Button(shell, SWT.NONE);
		pathButton.setLocation(309, 111);
		pathButton.setSize(50, 25);
		formToolkit.adapt(pathButton, true, true);
		pathButton.setText("\u9009\u62E9");
		
		Button submitButton = new Button(shell, SWT.NONE);
		submitButton.setLocation(166, 242);
		submitButton.setSize(112, 35);
		
		submitButton.setImage(SWTResourceManager.getImage(UpdateEmployeeDialog.class, "/com/mu/image/icon/submit.png"));
		formToolkit.adapt(submitButton, true, true);
		submitButton.setText("\u4FDD\u5B58");
		
		SwtUtils.centerShell(shell.getDisplay(), shell);
		
		IDText.setText(Constents.user.getDeptno()+"");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(81, 148, 61, 17);
		formToolkit.adapt(label, true, true);
		label.setText("\u4E0B\u8F7D\u8DEF\u5F84\uFF1A");
		
		downloadPathText = new Text(shell, SWT.BORDER);
		System.getProperty("user.home");
		downloadPathText.setBounds(145, 145, 155, 23);
		formToolkit.adapt(downloadPathText, true, true);
		
		Button downloadPathButton = new Button(shell, SWT.NONE);
		downloadPathButton.setBounds(309, 143, 50, 25);
		formToolkit.adapt(downloadPathButton, true, true);
		downloadPathButton.setText("\u9009\u62E9");
		
		Label messageLabel = new Label(shell, SWT.NONE);
		messageLabel.setBounds(81, 177, 61, 17);
		formToolkit.adapt(messageLabel, true, true);
		messageLabel.setText("\u804A\u5929\u8BB0\u5F55\uFF1A");
		
		messageText = new Text(shell, SWT.BORDER);
		messageText.setBounds(145, 174, 155, 23);
		formToolkit.adapt(messageText, true, true);
		
		Button messageButton = new Button(shell, SWT.NONE);
		messageButton.setBounds(309, 172, 50, 25);
		formToolkit.adapt(messageButton, true, true);
		messageButton.setText("\u9009\u62E9");
		
		Scale alphaScale = new Scale(shell, SWT.NONE);
		
		alphaScale.setToolTipText("\u4E2A\u4EBA\u4FE1\u606F\u7A97\u53E3\u900F\u660E\u5EA6");
		alphaScale.setMaximum(255);
		alphaScale.setMinimum(1);
		alphaScale.setSelection(Config.WINDOW_ALPHA);
		alphaScale.setBounds(145, 203, 155, 23);
		formToolkit.adapt(alphaScale, true, true);
		
		Label alphaLabel = new Label(shell, SWT.NONE);
		alphaLabel.setBounds(93, 206, 48, 17);
		formToolkit.adapt(alphaLabel, true, true);
		alphaLabel.setText("\u900F\u660E\u5EA6\uFF1A");
		
		Label alphaValueLabel = new Label(shell, SWT.NONE);
		alphaValueLabel.setEnabled(false);
		alphaValueLabel.setAlignment(SWT.CENTER);
		alphaValueLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		alphaValueLabel.setBounds(309, 203, 50, 23);
		formToolkit.adapt(alphaValueLabel, true, true);
		alphaValueLabel.setText(Config.WINDOW_ALPHA+"");
		
		alphaScale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value= alphaScale.getSelection();
				String str = ""+value;
				alphaValueLabel.setText(str);
			}
		});
		
		//显示配置 
		downloadPathText.setText(Config.DOWNLOAD_PATH);
		messageText.setText(Config.MESSAGE_PATH);
		alphaValueLabel.setText(Config.WINDOW_ALPHA+"");
		
		IniFileOpration ifo = new IniFileOpration();
		
		//提交信息
		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = nameText.getText();
				String age = ageText.getText();
				String username = usernameText.getText();
				String password = passwordText.getText();
				String path = pathText.getText();
				String DOWNLOAD_PATH = downloadPathText.getText();
				String MESSAGE_PATH = messageText.getText();
				String WINDOW_ALPHA = alphaScale.getSelection() + "";
				String messageString = "你的员工数据更改成功！";
				if(name!=""&&age!=""&&DOWNLOAD_PATH!=""&&MESSAGE_PATH!="") {
					//修改配置文件
					Map<String, String> configMap = new HashMap<>();
					configMap.put("DOWNLOAD_PATH", DOWNLOAD_PATH.replace(Constents.user.getId() + "/", ""));
					configMap.put("MESSAGE_PATH", MESSAGE_PATH.replace(Constents.user.getId() + "/", ""));
					configMap.put("WINDOW_ALPHA", WINDOW_ALPHA);
					ifo.setIniFile(configMap);
					
					//修改用户数据
					EmployeeImpl ei = new EmployeeImpl();
					if (username=="") username="zhangsan";
					int isUpdate = ei.updateEmployee(Constents.user.getId(),name,Integer.parseInt(age),username,path,Constents.user.getDeptno());
					
					//判断是否修改密码
					if (password!=null&&!"".equals(password)) {
						messageString += "你的新密码： " + password.toString();
						password = MD5.getInstance().getMD5(password);
						ei.updateEmployee(Constents.user.getId(), password);
					}
					
					if(isUpdate!=0) {
						MessageDialog.openInformation(shell, "成功", messageString);
						shell.close();
					}else {
						MessageDialog.openInformation(shell, "错误", "修改信息出错，请检查数据！");
					}
				} else {
					MessageDialog.openError(shell, "信息缺少", "名字、年龄、下载路径、聊天记录路径不能为空！");
				}
			}
		});
		
		//选择头像路径
		pathButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String userHome=System.getProperty("user.home");
				FileDialog dialog=new FileDialog(shell, SWT.OPEN);
				dialog.setFilterPath(userHome);  //设置初始路径
				dialog.setFilterExtensions(new String[] {"*.jpg","*.jepg","*.png","*.bmp","*.*"});
				String fileName=dialog.open();  //返回的全路径(路径+文件名)
				if (fileName==null||"".equals(fileName)) {
					return;
				}
				pathText.setText(fileName);
			}
		});
		
		//选择下载路径
		downloadPathButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String userHome=System.getProperty("user.home");
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
				dialog.setFilterPath(userHome);  //设置初始路径
				String dirName=dialog.open();  //返回的全路径(路径+文件夹名)
				if (dirName==null||"".equals(dirName)) {
					return;
				}
				downloadPathText.setText(dirName);
			}
		});
		
		//选择聊天记录路径
		messageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String userHome=System.getProperty("user.home");
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
				dialog.setFilterPath(userHome);  //设置初始路径
				String dirName=dialog.open();  //返回的全路径(路径+文件夹名)
				if (dirName==null||"".equals(dirName)) {
					return;
				}
				messageText.setText(dirName);
			}
		});
	}
}
