package com.mu.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.mu.client.chat_room.view.ClientFrame;
import com.mu.client.chat_room.view.Client_Frame;
import com.mu.dao.DBHelper;
import com.mu.view.TreeView.Factory;
import com.mu.view.TreeView.TreeContentProvider;
import com.mu.view.TreeView.TreeLabelProvider;
import com.mu.utils.Constents;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;

/**
 * 主窗口联系人列表
 * @author MUZUKI
 *
 */
public class Linked extends Composite {
	private static Tree tree;
	private ClientFrame client;
	private static TreeViewer treeViewer;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Linked(Composite parent, int style) {
		super(parent, style);
		setToolTipText("linked");
		setBackgroundMode(SWT.INHERIT_FORCE);
        treeViewer = new TreeViewer(this, SWT.BORDER|SWT.H_SCROLL);
        tree = treeViewer.getTree();
        tree.setBounds(10, 10, 284, 486);
        //生成树形图
        treeViewer.setLabelProvider(new TreeLabelProvider());
        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setInput(Factory.createTree());
        
        Menu menu = new Menu(tree);
        tree.setMenu(menu);
        
        MenuItem refresh = new MenuItem(menu, SWT.NONE);
        
        refresh.setText("\u66F4\u65B0");
        tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
					Point point = new Point (e.x, e.y);
				    TreeItem item = tree.getItem (point);
				    if (item==null) return;
				    String name=item.getText();
				    System.out.println(name);
				    String[] tmpStr = name.split(" ");
				    //如果点击的自己，直接返回
				    if (tmpStr[1].equals(Constents.uname)) return;
				    
				    Client_Frame.openClient(tmpStr);
				    
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
        
        refresh.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		refresh();
        	}
        });
        
        DBHelper db=new DBHelper();
//        shell.open();
//        shell.setLayout(new FillLayout());
//        shell.layout();
//        while (!shell.isDisposed()) {
//            if (!display.readAndDispatch())
//                display.sleep();
//        }
	}

	public static void refresh() {
		tree.clearAll(true);
		treeViewer.setLabelProvider(new TreeLabelProvider());
        treeViewer.setContentProvider(new TreeContentProvider());
        treeViewer.setInput(Factory.createTree());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
