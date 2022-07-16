package com.mu.server.chat_server.view;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.dao.DBHelper;
import com.mu.utils.SwtUtils;
import com.mu.view.Message;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;

/**
 * 添加员工的窗口
 * @author MUZUKI
 *
 */
public class AddEmployee extends Shell {
	private Text IDText;
	private Text nameText;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text usernameText;
	private Text pathText;
	private Text ageText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			AddEmployee shell = new AddEmployee(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public AddEmployee(Display display) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(AddEmployee.class, "/com/mu/utils/favicon.ico"));
		setLayout(null);
		createContents();
		
		Label IDLabel = new Label(this, SWT.NONE);
		IDLabel.setLocation(89, 65);
		IDLabel.setSize(25, 17);
		IDLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		IDLabel.setText("ID\uFF1A");
		
		IDText = new Text(this, SWT.BORDER);
		IDText.setEnabled(false);
		IDText.setEditable(false);
		IDText.setLocation(120, 61);
		IDText.setSize(73, 23);
		
		Label nameLabel = new Label(this, SWT.NONE);
		nameLabel.setLocation(218, 64);
		nameLabel.setSize(36, 17);
		nameLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nameLabel.setText("\u540D\u5B57\uFF1A");
		
		nameText = new Text(this, SWT.BORDER);
		nameText.setLocation(262, 63);
		nameText.setSize(73, 23);
		
		Label usernameLabel = new Label(this, SWT.NONE);
		usernameLabel.setLocation(68, 95);
		usernameLabel.setSize(48, 17);
		formToolkit.adapt(usernameLabel, true, true);
		usernameLabel.setText("\u7528\u6237\u540D\uFF1A");
		
		usernameText = new Text(this, SWT.BORDER);
		usernameText.setLocation(121, 91);
		usernameText.setSize(73, 23);
		formToolkit.adapt(usernameText, true, true);
		
		Label ageLabel = new Label(this, SWT.NONE);
		ageLabel.setLocation(217, 98);
		ageLabel.setSize(36, 17);
		formToolkit.adapt(ageLabel, true, true);
		ageLabel.setText("\u5E74\u9F84\uFF1A");
		
		ageText = new Text(this, SWT.BORDER);
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
		
		ageText.setLocation(263, 94);
		ageText.setSize(73, 23);
		formToolkit.adapt(ageText, true, true);
		
		Label deptLabel = new Label(this, SWT.NONE);
		deptLabel.setLocation(81, 129);
		deptLabel.setSize(36, 17);
		formToolkit.adapt(deptLabel, true, true);
		deptLabel.setText("\u90E8\u95E8\uFF1A");
		
		CCombo deptCombo = new CCombo(this, SWT.BORDER);
		deptCombo.setLocation(119, 128);
		deptCombo.setSize(115, 21);
		formToolkit.adapt(deptCombo);
		formToolkit.paintBordersFor(deptCombo);
		
		pathText = new Text(this, SWT.BORDER);
		pathText.setText("src/com/mu/image/avatar/1.bmp");
		pathText.setLocation(120, 160);
		pathText.setSize(155, 23);
		formToolkit.adapt(pathText, true, true);
		
		Label pathLabel = new Label(this, SWT.NONE);
		pathLabel.setLocation(56, 163);
		pathLabel.setSize(60, 17);
		formToolkit.adapt(pathLabel, true, true);
		pathLabel.setText("\u5934\u50CF\u8DEF\u5F84\uFF1A");
		
		Button pathButton = new Button(this, SWT.NONE);
		pathButton.setLocation(284, 157);
		pathButton.setSize(50, 27);
		formToolkit.adapt(pathButton, true, true);
		pathButton.setText("\u9009\u62E9");
		
		Button submitButton = new Button(this, SWT.NONE);
		submitButton.setLocation(136, 198);
		submitButton.setSize(112, 35);
		
		submitButton.setImage(SWTResourceManager.getImage(AddEmployee.class, "/com/mu/image/icon/submit.png"));
		formToolkit.adapt(submitButton, true, true);
		submitButton.setText("\u63D0\u4EA4");
		
		SwtUtils.centerShell(display, getShell());
		//获取部门列表
		DBHelper db = new DBHelper();
		String sql = "SELECT * FROM COM_DEPT"; 
		List<Map<String,Object>> list = db.select(sql);
		String[] tmpStr = null; 
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i).get("DEPTNO").toString() + list.get(i).get("DEPTNAME").toString() + " ";
		}
		tmpStr=str.split(" ");
				
		deptCombo.setItems(tmpStr);
		
		//获取最大的ID
		double s=db.selectAggreation("select max(ID) from COM_PERSON");
		Long l=new Long(new Double(s).longValue()+1);
		IDText.setText(l.toString());
		//提交信息
		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int id = l.intValue();
				String name = nameText.getText();
				String age = ageText.getText();
				String username = usernameText.getText();
				String deptname = deptCombo.getText();
				String path = pathText.getText();
				Pattern pattern = Pattern.compile("[^0-9]+");
				if(name!=""&&deptname!=""&&age!="") {
			        String[] cs = pattern.split(deptname);
					int deptno = Integer.parseInt(cs[0]);
					EmployeeImpl ei = new EmployeeImpl();
					
					if (username=="") username="zhangsan";
					
					
					//int id,String name,int age,String username,String pic
					Long l = ei.regStudent(id,name,Integer.parseInt(age),username,deptno,path);
					if(l!=0) {
						MessageDialog.openInformation(getShell(), "成功", "员工数据添加成功！" + "员工ID为：" + l);
						getShell().close();
					}else {
						MessageDialog.openInformation(getShell(), "错误", "添加员工数据出错，请检查数据！");
					}
				} else {
					MessageDialog.openError(getShell(), "信息缺少", "名字、年龄、部门必填！");
				}
			}
		});
		
		//选择路径
		pathButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String userHome=System.getProperty("user.home");
				FileDialog dialog=new FileDialog(getShell(), SWT.OPEN);
				dialog.setFilterPath(userHome);  //设置初始路径
				dialog.setFilterExtensions(new String[] {"*.jpg","*.jepg","*.png","*.bmp","*.*"});
				String fileName=dialog.open();  //返回的全路径(路径+文件名)
				if (fileName==null||"".equals(fileName)) {
					return;
				}
				pathText.setText(fileName);
			}
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u6DFB\u52A0\u5458\u5DE5");
		setSize(419, 306);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
