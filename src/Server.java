import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.net.*;
public class Server implements ActionListener{
    static JFrame frame = new JFrame();
    JTextField text;
    JPanel mainPanel;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    Server(){
        frame.setLayout(null);

        JPanel header = new JPanel();
        header.setBackground(Color.GRAY);
        header.setBounds(0,0,450,70);
        header.setLayout(null);
        frame.add(header);

        ImageIcon i1 = new ImageIcon("icons/3.png");
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        header.add(back);

        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon("icons/1.png");
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        header.add(profile);

        ImageIcon i7 = new ImageIcon("icons/video.png");
        Image i8 = i7.getImage().getScaledInstance(30,50, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        header.add(video);

        ImageIcon i10 = new ImageIcon("icons/phone.png");
        Image i11 = i10.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,35);
        header.add(phone);

        ImageIcon i13 = new ImageIcon("icons/3icon.png");
        Image i14 = i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420,20,10,25);
        header.add(morevert);

        JLabel name = new JLabel();
        name.setText("Aryan Singh");
        name.setBounds(110,15,150,30);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF",Font.BOLD,22));
        header.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,45,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SERIF",Font.BOLD,14));
        header.add(status);


        mainPanel = new JPanel();
        mainPanel.setBounds(5,75,440,570);
        frame.add(mainPanel);
    
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        frame.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(Color.gray);
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        frame.add(send);

        frame.setSize(450,700);
        frame.setTitle("Chatter");
        frame.setLocation(200,50);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.WHITE);



        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        try{
        String out = text.getText();

        JPanel p2 = formatLabel(out);
        
        mainPanel.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2,BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        mainPanel.add(vertical,BorderLayout.PAGE_START);

        dout.writeUTF(out);

        text.setText("");
        frame.repaint();
        frame.invalidate();
        frame.validate();
        }
        catch(Exception f){
            f.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(Color.green);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] arg){
        new Server();

        try{
            try (ServerSocket ss = new ServerSocket(1001)) {
                while(true){
                    Socket s = ss.accept();
                    DataInputStream din = new DataInputStream(s.getInputStream());
                    dout = new DataOutputStream(s.getOutputStream());

                    while(true){
                        String msg = din.readUTF();
                        JPanel panel = formatLabel(msg);

                        JPanel left = new JPanel(new BorderLayout());
                        left.add(panel,BorderLayout.LINE_START);
                        vertical.add(left);
                        frame.validate();
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
