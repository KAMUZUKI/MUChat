package com.mu.server.chat_server.view;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.dao.DBHelper;
import com.mu.utils.SwtUtils;

/**
 * 修改员工信息
 * @author MUZUKI
 *
 */
public class UpdateEmployeeDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text IDText;
	private Text nameText;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text usernameText;
	private Text pathText;
	private Text ageText;
	private Map<String, Object> empInfo;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UpdateEmployeeDialog(Shell parent, int style,Map<String, Object> empInfo) {
		super(parent, style);
		this.empInfo = empInfo;
		setText("更新员工数据");
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
		shell = new Shell(getParent(), getStyle());
		shell.setSize(403, 286);
		shell.setText(getText());
		
		shell.setImage(SWTResourceManager.getImage(UpdateEmployeeDialog.class, "/com/mu/utils/favicon.ico"));
		shell.setLayout(null);
		
		Label IDLabel = new Label(shell, SWT.NONE);
		IDLabel.setLocation(81, 38);
		IDLabel.setSize(25, 17);
		IDLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		IDLabel.setText("ID\uFF1A");
		
		IDText = new Text(shell, SWT.BORDER);
		IDText.setEnabled(false);
		IDText.setEditable(false);
		IDText.setLocation(112, 35);
		IDText.setSize(73, 23);
		
		Label nameLabel = new Label(shell, SWT.NONE);
		nameLabel.setLocation(210, 38);
		nameLabel.setSize(36, 17);
		nameLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nameLabel.setText("\u540D\u5B57\uFF1A");
		
		nameText = new Text(shell, SWT.BORDER);
		nameText.setText(empInfo.get("NAME").toString());
		nameText.setLocation(254, 37);
		nameText.setSize(73, 23);
		
		Label usernameLabel = new Label(shell, SWT.NONE);
		usernameLabel.setLocation(60, 69);
		usernameLabel.setSize(48, 17);
		formToolkit.adapt(usernameLabel, true, true);
		usernameLabel.setText("\u7528\u6237\u540D\uFF1A");
		
		usernameText = new Text(shell, SWT.BORDER);
		usernameText.setText(empInfo.get("USERNAME").toString());
		usernameText.setLocation(113, 65);
		usernameText.setSize(73, 23);
		formToolkit.adapt(usernameText, true, true);
		
		Label ageLabel = new Label(shell, SWT.NONE);
		ageLabel.setLocation(210, 69);
		ageLabel.setSize(36, 17);
		formToolkit.adapt(ageLabel, true, true);
		ageLabel.setText("\u5E74\u9F84\uFF1A");
		
		ageText = new Text(shell, SWT.BORDER);
		ageText.setText(empInfo.get("AGE").toString());
		
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
		
		ageText.setLocation(254, 66);
		ageText.setSize(73, 23);
		formToolkit.adapt(ageText, true, true);
		
		Label deptLabel = new Label(shell, SWT.NONE);
		deptLabel.setLocation(73, 103);
		deptLabel.setSize(36, 17);
		formToolkit.adapt(deptLabel, true, true);
		deptLabel.setText("\u90E8\u95E8\uFF1A");
		
		CCombo deptCombo = new CCombo(shell, SWT.BORDER);
		deptCombo.setText(empInfo.get("DEPTNO").toString()+empInfo.get("DEPTNAME").toString());
		deptCombo.setLocation(111, 102);
		deptCombo.setSize(115, 21);
		formToolkit.adapt(deptCombo);
		formToolkit.paintBordersFor(deptCombo);
		
		pathText = new Text(shell, SWT.BORDER);
		pathText.setText(empInfo.get("IMAGE").toString());
		pathText.setLocation(112, 134);
		pathText.setSize(155, 23);
		formToolkit.adapt(pathText, true, true);
		
		Label pathLabel = new Label(shell, SWT.NONE);
		pathLabel.setLocation(48, 137);
		pathLabel.setSize(60, 17);
		formToolkit.adapt(pathLabel, true, true);
		pathLabel.setText("\u5934\u50CF\u8DEF\u5F84\uFF1A");
		
		Button pathButton = new Button(shell, SWT.NONE);
		pathButton.setLocation(276, 131);
		pathButton.setSize(50, 27);
		formToolkit.adapt(pathButton, true, true);
		pathButton.setText("\u9009\u62E9");
		
		Button submitButton = new Button(shell, SWT.NONE);
		submitButton.setLocation(135, 180);
		submitButton.setSize(112, 35);
		
		submitButton.setImage(SWTResourceManager.getImage(UpdateEmployeeDialog.class, "/com/mu/image/icon/submit.png"));
		formToolkit.adapt(submitButton, true, true);
		submitButton.setText("\u63D0\u4EA4");
		
		SwtUtils.centerShell(shell.getDisplay(), shell);
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
		
		IDText.setText(empInfo.get("ID").toString());
		//提交信息
		submitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
					
					int id = Integer.parseInt(empInfo.get("ID").toString());
					int l = ei.updateEmployee(id,name,Integer.parseInt(age),username,path,deptno);
					if(l!=0) {
						MessageDialog.openInformation(shell, "成功", "员工ID为：" + id + "的员工数据更改成功！");
						shell.close();
					}else {
						MessageDialog.openInformation(shell, "错误", "添加员工数据出错，请检查数据！");
					}
				} else {
					MessageDialog.openError(shell, "信息缺少", "名字、年龄、部门必填！");
				}
			}
		});
		
		//选择路径
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

		
	}
}
