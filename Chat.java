import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
public class Chat3 extends Frame implements ActionListener,Runnable
{	
	static TextField text,ho,po;
	Button b,con,ref,clear;
	static TextArea t;
	String req1="";
	String res1="";
	boolean run=true;
	String host,port;
	DataOutputStream out1;
	BufferedReader ins1;
	DataOutputStream out;
	BufferedReader ins;
	Socket soc;	
	Thread th,the;
	cl ch=new cl();
	int lock=0,clock=0;
	static Chat3 c;
	Chat3() throws Exception
	{
		setLayout(null);
		ho=new TextField(8);
		add(ho);
		ho.addActionListener(this);
		ho.setBounds(100,100,150,20);
		po=new TextField(8);
		add(po);
		po.addActionListener(this);
		po.setBounds(300,100,80,20);
		con=new Button("Connect");
		con.setBounds(410,100,80,20);
		add(con);
		con.addActionListener(this);
		text=new TextField(8);
		text.setBounds(100,150,150,20);
		add(text);
		text.addActionListener(this);
		b=new Button("Send");
		b.setBounds(300,150,80,20);
		add(b);
		b.addActionListener(this);
		ref=new Button("Reset");
		ref.setBounds(410,150,80,20);
		add(ref);
		ref.addActionListener(this);
		t=new TextArea(10,30);
		t.setBounds(100,200,400,200);
		add(t);
		clear=new Button("Clear Text");
		clear.setBounds(250,410,80,20);
		add(clear);
		clear.addActionListener(this);
		th=new Thread(this,"thread");	
		System.out.println("end ");
		th.start();
		System.out.println("end ");
	}	
	public void run()
	{
		System.out.println("run ");
		while(true)
		{
			try
			{
				if(lock==0)
				{
					
						try{
							System.out.println("start ");
							ch.m();
							System.out.println("end ");
						   }
						catch(Exception e)
						{	
							t.setText(t.getText() + "Exception4:" + e);
						}	
					
				}
				if(lock==1)
				{
					out1=new DataOutputStream(soc.getOutputStream());
					ins1=new BufferedReader(new InputStreamReader(soc.getInputStream()));
					System.out.println("client");
					res1=ins1.readLine();
					if(res1!=null)
						t.setText(t.getText()  + "Server:" + res1 + '\n');
					ch.refresh();
				}
			}
			catch (Exception e) 
			{
				t.setText(t.getText() + '\n' +"Exception1 " +e +'\n');
			}
		}
	}
			
	public void actionPerformed(ActionEvent ae)
	{
		 String str= ae.getActionCommand();
		if(str.equals("Send")) 
		{
			req1=text.getText();
			try{
			ch.x();}
			catch(Exception e)
			{
				t.setText(t.getText() + '\n' +"interrupted " +e +'\n');
				text.setText("");
			}
			t.setText(t.getText() + "client:"+ req1 + '\n');
			text.setText("");
		}
		if(str.equals("Clear Text")) 
		{

			t.setText("");
		}	


		if(str.equals("Connect")) 
		{
			
			try{
			System.out.println(host+port);
			System.out.println(host+port);
			System.out.println(host+port);
			host=ho.getText();
			port=po.getText();
			System.out.println(host+port);
			int por=Integer.parseInt(port);
			System.out.println(host+port);
			soc=new Socket(host,por);
			lock=1;	
			the=new Thread(c);
			the.start();
				}
			catch(Exception e)
			{
				t.setText(t.getText() + '\n' +"Exception2 " +e +'\n');
			}
			t.setText( "Connection established to "+ host + " at " + port + '\n' );
		}
		if(str.equals("Reset")) 
		{
			try{
			out1.writeBytes("Connection Closed" +'\n');
			soc.close();
			the.stop();
			lock=0;
			t.setText( t.getText() + "Connection Closed" +'\n' );}
			catch(Exception e)
			{
				t.setText(t.getText() + '\n' +"Exception3 " +e +'\n');
				ho.setText("");po.setText("");
			}
			ho.setText("");po.setText("");
		}
	
		repaint();
	}
	public static void main(String args[])throws Exception
	{
		c=new Chat3();
		c.setVisible(true);
		c.setSize(600,600);
		c.setTitle("Client3");	
	}
public class cl
{
	Socket s;
	ServerSocket ser;
	public void x() throws Exception
	{
		if(lock==1)
		{
			out1.writeBytes( req1 +'\n');
		}
		if(lock==0)
		{

			out.writeBytes( req1 +'\n');
		}
	}
	public void refresh() throws Exception
	{
		clock=1;
		repaint();
	}
	public void m() throws Exception
	{
		
		System.out.println("m");
		if(clock==0)
		{
			System.out.println("clk");
			ser=new ServerSocket(4444);
			s=ser.accept();
			t.setText("");
			System.out.println(s + " " + ser);	
		}
		if(!(res1.equals("Connection Closed")))
		{
		System.out.println("data");
		out=new DataOutputStream(s.getOutputStream());
		ins=new BufferedReader(new InputStreamReader(s.getInputStream()));
		res1=ins.readLine();
		System.out.println(res1);
		if(res1!=null)
			t.setText(t.getText() + "Server:" + res1 + '\n');
		ch.refresh();
		}
		if(res1.equals("Connection Closed"))
		{
			System.out.println(s);
			s.close();
			
			System.out.println(s);
			s=ser.accept();
			t.setText("");
			System.out.println(s);
			res1="";
		}
	}
}

	
}
