package com.mu.view.TreeView;

import java.io.InputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mu.common.Person;
import com.mu.view.MainWindow;

/**
 * TreeView��ʾ�ṩ
 * @author MUZUKI
 *
 */
public class TreeLabelProvider extends LabelProvider implements ILabelProvider {
	//���ڵ�ͼƬ
	public Image fatherIcon = SWTResourceManager.getImage("src/com/mu/view/pic/employee.png");;    
	//�ӽڵ�ͼƬ
    public Image sonIcon = null;
    
    public String getText(Object element) {
        Itree node = (Itree)element;
        if(node instanceof Person) {
        	String str = "";
        	if (((Person)node).getStatus().equals("1")) {
        		System.out.println(((Person)node).getUsername()+ "----------------1------------------");
				str = node.getName()+" "+((Person)node).getUsername()+" [����]";
			}else {
				System.out.println(((Person)node).getUsername() + "-----------------0------------------");
	        	str = node.getName()+" "+((Person)node).getUsername()+" [����]";
			}
        	return str;
        }
        return node.getName();
    }
    
    public Image getImage(Object element) {
    	Itree node = (Itree)element;
//    	
    	if(node instanceof Person) {
    		String str= ((Person)node).getImage();
    		if(str!=null) {
//    			System.out.println(str);
    			String[] tmpStr = str.split("avatar/");
                String path = "src/com/mu/image/avatar/" + tmpStr[1].charAt(0) +"-1.bmp";
//                System.out.println(path);
                try {
                	sonIcon = SWTResourceManager.getImage(path);
				} catch (Exception e) {
					System.out.println("ͷ�����ʧ��");
				}
    		}
        	return sonIcon;
        }else {
            return fatherIcon;
		}
    	
    }
}
