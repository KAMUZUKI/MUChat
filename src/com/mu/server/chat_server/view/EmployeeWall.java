package com.mu.server.chat_server.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.widgets.Control;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.dao.DBHelper;
import com.mu.utils.Utils;

import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * 服务端展示员工的窗口
 * @author MUZUKI
 *
 */
public class EmployeeWall extends Composite {
	private Text IDText;
	private Text nameText;
	private Text ageText_1;
	private Text ageText_2;
	
	private int pageno=1;
	private int pagesize=10;
	private int totalpage=1;
	
	private Label infoLabel; //分页信息
	private Combo deptCombo;
	private Combo pageCombo;
	private Button ascButton;
	private Composite showComposite;
	private Composite composite_3; 
	private Map<String, Label> map;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	private Shell shell;
	private ScrolledComposite scrolledComposite;
	private GridData gridData;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EmployeeWall(Composite parent, int style) {
		super(parent, SWT.NONE);
		shell=getShell();
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setToolTipText("");
		
		Label IDlabel = new Label(composite, SWT.NONE);
		
		IDlabel.setBounds(16, 17, 25, 17);
		IDlabel.setText("ID\uFF1A");
		
		IDText = new Text(composite, SWT.BORDER);
		IDText.setBounds(47, 14, 73, 23);
		
		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setBounds(132, 17, 31, 17);
		nameLabel.setText("\u59D3\u540D\uFF1A");
		
		nameText = new Text(composite, SWT.BORDER);
		nameText.setBounds(169, 14, 73, 23);
		
		Label deptLabel = new Label(composite, SWT.NONE);
		deptLabel.setBounds(248, 17, 30, 17);
		formToolkit.adapt(deptLabel, true, true);
		deptLabel.setText("\u90E8\u95E8\uFF1A");
		
		deptCombo = new Combo(composite, SWT.NONE);
		deptCombo.setBounds(287, 13, 88, 25);
		formToolkit.adapt(deptCombo);
		formToolkit.paintBordersFor(deptCombo);
		//获取部门列表
		DBHelper db = new DBHelper();
		String sql = "SELECT * FROM COM_DEPT"; 
		List<Map<String,Object>> list = db.select(sql);
		String[] tmpStr = null; 
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i).get("DEPTNAME").toString() + " ";
		}
		tmpStr=str.split(" ");
		deptCombo.setItems(tmpStr);
		
		Label ageLabel_1 = new Label(composite, SWT.NONE);
		ageLabel_1.setBounds(432, 22, 54, 17);
		ageLabel_1.setText("\u5E74\u9F84\u8303\u56F4\uFF1A");
		
		Label ageLabel_2 = new Label(composite, SWT.NONE);
		ageLabel_2.setBounds(549, 21, 15, 17);
		ageLabel_2.setText("\u5230");
		
		ageText_1 = new Text(composite, SWT.BORDER);
		ageText_1.setBounds(492, 18, 51, 23);
		
		ageText_2 = new Text(composite, SWT.BORDER);
		ageText_2.setBounds(566, 18, 57, 23);
		
		Button searchButton = new Button(composite, SWT.NONE);
		
		searchButton.setBounds(709, 36, 80, 27);
		searchButton.setText("\u67E5\u8BE2");
		
		Label byIDLabel = new Label(composite, SWT.NONE);
		byIDLabel.setBounds(109, 57, 39, 17);
		byIDLabel.setText("\u6309ID");
		
		ascButton = new Button(composite, SWT.RADIO);
		ascButton.setBounds(159, 57, 44, 17);
		ascButton.setText("\u5347\u5E8F");
		
		Button descButton_2 = new Button(composite, SWT.RADIO);
		descButton_2.setSelection(true);
		descButton_2.setBounds(231, 57, 46, 17);
		descButton_2.setText("\u964D\u5E8F");
		
		Label pageLabel = new Label(composite, SWT.NONE);
		pageLabel.setBounds(443, 58, 39, 17);
		pageLabel.setText("\u5206\u9875\u6570");
		
		pageCombo = new Combo(composite, SWT.NONE);
		pageCombo.setItems(new String[] {"10", "20", "30", "50", "100"});
		pageCombo.setToolTipText("");
		pageCombo.setBounds(492, 54, 72, 25);
		formToolkit.adapt(pageCombo);
		formToolkit.paintBordersFor(pageCombo);
		pageCombo.select(0);
		
		Button button = new Button(composite, SWT.NONE);
		
		button.setBounds(827, 36, 80, 27);
		formToolkit.adapt(button, true, true);
		button.setText("\u6DFB\u52A0\u5458\u5DE5");
		
		showComposite = new Composite(sashForm, SWT.NONE);
		showComposite.setToolTipText("");
		showComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		scrolledComposite = new ScrolledComposite(showComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		formToolkit.adapt(scrolledComposite);
		formToolkit.paintBordersFor(scrolledComposite);
		
		composite_3 = new Composite(scrolledComposite, SWT.NONE);
		formToolkit.adapt(composite_3);
		formToolkit.paintBordersFor(composite_3);
	
		GridLayout gl_composite_3 = new GridLayout(5, true);
		gl_composite_3.horizontalSpacing = 15;
		gl_composite_3.marginLeft = 10;
		gl_composite_3.marginWidth = 10;
		gl_composite_3.marginHeight = 10;
		composite_3.setLayout(gl_composite_3);
		
		gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gridData.widthHint=200;
		gridData.heightHint=180;
		scrolledComposite.setContent(composite_3);
		
		Composite bottomComposite = new Composite(sashForm, SWT.NONE);
		
		infoLabel = new Label(bottomComposite, SWT.NONE);
		infoLabel.setText("\u603B\u6570\u636E\u91CFX\u6761\uFF0C\u5F53\u524D\u7B2Cx\u9875\uFF0C\u6BCF\u9875x\u6761\u6570\u636E");
		infoLabel.setBounds(10, 0, 313, 30);
		formToolkit.adapt(infoLabel, true, true);
		
		Button firstButton = new Button(bottomComposite, SWT.NONE);
		firstButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageno=1;
				EmployeeWall.this.showPage();
			}
		});
		firstButton.setText("\u7B2C\u4E00\u9875");
		firstButton.setBounds(346, 0, 90, 30);
		formToolkit.adapt(firstButton, true, true);
		
		//添加员工
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddEmployee ae = new AddEmployee(getDisplay());
				ae.open();
			}
		});
		
		Button frontButton = new Button(bottomComposite, SWT.NONE);
		//上一页
		frontButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageno=pageno-1;
				if (pageno<=0) {
					pageno=1;
				}
				EmployeeWall.this.showPage();
			}
		});
		frontButton.setText("\u4E0A\u4E00\u9875");
		frontButton.setBounds(502, 0, 90, 30);
		formToolkit.adapt(frontButton, true, true);
		
		Button nextButton = new Button(bottomComposite, SWT.NONE);
		//下一页
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageno=pageno+1;
				if (pageno>totalpage) {
					pageno=totalpage;
				}
				EmployeeWall.this.showPage();
			}
		});
		nextButton.setText("\u4E0B\u4E00\u9875");
		nextButton.setBounds(653, 0, 90, 30);
		formToolkit.adapt(nextButton, true, true);
		
		Button finalButton = new Button(bottomComposite, SWT.NONE);
		//最后一页
		finalButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageno=totalpage;
				EmployeeWall.this.showPage();
			}
		});
		finalButton.setText("\u6700\u540E\u4E00\u9875");
		finalButton.setBounds(802, 0, 90, 30);
		formToolkit.adapt(finalButton, true, true);
		sashForm.setWeights(new int[] {84, 504, 28});
		//第一页
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageno=1;
				pagesize=Integer.parseInt(pageCombo.getText().trim());
				EmployeeWall.this.showPage();
			}
		});
	}

	private void showPage() {
		String searchId=IDText.getText().trim();
		String searchName=nameText.getText().trim();
		String searchDeptname=deptCombo.getText();
		String minAge=ageText_1.getText().trim();
		String maxAge=ageText_2.getText().trim();
		boolean isAsc=ascButton.getSelection();
		
		EmployeeImpl ei=new EmployeeImpl();
		PageBean pageBean=ei.pageSearch(searchId, searchName,searchDeptname, minAge, maxAge, isAsc, pageno, pagesize);
		
		pageno=pageBean.getPageno();
		pagesize=pageBean.getPagesize();
		totalpage=pageBean.getTotalPages();
		
		List<Map<String, Object>> list=pageBean.getDataset();
		System.out.println(list.size());
		if (list.size()==0) {
			MessageDialog.openError(shell, "错误", "没有此条件的员工！");
			return;
		}
		
		//TODO:动态地给界面加上n个 label
		Control[] cs=composite_3.getChildren();
		if (cs!=null&&cs.length>0) {
			for (int i = 0; i < cs.length; i++) {
				Control control=cs[i];
				if (control instanceof Group) {
					
				}
//				Group group=(Group)control;
				control.dispose();
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> empInfo=list.get(i);
			String id = list.get(i).get("ID").toString();
			String name = list.get(i).get("NAME").toString();
			String deptname = list.get(i).get("DEPTNAME").toString();
			String username = list.get(i).get("USERNAME").toString();
		    String path	= list.get(i).get("IMAGE").toString();
			list.get(i).get("AGE");
			
			Group group = new Group(composite_3, SWT.NONE);
			GridData gd_group = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
			gd_group.minimumWidth = 160;
			gd_group.widthHint = 160;
			gd_group.minimumHeight = 80;
			gd_group.heightHint = 60;
			group.setLayoutData(gd_group);
			group.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			group.setText("ID: " + id);
			formToolkit.adapt(group);
			formToolkit.paintBordersFor(group);
			
			Label avatarLabel = new Label(group, SWT.NONE);
			
			avatarLabel.setImage(SWTResourceManager.getImage(EmployeeWall.class, Utils.pathInvert(path)));
			avatarLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
			avatarLabel.setBounds(10, 25, 40, 40);
			formToolkit.adapt(avatarLabel, true, true);
			//存入对象数据
			avatarLabel.setData("info",empInfo);
			
			Label InfoLabel = new Label(group, SWT.NONE|SWT.WRAP);
			InfoLabel.setBounds(75, 25, 75, 20);
			formToolkit.adapt(InfoLabel, true, true);
			InfoLabel.setText(deptname);
			
			Label nameInfoLabel = new Label(group, SWT.NONE);
			nameInfoLabel.setBounds(75, 46, 61, 17);
			formToolkit.adapt(nameInfoLabel, true, true);
			nameInfoLabel.setText(name);
			
			avatarLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					Object object=e.getSource();
					if(object instanceof Label) {
						Label label=(Label)object;
						Map<String, Object> stuinfo=(Map<String, Object>) label.getData("info");
						UpdateEmployeeDialog addEmployee = new UpdateEmployeeDialog(shell, SWT.SHELL_TRIM|SWT.APPLICATION_MODAL, stuinfo);
						addEmployee.open();
					}
				}
			});
		}
		
		//重新布局
		composite_3.layout();
		
		map=new HashMap<String,Label>();

		composite_3.layout();
		getShell().layout();
		infoLabel.setText("总数据量" + pageBean.getTotal()+"条,总页数：" + pageBean.getTotalPages()+",当前页" + pageBean.getPageno()+"每页"+pageBean.getPagesize()+"条数据");
	
	}
	
	
	private class Task implements Runnable{
		@Override
		public void run() {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					EmployeeImpl ei=new EmployeeImpl();
					//循环map
					for (Map.Entry<String,Label> entry : map.entrySet()) {
						String path = entry.getKey();
						Label label = entry.getValue();
						if(path==null) {
							label.setText("暂无图片");
							continue;
						}
						label.setImage(SWTResourceManager.getImage(path));
					}
					
				}
			});
		}
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
