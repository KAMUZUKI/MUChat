package com.mu.server.chat_server.backstage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.mu.common.Message;
import com.mu.server.chat_server.tools.ServerThreadCollection;
/**
 * �����������ļ����߳���
 * @author Administrator
 *
 */
public class SendFileThread implements Runnable{
	private Socket s;
	private ServerSocket ss;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private Message mess;
	private int Type;	//0Ϊ���͸������ˣ�1Ϊ���͸�����
	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	public SendFileThread(Message mess,int Type){
		this.mess = mess;
		this.Type = Type;
		try {
			ss = new ServerSocket(8888);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			s = ss.accept();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str;
		Long Name = null;;
		try {
			//��װ������
			try {
				bis = new BufferedInputStream((s.getInputStream()));
				Name = System.currentTimeMillis();
				bos = new BufferedOutputStream(new FileOutputStream(Name+""+mess.getContent()));
			} catch (IOException e){
				e.printStackTrace();
			}
			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
			if(Type==0){
				//���͸�������
				SendFileToAll(Name+""+mess.getContent());
			}else if(Type==1){
				//���͸�����
				SendFileToPerson(Name+""+mess.getContent());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bis!=null){
					bis.close();
				}
				if(ss!=null){
					ss.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * ���͸�����
	 */
	public void SendFileToPerson(String FileName){
		Socket s1 = null;;
		try {
			//���ݻ����ȡ�÷���������ͻ���ͨ�ŵ��߳�
			ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(mess.getGetter());
	        bis = new BufferedInputStream(new FileInputStream(FileName));
			os = new ObjectOutputStream(sccc.getS().getOutputStream());
			os.writeObject(mess);
			InetAddress ip = sccc.getS().getInetAddress();
			s1 = new Socket(ip, 7777);
			bos = new BufferedOutputStream(s1.getOutputStream());
			String str;

			byte[] bys = new byte[1024];
			int len = 0;
			while ((len = bis.read(bys)) != -1) {
				bos.write(bys, 0, len);
				bos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(s1!=null){
					s1.shutdownOutput();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if(s1!=null){
					s1.close();
				}
				if(bis!=null){
					bis.close();
				}
				if(bos!=null){
					bos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �����ļ���������
	 */
	public void SendFileToAll(String FileName){
		//��������û�
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		String Name = null;
		for(int i=0;i<strings.length;i++){
			Name = strings[i];
			if(!mess.getSender().equals(Name)){
				//���ý����û�
				mess.setGetter(Name);
				Socket s1 = null;;
				try {
					 bis = new BufferedInputStream(new FileInputStream(FileName));
					//�����������������ͻ���ͨ�ŵ��߳�
					ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
					os = new ObjectOutputStream(sccc.getS().getOutputStream());
					os.writeObject(mess);
					InetAddress ip = sccc.getS().getInetAddress();
					s1 = new Socket(ip, 7777);
					bos = new BufferedOutputStream(s1.getOutputStream());
					String str;
					byte[] bys = new byte[1024];
					int len = 0;
					while ((len = bis.read(bys)) != -1) {
						bos.write(bys, 0, len);
						bos.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						s1.shutdownOutput();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if(s1!=null){
							s1.close();
						}
						if(bis!=null){
							bis.close();
						}
						if(bos!=null){
							bos.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//���߱���ͬ��ִ������쳣
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
