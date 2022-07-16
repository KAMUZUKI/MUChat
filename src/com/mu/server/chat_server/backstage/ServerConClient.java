package com.mu.server.chat_server.backstage;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import com.mu.common.Message;
import com.mu.common.MessageType;
import com.mu.server.chat_server.tools.ServerThreadCollection;
import com.mu.server.chat_server.view.Server_Frame;

/**
 * ��½�ɹ��������ͻ���ͨ�ŵķ�������̨�߳���
 * @author Administrator
 *	Server_Continue_Connect_Client_Thread
 */
public class ServerConClient implements Runnable{
	private Socket s = null;
	private ObjectInputStream ois;
	private ObjectOutputStream os;
	private ObjectOutputStream oos;
	private String UserName;
	private boolean isConnect = true;
	//���ͻ��˵�Socket����
	public ServerConClient(Socket s,String UserName){
		this.s = s;
		this.UserName = UserName;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(isConnect){
				//���ܿͻ��˼�����������Ϣ
				try{
					ois = new ObjectInputStream(s.getInputStream());
				}catch(SocketException e){
					ServerThreadCollection.RemoveServerContinueConnetClient(UserName);
					//���·������������û�
					ServerUpdataOnline();
					//֪ͨ�����˸��������û�
					UpdataOnline();
					//�ر�Socket
					s.close();
//					e.printStackTrace();
					System.out.println("Connection reset");
					break;
				}
				Message mess = (Message)ois.readObject();
				//�ж���Ϣ�����ͣ�������ת������
				Server_Frame.showMessage(mess);
				//����Ƿ���ȫ���˵���Ϣ
				if(mess.getMessageType().equals(MessageType.Common_Message_ToAll)){
					//��������û�
					String string = ServerThreadCollection.GetOnline();
					String[] strings = string.split(" ");
					String Name = null;
					for(int i=0;i<strings.length;i++){
						Name = strings[i];
						if(!mess.getSender().equals(Name)){
							//���ý����û�
							mess.setGetter(Name);
							String time = (new Date().toLocaleString());
							mess.setTime(time);
							//�����������������ͻ���ͨ�ŵ��߳�
							ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(Name);
							os = new ObjectOutputStream(sccc.s.getOutputStream());
							os.writeObject(mess);
						}
					}
				}else if(mess.getMessageType().equals(MessageType.Common_Message_ToPerson)){
					//���ݻ����ȡ�÷���������ͻ���ͨ�ŵ��߳�
					ServerConClient sccc = ServerThreadCollection.getServerContinueConnetClient(mess.getGetter());
					os = new ObjectOutputStream(sccc.s.getOutputStream());
					os.writeObject(mess);
				}else if(mess.getMessageType().equals(MessageType.Send_FileToAll)){
					ServerManage.Send_SystemMessage("ϵͳ��Ϣ��"+mess.getSender()+"�������˷������ļ���Ϊ��"+mess.getContent()+"���ļ�\r\n");
					Server_Frame.ShowSystemMessage("ϵͳ��Ϣ��"+mess.getSender()+"�������˷������ļ���Ϊ��"+mess.getContent()+"���ļ�\r\n");
					//����Ƿ����ļ���������
					SendFileThread r = new SendFileThread(mess,0);
					Thread t = new Thread(r);
					t.start();
				}else if(mess.getMessageType().equals(MessageType.Send_FileToPerson)){
					//����Ƿ��͸�����
					Server_Frame.ShowSystemMessage(mess.getSender()+"��"+mess.getGetter()+"�������ļ���Ϊ��"+mess.getContent()+"���ļ�\r\n");
					SendFileThread r = new SendFileThread(mess,1);
					Thread t = new Thread(r);
					t.start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ֪ͨ�������û���Ϊ�����û�Ҫ������
	 * @param Name �û���
	 */
	public void UpdataOnline(){
		//��������û�
		String string = ServerThreadCollection.GetOnline();
		String[] strings = string.split(" ");
		for(int i=0;i<strings.length;i++){
			String Getter = strings[i];
			Message mess = new Message();
			//���������û�������
			mess.setContent(string);	
			mess.setGetter(Getter);
			mess.setMessageType(MessageType.Send_Online);
			try {
				//ȡ��ÿ������������ͻ���ͨ�ŵ��߳�
				ServerConClient scc = ServerThreadCollection.getServerContinueConnetClient(Getter);
				if(scc!=null){
					os = new ObjectOutputStream(scc.s.getOutputStream());
					os.writeObject(mess);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * ���������������û��б�ķ���
	 */
	public void ServerUpdataOnline(){
		//��������û�
		String string = ServerThreadCollection.GetOnline();
		//���������û�
		Server_Frame.SetOnLline(string);
	}

	public Socket getS() {
		return s;
	}


	public void setS(Socket s) {
		this.s = s;
	}
	
	/**
	 * �ر��߳���ķ���
	 */
	public void CloseThread(){
		this.isConnect = false;
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
