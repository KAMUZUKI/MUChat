package com.mu.server.chat_server.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.utils.SwtUtils;

/**
 * 员工管理窗口
 * @author MUZUKI
 *
 */
public class EmpManagement {

	protected Shell shlEmployeemanage;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EmpManagement window = new EmpManagement();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlEmployeemanage.open();
		shlEmployeemanage.layout();
		while (!shlEmployeemanage.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlEmployeemanage = new Shell();
		shlEmployeemanage.setImage(SWTResourceManager.getImage(EmpManagement.class, "/com/mu/utils/favicon.ico"));
		shlEmployeemanage.setSize(953, 632);
		shlEmployeemanage.setText("EmployeeManage");
		SwtUtils.centerShell(Display.getDefault(), shlEmployeemanage);
		Composite employeeWall =  new EmployeeWall(shlEmployeemanage, SWT.NONE);
		employeeWall.setBounds(0, 0, 937, 593);
		employeeWall.layout();
	}

}
