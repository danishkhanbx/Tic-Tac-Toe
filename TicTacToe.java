package TicTacToe;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener
{
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel titlePanel  = new JPanel();
	JPanel buttonPanel  = new JPanel();
	JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];    // Arrays of buttons from 0-9 in index & from 1-9 in value
    JButton reset = new JButton("Reset");
	JButton exit = new JButton("Exit");
	JButton previous = new JButton("Ex-Winner");
    boolean player1Turn;    // if player1turn is true means its his chance to play else it will be some other player
    int flag = -1;         // to find out who won, when flag 1=X, 0=O, & -1=Nobody won
    
    TicTacToe()
    {
    	frame.setTitle("Tic-Tac-Toe");        // Frame Title
		frame.setSize(800,800);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
    	frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);  
		textfield.setText("Tic-Tac-Toe");
		textfield.setOpaque(true);
		
		titlePanel.setLayout(new BorderLayout());     // create a panel that will show turns & winners just above the buttons
		titlePanel.setBounds(0,0,800,100);
		
		buttonPanel.setLayout(new GridLayout(4,4));  // 4 rows full of buttons , 3 columns
		buttonPanel.setBackground(new Color(150,150,150));	
		for(int i=0; i<9; i++)
		{
			buttons[i] = new JButton();
			buttonPanel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
		}
		
		reset.setForeground(new Color(158,0,0));
		reset.setBackground(Color.BLACK);
		reset.setFont(new Font("",Font.BOLD,30));
		reset.addActionListener(this);
		
		exit.setForeground(new Color(158,0,0));
		exit.setBackground(Color.BLACK);
		exit.setFont(new Font("",Font.BOLD,30));
		exit.addActionListener(this);
		
		previous.setForeground(new Color(158,0,0));
		previous.setBackground(Color.BLACK);
		previous.setFont(new Font("",Font.BOLD,30));
		previous.addActionListener(this);
		
		titlePanel.add(textfield);
		frame.add(titlePanel,BorderLayout.NORTH);
		frame.add(buttonPanel);   // adding panel of buttons 
		buttonPanel.add(reset);  // in that existing buttons panel adding 3 new button
		buttonPanel.add(exit);
		buttonPanel.add(previous);
		
		firstTurn();         // to determine who will play first we use random & display the turn in the panel
    }
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		for(int i=0; i<9; i++)
		{
			if(e.getSource()==buttons[i])
			{
				if(player1Turn)  // from random true or false will come, if true player1 will always mark X then check the conditions for bingo, if no conditions met   
				{               // then we wont use random to determine which player will play, we will just not allow player1 to play so the other player have to play 
					if(buttons[i].getText()=="")
					{
						buttons[i].setForeground(new Color(255,0,0));
						buttons[i].setText("X");
						player1Turn = false;      // condition for other player have to play the next move now
						textfield.setText("O turn");
						check();
					}
				}
				else        // after this player will marked O, it will check the conditions for bingo, if no conditions met then player1 have to play because of the conditions
				{
					if(buttons[i].getText()=="")
					{
						buttons[i].setForeground(new Color(0,0,255));
						buttons[i].setText("O");
						player1Turn = true;      // condition for player1 have to play the next move now
						textfield.setText("X turn");
						check();
					}
				}
			}
		}
		
		if(e.getSource()==reset) // we will reset everything from mark to color, only previous winner will be recorded then start a new game
		{
			/* When no previous Button needed to be their
			new TicTacToe();
			frame.dispose();  */
			previous.setText("Ex-Winner");
			for(int i=0; i<9; i++) 
			{
				buttons[i].setText("");
		        buttons[i].setEnabled(true);
		        JButton defbtn = new JButton();
		   	    buttons[i].setBackground(defbtn.getBackground());
		    }
		    firstTurn();
		}
		
		if(e.getSource()==exit)
		{
			System.exit(0);
		}
		
		if(e.getSource()==previous) // we will use the flag values to know who won the previous round
		{
			if(flag==0)
			{
				previous.setText("O Won");
			}
			if(flag==1)
			{
				previous.setText("X Won");
			}
			if(flag==-1)
			{
				previous.setText("Nobody Won");
			}
		}	
	}
	
	public void firstTurn() // to determine who will play first
	{
		try               // it will show the turn after 1.5 seconds starting the game
		{
			Thread.sleep(1500);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		if(random.nextInt(2)==0)   // random.nextInt(2) will get 0 or 1 when its 1->X's turn else O's turn
		{
			player1Turn = true;
			textfield.setText("X Turn");
		}
		else
		{
			player1Turn = false;
			textfield.setText("O Turn");
		}
	}
	
	public void check() // its a winning factor combinations, when any of them met the if will call Wins function sending the winning combo buttons as a parameter
	{
		// X winning conditions
		if( (buttons[0].getText()=="X") && (buttons[1].getText()=="X") && (buttons[2].getText()=="X") )
		{
			xWins(0,1,2);
		}
		if( (buttons[3].getText()=="X") && (buttons[4].getText()=="X") && (buttons[5].getText()=="X") )
		{
			xWins(3,4,5);
		}
		if( (buttons[0].getText()=="X") && (buttons[3].getText()=="X") && (buttons[6].getText()=="X") )
		{
			xWins(0,3,6);
		}
		if( (buttons[6].getText()=="X") && (buttons[7].getText()=="X") && (buttons[8].getText()=="X") )
		{
			xWins(6,7,8);
		}
		if( (buttons[1].getText()=="X") && (buttons[4].getText()=="X") && (buttons[7].getText()=="X") )
		{
			xWins(1,4,7);
		}
		if( (buttons[2].getText()=="X") && (buttons[5].getText()=="X") && (buttons[8].getText()=="X") )
		{
			xWins(2,5,8);
		}
		if( (buttons[0].getText()=="X") && (buttons[4].getText()=="X") && (buttons[8].getText()=="X") )
		{
			xWins(0,4,8);
		}
		if( (buttons[2].getText()=="X") && (buttons[4].getText()=="X") && (buttons[6].getText()=="X") )
		{
			xWins(2,4,6);
		}
		
		// O winning conditions
		if( (buttons[0].getText()=="O") && (buttons[1].getText()=="O") && (buttons[2].getText()=="O") )
		{
			oWins(0,1,2);
		}
		if( (buttons[3].getText()=="O") && (buttons[4].getText()=="O") && (buttons[5].getText()=="O") )
		{
			oWins(3,4,5);
		}
		if( (buttons[0].getText()=="O") && (buttons[3].getText()=="O") && (buttons[6].getText()=="O") )
		{
			oWins(0,3,6);
		}
		if( (buttons[6].getText()=="O") && (buttons[7].getText()=="O") && (buttons[8].getText()=="O") )
		{
			oWins(6,7,8);
		}
		if( (buttons[1].getText()=="O") && (buttons[4].getText()=="O") && (buttons[7].getText()=="O") )
		{
			oWins(1,4,7);
		}
		if( (buttons[2].getText()=="O") && (buttons[5].getText()=="O") && (buttons[8].getText()=="O") )
		{
			oWins(2,5,8);
		}
		if( (buttons[0].getText()=="O") && (buttons[4].getText()=="O") && (buttons[8].getText()=="O") )
		{
			oWins(0,4,8);
		}
		if( (buttons[2].getText()=="O") && (buttons[4].getText()=="O") && (buttons[6].getText()=="O") )
		{
			oWins(2,4,6);
		}
	}
	
	public void xWins(int a, int b, int c)  
	{
		buttons[a].setBackground(Color.GREEN);  // it will mark the 3 winning combo buttons as green to highlight it
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i=0; i<9; i++)   // when the winner is declared the game should no longer continue, so we disable all the buttons   
			buttons[i].setEnabled(false);
		
		textfield.setText("X Wins");
		flag = 1;
	}
	
	public void oWins(int a, int b, int c)
	{
		buttons[a].setBackground(Color.GREEN);
		buttons[b].setBackground(Color.GREEN);
		buttons[c].setBackground(Color.GREEN);
		
		for(int i=0; i<9; i++)
			buttons[i].setEnabled(false);
		
		textfield.setText("O Wins");
		flag = 0;
	}

}
