package checkers_game;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

class Game
{
    int cut_off=5;
    int x;
    int getUtility(int [][]board)
    {
        int p1=0, p2=0, k1=0, k2=0, pos1=0, pos2=0, m1=0, m2=0;
        int n=board.length;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                int cell=board[i][j];
                if(cell==1) p1++;
                if(cell==2) k1++;
                if(cell==-1) p2++;
                if(cell==-2) k2++;
                if(cell>0)pos1+=(7-i);
                if(cell<0)pos2+=i;
                if((i==3||i==4)&&(j==3||j==4||j==5||j==2))
                {
                    if(cell>0)
                        m1++;
                    if(cell<0)
                        m2++;
                }
            }
        }
        
        int c1=0;
        int c2=0;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]>0)
                {
                    if(isSafe(board, i+1, j-1)&&isSafe(board, i-1, j+1)&&board[i+1][j-1]<0&&board[i-1][j+1]==0)
                        c1+=Math.abs(board[i][j]);
                    
                    if(isSafe(board, i+1, j+1)&&isSafe(board, i-1, j-1)&&board[i+1][j+1]<0&&board[i-1][j-1]==0)
                        c1 += Math.abs(board[i][j]);                    
                    if(isSafe(board, i-1, j+1)&&isSafe(board, i+1, j-1)&&board[i-1][j+1]==-2&&board[i+1][j-1]==0)
                        c1 += Math.abs(board[i][j]);                        
                    if(isSafe(board, i-1, j-1)&&isSafe(board, i+1, j+1)&&board[i-1][j-1]==-2&&board[i+1][j+1]==0)
                        c1 += Math.abs(board[i][j]);
                }
                
                else
                {
                    if(isSafe(board, i-1, j+1)&&isSafe(board, i+1, j-1)&&board[i-1][j+1]>0&&board[i+1][j-1]==0)
                        c2 += Math.abs(board[i][j]);
                        
                    if(isSafe(board, i-1, j-1)&&isSafe(board, i+1, j+1)&&board[i-1][j-1]>0&&board[i+1][j+1]==0)
                        c2 += Math.abs(board[i][j]);
                    
                    if(isSafe(board, i+1, j-1)&&isSafe(board, i-1, j+1)&&board[i+1][j-1]==2&&board[i-1][j+1]==0)
                        c2 += Math.abs(board[i][j]);
                    
                    if(isSafe(board, i+1, j+1)&&isSafe(board, i-1, j-1)&&board[i+1][j+1]==2&&board[i-1][j-1]==0)
                        c2 += Math.abs(board[i][j]);
                }
            }
        }
        return ((2*(5*p1+10*k1)+4*pos1+2*m1-15*c1)-(2*(5*p2+10*k2)+4*pos2+2*m2-15*c2))*100;
    }
    
    int check_winner(int [][]board)
    {
        int c1=0, c2=0;
        for(int[]row:board)
        {
            for(int cell:row)
            {
                if(cell>0)
                    c1++;
                if(cell<0)
                    c2++;
            }
        }
        if(c1>c2)return 1;
        else return -1;
    }
                
    boolean game_over(int [][]board)
    {
        boolean flag1=false, flag2=false;
        for(int[]row:board)
        {
            for(int cell:row)
            {
                if(cell>0)
                    flag1=true;
                if(cell<0)
                    flag2=true;
                if(flag1&&flag2)return false;
            }
        }
        return true;
    }
    boolean no_moves(int [][]board, int turn)
    {
        int n=board.length;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(turn==0)
                {
                    if(board[i][j]==-1)
                    {
                        if(isSafe(board, i-1, j-1)&&(board[i-1][j-1]==0||(board[i-1][j-1]>0&&isSafe(board, i-2, j-2)&&board[i-2][j-2]==0)))return false;
                        if(isSafe(board, i-1, j+1)&&(board[i-1][j+1]==0||(board[i-1][j+1]>0&&isSafe(board, i-2, j+2)&&board[i-2][j+2]==0)))return false;
                    }
                }
                
                else
                {
                   if(board[i][j]==1)
                    {
                       if(isSafe(board, i+1, j-1)&&(board[i+1][j-1]==0||(board[i+1][j-1]<0&&isSafe(board, i+2, j-2)&&board[i+2][j-2]==0)))return false;
                       if(isSafe(board, i+1, j+1)&&(board[i+1][j+1]==0||(board[i+1][j+1]<0&&isSafe(board, i+2, j+2)&&board[i+2][j+2]==0)))return false;
                    }
                }
                
                if(Math.abs(board[i][j])==2)
                {
                     if(isSafe(board, i-1, j-1)&&(board[i-1][j-1]==0||(board[i-1][j-1]*board[i][j]<0&&isSafe(board, i-2, j-2)&&board[i-2][j-2]==0)))return false;
                     if(isSafe(board, i-1, j+1)&&(board[i-1][j+1]==0||(board[i-1][j+1]*board[i][j]<0&&isSafe(board, i-2, j+2)&&board[i-2][j+2]==0)))return false;
                     if(isSafe(board, i+1, j-1)&&(board[i+1][j-1]==0||(board[i+1][j-1]*board[i][j]<0&&isSafe(board, i+2, j-2)&&board[i+2][j-2]==0)))return false;
                     if(isSafe(board, i+1, j+1)&&(board[i+1][j+1]==0||(board[i+1][j+1]*board[i][j]<0&&isSafe(board, i+2, j+2)&&board[i+2][j+2]==0)))return false;
                    
                }
            }
        }
        return true;
    }
    boolean isSafe(int [][]board, int x, int y)
    {
        int n=board.length;
        return (x>=0&&x<n&&y>=0&&y<n);
    }
    
    int[][] copy(int [][]board)
    {
        int n=board.length;
        int [][]c=new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
                c[i][j]=board[i][j];
        }
        return c;
    }
    
    void make_king(int [][]board)
    {
        int n=board.length;
        for(int i=0;i<n;i++)
        {
            if(board[0][i]==-1)
                board[0][i]=-2;
        }
        
        for(int i=0;i<n;i++)
        {
            if(board[n-1][i]==1)
                board[n-1][i]=2;
        }
    }
    
    int min_val(int [][]board, int l)
    {        
        if(l>=cut_off)
            return getUtility(board);
        
        int n=board.length;
        int min_h=Integer.MAX_VALUE;
        int min_k=Integer.MAX_VALUE;
        boolean kill=false;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]<0)
                {
                    int[][]temp=copy(board);
                    int r=i, c=j;
                    if(isSafe(temp, r-1, c-1)&&temp[r-1][c-1]==0)
                    {
                        temp[r-1][c-1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v=max_val(temp, l+1);
                        
                        if(v<min_h)
                            min_h=v;
                    }
                    
                    temp=copy(board);
                    if(isSafe(temp, r-1, c-1)&&isSafe(temp, r-2, c-2)&&temp[r-1][c-1]>0&&temp[r-2][c-2]==0)
                    {
                        temp[r-2][c-2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r-1][c-1]=0;
                        make_king(temp);
                        int v=max_val(temp, l+1);
                        if(v<min_k)
                            {
                                min_k=v;
                                kill=true;
                            }
                    }
                    
                    temp=copy(board);
                    if(isSafe(temp,r-1, c+1)&&temp[r-1][c+1]==0)
                    {
                        temp[r-1][c+1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v=max_val(temp, l+1);
                        if(v<min_h)
                            min_h=v;
                    }
                   
                    temp=copy(board);
                    if(isSafe(temp,r-1, c+1)&&isSafe(temp,r-2, c+2)&&temp[r-1][c+1]>0&&temp[r-2][c+2]==0)
                    {
                        temp[r-2][c+2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r-1][c+1]=0;
                        make_king(temp);
                        int v=max_val(temp, l+1);
                        if(v<min_k)
                            {
                                min_k=v;
                                kill=true;
                            }
                    }
                    
                    if(temp[r][c]==-2)
                    {
                        temp=copy(board);
                        if(isSafe(temp,r+1, c+1)&&temp[r+1][c+1]==0)
                        {
                            temp[r+1][c+1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v=max_val(temp, l+1);
                            if(v<min_h)
                                min_h=v;
                        }

                        temp=copy(board);
                        if(isSafe(temp,r+1, c+1)&&isSafe(temp,r+2, c+2)&&temp[r+1][c+1]>0&&temp[r+2][c+2]==0)
                        {
                            temp[r+2][c+2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r+1][c+1]=0;
                            make_king(temp);
                            int v=max_val(temp, l+1);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill=true;
                            }
                        }
                        temp=copy(board);
                        if(isSafe(temp,r+1, c-1)&&temp[r+1][c-1]==0)
                        {
                            temp[r+1][c-1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v=max_val(temp, l+1);
                            if(v<min_h)
                                min_h=v;
                        }

                        temp=copy(board);
                        if(isSafe(temp,r+1, c-1)&&isSafe(temp,r+2, c-2)&&temp[r+1][c-1]>0&&temp[r+2][c-2]==0)
                        {
                            temp[r+2][c-2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r+1][c-1]=0;
                            make_king(temp);
                            int v=max_val(temp, l+1);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill=true;
                            }
                            
                        }
                    }
                    
                }
            }
        }  
        if(min_k==Integer.MAX_VALUE&&min_h==Integer.MAX_VALUE)
            return 0;
        return kill?min_k:min_h;
    }
    
    int max_val(int [][]board, int l)
    {
        if(l>=cut_off)
            return getUtility(board);
        
        int n=board.length;
        int max_h=Integer.MIN_VALUE;
        int max_k=Integer.MIN_VALUE;
        boolean kill=false;
        
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]>0)
                {
                    
                    int[][]temp=copy(board);
                    int r=i, c=j;
                    if(isSafe(temp,r+1, c-1)&&temp[r+1][c-1]==0)
                    {
                        temp[r+1][c-1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v=min_val(temp, l+1);
                        if(v>max_h)
                        max_h=v;
                    }
                    
                    temp=copy(board);
                    if(isSafe(temp,r+1, c-1)&&isSafe(temp,r+2, c-2)&&temp[r+1][c-1]<0&&temp[r+2][c-2]==0)
                    {
                        temp[r+2][c-2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r+1][c-1]=0;
                        make_king(temp);
                        int v=min_val(temp, l+1);
                        if(v>max_k)
                            {
                                max_k=v;
                                kill=true;
                            }
                    }
                    
                    temp=copy(board);
                    if(isSafe(temp, r+1, c+1)&&temp[r+1][c+1]==0)
                    {
                        temp[r+1][c+1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v=min_val(temp, l+1);
                        if(v>max_h)
                        max_h=v;
                    }
                    
                    temp=copy(board);
                    if(isSafe(temp, r+1, c+1)&&isSafe(temp, r+2, c+2)&&temp[r+1][c+1]<0&&temp[r+2][c+2]==0)
                    {
                        temp[r+2][c+2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r+1][c+1]=0;
                        make_king(temp);
                        int v=min_val(temp, l+1);
                        if(v>max_k)
                            {
                                max_k=v;
                                kill=true;
                            }
                    }
                    
                    
                    
                    //for queens
                    if(temp[r][c]==2)
                    {
                        temp=copy(board);
                        if(isSafe(temp, r-1, c-1)&&temp[r-1][c-1]==0)
                        {
                            temp[r-1][c-1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v=min_val(temp, l+1);
                            if(v>max_h)
                            max_h=v;
                        }

                        temp=copy(board);
                        if(isSafe(temp, r-1, c-1)&&isSafe(temp, r-2, c-2)&&temp[r-1][c-1]<0&&temp[r-2][c-2]==0)
                        {
                            temp[r-2][c-2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r-1][c-1]=0;
                            make_king(temp);
                            int v=min_val(temp, l+1);
                            if(v>max_k)
                            {
                                max_k=v;
                                kill=true;
                            }
                        }

                        temp=copy(board);
                        if(isSafe(temp, r-1, c+1)&&temp[r-1][c+1]==0)
                        {
                            temp[r-1][c+1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v=min_val(temp, l+1);
                            if(v>max_h)
                            max_h=v;
                        }

                        temp=copy(board);
                        if(isSafe(temp, r-1, c+1)&&isSafe(temp, r-2, c+2)&&temp[r-1][c+1]<0&&temp[r-2][c+2]==0)
                        {
                            temp[r-2][c+2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r-1][c+1]=0;
                            make_king(temp);
                            int v=min_val(temp, l+1);
                            if(v>max_k)
                            {
                                max_k=v;
                                kill=true;
                            }
                            
                        } 
                    }
                    
                }
            }
        }
        if(max_k==Integer.MIN_VALUE&&max_h==Integer.MIN_VALUE)
            return 0;
        return kill?max_k:max_h;
    }
    
  
    State choose_best_max_action(int [][]board, boolean tokill, int r1, int c1) throws InterruptedException
    {
        int n=board.length;
        int [][] final_board=copy(board);
        int [][] kill_board=copy(board);
        boolean kill=false;
        int max_h=Integer.MIN_VALUE;
        int max_k=Integer.MIN_VALUE;
        
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]>0)
                {
                    
                    int[][]temp=copy(board);
                    int r=i, c=j;
                    if(tokill==false&&isSafe(temp, r+1, c-1)&&temp[r+1][c-1]==0)
                    {
                        temp[r+1][c-1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v;
                        v=min_val(temp,0);
                            if(v>max_h)
                            {
                                max_h=v;
                                final_board=temp;
                            }
                    }
                    
                    temp=copy(board);
                    if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r+1, c-1)&&isSafe(temp, r+2, c-2)&&temp[r+1][c-1]<0&&temp[r+2][c-2]==0)
                    {
                        temp[r+2][c-2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r+1][c-1]=0;
                        make_king(temp);
                        r1=r+2;
                        c1=c-2;
                        State s=choose_best_max_action(temp, true, r1, c1);
                        int v;
                        v=min_val(s.board,0);
                        if(v>max_k)
                        {
                            max_k=v;
                            kill_board=s.board;
                            kill=true;
                        }
                    }
                    
                    temp=copy(board);
                    if(tokill==false&&isSafe(temp, r+1, c+1)&&temp[r+1][c+1]==0)
                    {
                        temp[r+1][c+1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v;
                        v=min_val(temp,0);
                            if(v>max_h)
                            {
                                max_h=v;
                                final_board=temp;
                            }
                    }
                    
                    temp=copy(board);
                    if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r+1, c+1)&&isSafe(temp, r+2, c+2)&&temp[r+1][c+1]<0&&temp[r+2][c+2]==0)
                    {
                        temp[r+2][c+2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r+1][c+1]=0;
                        make_king(temp);
                        r1=r+2;
                        c1=c+2;
                        State s=choose_best_max_action(temp, true, r1, c1);
                        int v;
                        v=min_val(s.board,0);
                        if(v>max_k)
                        {
                            max_k=v;
                            kill_board=s.board;
                            kill=true;
                        }
                    }
                      
                    //for queens
                    if(temp[r][c]==2)
                    {
                        temp=copy(board);
                        if(tokill==false&&isSafe(temp, r-1, c-1)&&temp[r-1][c-1]==0)
                        {
                            temp[r-1][c-1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v;
                            v=min_val(temp,0);
                            if(v>max_h)
                            {
                                max_h=v;
                                final_board=temp;
                            }
                        }

                        temp=copy(board);
                        if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r-1, c-1)&&isSafe(temp, r-2, c-2)&&temp[r-1][c-1]<0&&temp[r-2][c-2]==0)
                        {
                            temp[r-2][c-2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r-1][c-1]=0;
                            make_king(temp);
                            r1=r-2;
                            c1=c-2;
                            State s=choose_best_max_action(temp, true, r1, c1);
                            int v;
                            v=min_val(s.board,0);
                            if(v>max_k)
                            {
                                max_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                        }

                        temp=copy(board);
                        if(tokill==false&&isSafe(temp, r-1, c+1)&&temp[r-1][c+1]==0)
                        {
                            temp[r-1][c+1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v;
                            v=min_val(temp,0);
                            if(v>max_h)
                            {
                                max_h=v;
                                final_board=temp;
                            }
                        }

                        temp=copy(board);
                        if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r-1, c+1)&&isSafe(temp, r-2, c+2)&&temp[r-1][c+1]<0&&temp[r-2][c+2]==0)
                        {
                            temp[r-2][c+2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r-1][c+1]=0;
                            make_king(temp);
                            r1 = r - 2;
                            c1 = c + 2;
                            State s=choose_best_max_action(temp, true, r1, c1);
                            int v;
                            v=min_val(s.board,0);
                            if(v>max_k)
                            {
                                max_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                        } 
                    }
                    
                }
            }
        }
        if(kill==true)
        {
            if(tokill)System.out.println("\nMultiple kills by Player");
            return new State(kill_board);
        }
        return new State(final_board);
    }
    
    State choose_best_min_action(int [][]board, boolean tokill, int r1, int c1) throws InterruptedException
    {
        int n=board.length;
        int [][] final_board=copy(board);
        int [][] kill_board=copy(board);
        boolean kill=false;
        int min_h=Integer.MAX_VALUE;
        int min_k=Integer.MAX_VALUE;

        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(board[i][j]<0)
                {
                    int[][]temp=copy(board);
                    int r=i, c=j;
                    if(tokill==false&&isSafe(temp, r-1, c-1)&&temp[r-1][c-1]==0)
                    {
                        temp[r-1][c-1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v;
                        v=max_val(temp,0);
                            if(v<min_h)
                            {
                                min_h=v;
                                final_board=temp;
                            }
                    }
                    
                    temp=copy(board);
                    if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r-1, c-1)&&isSafe(temp, r-2, c-2)&&temp[r-1][c-1]>0&&temp[r-2][c-2]==0)
                    {
                        temp[r-2][c-2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r-1][c-1]=0;
                        make_king(temp);
                        int v;
                        r1=r-2;
                        c1=c-2;
                        State s= choose_best_min_action(temp, true, r1, c1);
                        v=max_val(temp,0);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                    }
                    
                    temp=copy(board);
                    if(tokill==false&&isSafe(temp, r-1, c+1)&&temp[r-1][c+1]==0)
                    {
                        temp[r-1][c+1]=temp[r][c];
                        temp[r][c]=0;
                        make_king(temp);
                        int v;
                        v=max_val(temp,0);
                            if(v<min_h)
                            {
                                min_h=v;
                                final_board=temp;
                            }
                    }
                   
                    temp=copy(board);
                    if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r-1, c+1)&&isSafe(temp, r-2, c+2)&&temp[r-1][c+1]>0&&temp[r-2][c+2]==0)
                    {
                        temp[r-2][c+2]=temp[r][c];
                        temp[r][c]=0;
                        temp[r-1][c+1]=0;
                        make_king(temp);
                        r1 = r - 2;
                        c1 = c + 2;
                        int v;
                        State s= choose_best_min_action(temp, true, r1, c1);
                        v=max_val(temp,0);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                            
                    }
                    
                    if(temp[r][c]==-2)
                    {
                        temp=copy(board);
                        if(tokill==false&&isSafe(temp, r+1, c+1)&&temp[r+1][c+1]==0)
                        {
                            temp[r+1][c+1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v;
                            v=max_val(temp,0);
                            if(v<min_h)
                            {
                                min_h=v;
                                final_board=temp;
                            }
                        }

                        temp=copy(board);
                        if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r+1, c+1)&&isSafe(temp, r+2, c+2)&&temp[r+1][c+1]>0&&temp[r+2][c+2]==0)
                        {
                            
                            temp[r+2][c+2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r+1][c+1]=0;
                            make_king(temp);
                            r1 = r + 2;
                            c1 = c + 2;
                            int v;
                            State s= choose_best_min_action(temp, true, r1, c1);
                            v=max_val(temp,0);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                        }
                        temp=copy(board);
                        if(tokill==false&&isSafe(temp, r+1, c-1)&&temp[r+1][c-1]==0)
                        {
                            temp[r+1][c-1]=temp[r][c];
                            temp[r][c]=0;
                            make_king(temp);
                            int v;
                            v=max_val(temp,0);
                            if(v<min_h)
                            {
                                min_h=v;
                                final_board=temp;
                            }
                        }

                        temp=copy(board);
                        if(((tokill==true&&r==r1&&c==c1)||tokill==false)&&isSafe(temp, r+1, c-1)&&isSafe(temp, r+2, c-2)&&temp[r+1][c-1]>0&&temp[r+2][c-2]==0)
                        {
                            temp[r+2][c-2]=temp[r][c];
                            temp[r][c]=0;
                            temp[r+1][c-1]=0;
                            make_king(temp);
                            r1 = r + 2;
                            c1 = c - 2;
                            int v;
                            State s= choose_best_min_action(temp, true, r1, c1);
                            v=max_val(temp,0);
                            if(v<min_k)
                            {
                                min_k=v;
                                kill_board=s.board;
                                kill=true;
                            }
                        }
                    }
                    
                }

            }
        }
        if(kill) 
        {
            if(tokill)System.out.println("\nMultiple kills by Player");         
            return new State(kill_board);
        }
        return new State(final_board);
    }
}
        
class State
{
    public int turn;
    public int[][]board;
    int [][]prev_board;
    State(int[][]board)
    {
        this.board=board;
        this.turn=0;
    }
    void print_board()
    {
        System.out.println();
                for (int[] row : board) {
                    for (int cell : row) {
                        System.out.print((cell==0?"__":(cell==1?"BP":(cell==2?"BK":(cell==-1?"AP":"AK"))))+" ");
                    }
                    System.out.println();
                }
                System.out.println();
    }
}


public class Checkers_Game {
    static int x1,y1,x2,y2;
    static int kx=-1, ky=-1;
    static boolean human_done=false;
    static JFrame frame;
    static DrawPanel drawPanel;
    static State state;
    static Game checkers;
    
    static class Human1
    {
        boolean move(int[][]board,Game checkers) throws InterruptedException
        {
            int dgx[]={-1,-1,1,1};
            int dgy[]={1,-1,-1,1};
            boolean finished=true;
            int nt=2;
            while(human_done!=true)
                Thread.sleep(100);

            human_done=false;
            if(board[x1][y1]>=0)
            {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }        
            if(kx!=-1&&ky!=-1)
            {
                if(kx!=x1&&ky!=y1)
                {
                    showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                    return false;
                }
                
                else
                {
                    kx=-1;
                    ky=-1;
                }
            }
            if (board[x1][y1] == -1 && (x2 - x1) >= 0) {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }

            int dx = x2 - x1;
            int dy = y2 - y1;

            if (Math.abs(x1 - x2) != Math.abs(y1 - y2) || board[x2][y2] != 0 || ((Math.abs(x1 - x2) == 2) && board[x1 + (dx / 2)][y1 + (dy / 2)] == 0)) 
            {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }

            boolean flag = false;
            int n=board.length;
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < n; l++) {
                    if (board[j][l] >= 0)
                        continue;
                    else if (board[j][l] == -2)
                        nt = 4;
                    else
                        nt = 2;

                    for (int k = 0; k < nt; k++) {
                        if (checkers.isSafe(board, j + dgx[k], l + dgy[k])
                                && checkers.isSafe(board, j + 2 * dgx[k], l + 2 * dgy[k]) 
                                && board[j + dgx[k]][l + dgy[k]] > 0 
                                && board[j + 2 * dgx[k]][l + 2 * dgy[k]] == 0)
                            flag = true;
                    }
                }
            }

            if (flag == true && (Math.abs(x2-x1)!=2||!(board[x1 + (dx / 2)][y1 + (dy / 2)] > 0))) {
                showMessageDialog(null, "Capture Your Opponent's Piece!");
                return false;
            }

            board[x2][y2] = board[x1][y1];
            board[x1][y1] = 0;
            checkers.make_king(board);
            flag = false;
            if (board[x2][y2] == -2)
                nt = 4;
            else 
                nt = 2;

            if (Math.abs(x2 - x1) == 2) {
                board[x1 + (dx / 2)][y1 + (dy / 2)] = 0;
                for (int k = 0; k < nt; k++) {
                    if (checkers.isSafe(board, x2 + dgx[k], y2 + dgy[k])
                            && checkers.isSafe(board, x2 + 2 * dgx[k], y2 + 2 * dgy[k])
                            && board[x2 + dgx[k]][y2 + dgy[k]] > 0 
                            && board[x2 + 2 * dgx[k]][y2 + 2 * dgy[k]] == 0) {
                        {
                            kx=x2;
                            ky=y2;
                            flag = true;
                        }
                    }
                }

                if (flag == true) 
                {
                    human_done = false;
                    frame.repaint();
                    Thread.sleep(1000);
                    showMessageDialog(null, "You Get Another Jump!");
                    return false;
                }
            }
            return finished; 
        }
    }
    
    static class Human2
    {
        boolean move(int[][]board,Game checkers) throws InterruptedException
        {
            int dgx[]={1,1,-1,-1};
            int dgy[]={1,-1,-1,1};
            boolean finished=true;
            int nt=2;
            while(human_done!=true)
                Thread.sleep(100);

            human_done=false;
            if(board[x1][y1]<=0)
            {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }        
            if(kx!=-1&&ky!=-1)
            {
                if(kx!=x1&&ky!=y1)
                {
                    showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                    return false;
                }
                
                else
                {
                    kx=-1;
                    ky=-1;
                }
            }
            if (board[x1][y1] == 1 && (x2 - x1) <= 0) {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }

            int dx = x2 - x1;
            int dy = y2 - y1;

            if (Math.abs(x1 - x2) != Math.abs(y1 - y2) || board[x2][y2] != 0 || ((Math.abs(x1 - x2) == 2) && board[x1 + (dx / 2)][y1 + (dy / 2)] == 0)) 
            {
                showMessageDialog(null, "Invalid Move or Mouse Click Not Detected");
                return false;
            }

            boolean flag = false;
            int n=board.length;
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < n; l++) {
                    if (board[j][l] <= 0)
                        continue;
                    else if (board[j][l] == 2)
                        nt = 4;
                    else
                        nt = 2;

                    for (int k = 0; k < nt; k++) {
                        if (checkers.isSafe(board, j + dgx[k], l + dgy[k])
                                && checkers.isSafe(board, j + 2 * dgx[k], l + 2 * dgy[k]) 
                                && board[j + dgx[k]][l + dgy[k]] < 0 
                                && board[j + 2 * dgx[k]][l + 2 * dgy[k]] == 0)
                            flag = true;
                    }
                }
            }

            if (flag == true && (Math.abs(x2-x1)!=2||!(board[x1 + (dx / 2)][y1 + (dy / 2)] < 0))) {
                showMessageDialog(null, "Capture Your Opponent's Piece!");
                return false;
            }

            board[x2][y2] = board[x1][y1];
            board[x1][y1] = 0;
            checkers.make_king(board);
            flag = false;
            if (board[x2][y2] == 2)
                nt = 4;
            else 
                nt = 2;

            if (Math.abs(x2 - x1) == 2) {
                board[x1 + (dx / 2)][y1 + (dy / 2)] = 0;
                for (int k = 0; k < nt; k++) {
                    if (checkers.isSafe(board, x2 + dgx[k], y2 + dgy[k])
                            && checkers.isSafe(board, x2 + 2 * dgx[k], y2 + 2 * dgy[k])
                            && board[x2 + dgx[k]][y2 + dgy[k]] < 0 
                            && board[x2 + 2 * dgx[k]][y2 + 2 * dgy[k]] == 0) {
                        {
                            kx=x2;
                            ky=y2;
                            flag = true;
                        }
                    }
                }

                if (flag == true) 
                {
                    human_done = false;
                    frame.repaint();
                    Thread.sleep(1000);
                    showMessageDialog(null, "You Get Another Jump!");
                    return false;
                }
            }
            return finished; 
        }
    }
    
    static class DrawPanel extends JPanel
    {
        private static final long serialVersionUID = 1L;

        @Override
        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            
                if (state.turn == 1) {
                    g.setColor(Color.red);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                        
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());                   
                }
                            
            int r=0;
            int c=0;
            for ( int x = 10; x <570 ; x += 70 )
            {
                
                    for ( int y = 10; y <570; y += 70 ) 
                    {
                        if((c%2==0&&r%2==0)||(c%2==1&&r%2==1))
                        {
                            g.setColor(Color.white);
                            g.fillRect( x, y, 70, 70 );
                        }
                        
                        else
                        {
                            g.setColor(Color.black);
                            g.fillRect( x, y, 70, 70 );
                        }
                        c++;
                    }
                    r++;
            }
            if (state.turn == 0) {
                String s = "";
                String s1="";
                
                if (checkers.x == 1) {
                    s = "Player's";
                    s1="turn!";
                }
                if (checkers.x == 2) {
                    s = "Player1's";
                    s1="turn!";
                }
                g.setColor(Color.GRAY);
                g.drawString(s, 510, 530);
                g.drawString(s1, 520, 550);

            } else {
                String s="", s1="";
                if (checkers.x == 0) {
                    s = "";
                }
                if (checkers.x == 1) {
                    s = "Computer's";
                    s1="turn!";
                }
                if (checkers.x == 2) {
                    s = "Player2's";
                    s1="turn!";
                }
                g.setColor(Color.GRAY);
                g.drawString(s, 15,40 );
                g.drawString(s1, 30,60);
            }
            int [][]board=state.board;
           
            int n=board.length;
            for(int i=0;i<n;i++)
            {
                for(int j=0;j<n;j++)
                {
                    if(board[i][j]==0)
                        continue;
                    if(board[i][j]==1)
                    {
                        g.setColor(Color.red);
                        g.fillOval(15+j*70, 15+i*70,60,60);
                    }
                    
                    if(board[i][j]==-1)
                    {
                        g.setColor(Color.blue);
                        g.fillOval(15+j*70, 15+i*70,60,60);
                    }
                  
                    if(board[i][j]==2)
                    {
                        g.setColor(Color.red);
                        g.fillOval(15+j*70, 15+i*70,60,60);
                        g2.setStroke(new BasicStroke(3));
                        g.setColor(Color.yellow);
                        g.drawOval(15+j*70, 15+i*70,60,60);
                    }
                    
                    if(board[i][j]==-2)
                    {
                        g.setColor(Color.blue);
                        g.fillOval(15+j*70, 15+i*70,60,60);
                        g2.setStroke(new BasicStroke(3));
                        g.setColor(Color.yellow);
                        g.drawOval(15+j*70, 15+i*70,60,60);
                    }
                }
            }
        }
    }

    static class Mouse extends Frame implements MouseListener
    {
        @Override
        public void mousePressed(MouseEvent e) 
        { 
            if(state.turn==1&&checkers.x!=2) return;
            y1=(e.getX()*8)/595;
            x1=(e.getY()*8)/620;
            System.out.print("\nMove man at cell ("+x1+","+y1+")");
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            if(state.turn==1&&checkers.x!=2) return;
            y2=(e.getX()*8)/595;
            x2=((e.getY())*8)/620;
            System.out.println(" to cell ("+x2+","+y2+")");
            human_done=true;
        } 

        public void mouseExited(MouseEvent e) {} 
        public void mouseEntered(MouseEvent e) {} 
        public void mouseClicked(MouseEvent e) {} 
    }
    
    public static void main(String[] args) throws InterruptedException {
        int n=8;
        int[][]init_board=new int[n][n];
        for(int i=0;i<(n/2)-1;i++)
        {
            for(int j=(i+1)%2;j<n;j+=2)
                init_board[i][j]=1;

            for(int j=(n-i)%2;j<n;j+=2)
                init_board[n-1-i][j]=-1;
        }
        state=new State(init_board);
        checkers=new Game();
        
        String[] options = {"Computer vs Computer", "Human vs Computer", "Human vs Human"};
        checkers.x = JOptionPane.showOptionDialog(null, "Choose mode of Game", "Gaming Modes",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[checkers.x]);
        
        
        frame = new JFrame("Checkers");
        Mouse m=new Mouse();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawPanel = new DrawPanel();
        frame.setSize(595, 620);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addMouseListener(m);
        
        if(checkers.x==0)
        {
            checkers.cut_off=5;
            checkers.x=0;
            while (true) 
            {
                Thread.sleep(1000);
                frame.repaint();
                if (checkers.game_over(state.board) == true) {
                    int w=checkers.check_winner(state.board);
                    String winner=w>0?"Red":"Blue";
                    showMessageDialog(null, "Game Over. Computer "+winner+" has won!!");
                    break;
                }
                
                if (state.turn == 0) {
                    System.out.print("\nComputer 1's Move");
                } else {
                    System.out.print("\nComputer 2's Move");
                }
                
                if (state.turn==0) {
                    state = checkers.choose_best_min_action(state.board, false, 0, 0);
                    state.turn=1;
                } else {
                    state = checkers.choose_best_max_action(state.board, false, 0, 0);
                    state.turn=0;
                }
                 if (state.board==state.prev_board) {
                    String winner=state.turn>0?"Red":"Blue";
                    showMessageDialog(null, "Game Over. Computer "+winner+" has won!!");
                    break;
                }
               state.print_board();
            }
        }
        
        else if(checkers.x==1)
        {
            Human1 human=new Human1();
            checkers.cut_off=5;
            checkers.x=1;
            while (true) {
                Thread.sleep(1000);
                frame.repaint();
                if (checkers.game_over(state.board) == true) {
                    int w=checkers.check_winner(state.board);
                    String winner=w>0?"Computer":"Player";
                    showMessageDialog(null, "Game Over. "+winner+" has won!!");
                    break;
                }
                
                if(checkers.no_moves(state.board, state.turn))
                {
                    String winner=state.turn>0?"Player":"Computer";
                    showMessageDialog(null, "Game Over. "+winner+" has won!!");
                    break;
                }
                
                if (state.turn == 1) {
                    System.out.print("\nComputers's Move");
                } else {
                    System.out.print("\nPlayer's Move");
                }

                if (state.turn == 1) {
                    state = checkers.choose_best_max_action(state.board, false, 0, 0);
                    state.turn = 0;
                } else if (human.move(state.board, checkers)) {
                    state.turn = 1;
                } else {
                    System.out.println("((Player Moves Again))");
                    continue;
                }
                
              state.print_board();
            }
        }
        
        else
        {
            Human1 human1=new Human1();
            Human2 human2=new Human2();
            checkers.x=2;
            while (true) {
                Thread.sleep(1000);
                frame.repaint();
                state.prev_board=state.board;
                if (checkers.game_over(state.board) == true) {
                    int w=checkers.check_winner(state.board);
                    String winner=w>0?"Player 2":"Player 1";
                    showMessageDialog(null, "Game Over. "+winner+" has won!!");
                    break;
                }
                if(checkers.no_moves(state.board, state.turn))
                {
                    String winner=state.turn>0?"Player 1":"Player 2";
                    showMessageDialog(null, "Game Over. "+winner+" has won!!");
                    break;
                }
                if (state.turn == 1) {
                    System.out.print("\nPlayer2's Move");
                } else {
                    System.out.print("\nPlayer1's Move");
                }

                if (state.turn == 1) {
                    if (human2.move(state.board, checkers))
                    state.turn = 0;
                    else 
                    {
                        System.out.println("((Player2 Moves Again))");
                        continue;
                    }
                } 
                else 
                {
                    if (human1.move(state.board, checkers))
                    state.turn = 1;
                    else 
                    {
                        System.out.println("((Player1 Moves Again))");
                        continue;
                    }
                } 
               state.print_board();
            }
        }
        
    }
}