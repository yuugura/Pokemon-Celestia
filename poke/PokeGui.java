package poke;

import java.awt.*;
import javax.swing.*;

public class PokeGui extends JComponent implements Runnable {
    final static Dimension screenSize = Toolkit.getDefaultToolKit().getScreenSize();
    final static int width = (int)(screen.getWidth());
    final static int height = (int)(screen.getHeight());
    final static int fontSize = (int)(height/30);

    public void startMainMenu(){
        JFrame menuFrame = new JFrame("Pokemon");
        menuFrame.setBounds(1000, 800, width, height);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.getContentPane().setLayout(null);

        JButton startBtn = new JButton("Start Game");
        startBtn.setBounds(550,300, width/4, height/15);
        startBtn.setFont(new Font("inhalt", Font.PLAIN, fontSize+5));

        JButton endBtn = new JButton("Quit Game");
        endBtn.setBounds(550, 400, width/4, height/15);
        endBtn.setFont(new Font("inhalt", Font.PLAIN, fontSize+5));

        menuFrame.getContentPane.add(startBtn);
        menuFrame.getContentPane.add(endBtn);

        menuFrame.setVisible(true);

        btnStart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent click){
                fightFrame.dispose();
                initPokeSelect();
            }
        })

        endBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent click){
                System.exit(0);
            }
        });
    }

    public void initPokeSelect(){
        JFrame dexFrame = new JFrame("Pokemon");
        dexFrame.setBounds(1000, 800, width, height);
        dexFrame.setLocationRelativeTo(null);
        dexFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dexFrame.getContentPane().setLayout(null);

        JButton startFight = new JButton("Start Battle");
        startFight.setBounds((int)(width/1.4), (int)(height/20), 250, 70);
        startFight.setFont(new Font("inhalt", Font.PLAIN, fontSize+2));
    }
}