
package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import playerz.CharacterPick;
import utilz.LoadSave;

public class trapp extends Entity{
    
    private BufferedImage bd, be, ed, pb, pd, pe, pr, rb, rd, re;
    private BufferedImage BgImg1, BgImg2, BgImg3, BgImg4, BgImg5, r1, r2, r3, r4, r5, Player2wins, Player1wins, load1, load2, load3, load4, load5, load6, load7;
    private int BGwidth = (int)( 800 * Game.SCALE);
    private int BGheight = (int)(500 * Game.SCALE);
    private  boolean nxtrnd = false;
    CharacterPick p2 = new CharacterPick();
    
    public trapp(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 819 * Game.SCALE, 30 * Game.SCALE);
    }

    int map = p2.getmapinfo();
    
    public void render(Graphics g) {
                drawHitbox(g);
    }

    public void drawBG(Graphics g){
         if(map == 1){
                BgImage1(g);
            } else if(map == 2){
                BgImage2(g);
            } else if(map == 3){
                BgImage3(g);
            } else if(map == 4){
                BgImage4(g);
            } 
     }
    
    public void drawR1(Graphics g, boolean rr1){
        if(rr1){ 
            Round1(g);
        }
     }
    
    public void drawR2(Graphics g, boolean rr2){
        
        if(rr2){ 
            Round2(g);
        } 
     }
    
    public void drawR3(Graphics g, boolean rr3){
        if(rr3){    
            Round3(g);
        } 
    }
    
    public void drawR4(Graphics g, boolean rr4){
        
        if(rr4){ 
            Round4(g);
        } 
     }
    
    public void drawR5(Graphics g, boolean rr5){
        if(rr5){    
            Round5(g);
        } 
    }
    
    public void drawgif1(Graphics g, boolean gif1){
        if(gif1){
            drawGIF1(g);    
        }    
    }
    public void drawgif2(Graphics g, boolean gif2){
        if(gif2){
            drawGIF2(g);    
        }    
    }
    public void drawgif3(Graphics g, boolean gif3){
        if(gif3){
            drawGIF3(g);   
        }    
    }
    public void drawgif4(Graphics g, boolean gif4){
        if(gif4){
            drawGIF4(g);   
        }    
    }
    public void drawgif5(Graphics g, boolean gif5){
        if(gif5){
            drawGIF5(g);  
        }   
    }
    public void drawgif6(Graphics g, boolean gif6){
        if(gif6){
            drawGIF6(g); 
        }    
    }
    public void drawgif7(Graphics g, boolean gif7){
        if(gif7){
            drawGIF7(g); 
        }    
    }
    
    
    
    public void drawplayerwin(Graphics g, boolean won1, boolean won2, boolean win){
        if(win){
            if(won1){
                p1wins(g);
            }
        
            if(won2){
                p2wins(g);
            }
        }
    }
    public void drawOver1(Graphics g){
        drawover1(g);
    }
    public void drawOver2(Graphics g){
        drawover2(g);
    }
    
    public void drawLine(Graphics g){
        drawline(g);
    }
    public void drawScores1(Graphics g, int score1){
        drawScore1(g, score1);
    }
    public void drawScores2(Graphics g, int score2){
        drawScore2(g, score2);
    }
        
    public void drawdialogs(Graphics g, int player1, int player2, boolean play){
        
        if(play == true){
              if(player1 == 1 && player2 == 2 || player1 == 2 && player2 == 1){
                drawRB(g);
            }else if(player1 == 1 && player2 == 3 || player1 == 3 && player2 == 1){
                drawRE(g);
            }else if(player1 == 1 && player2 == 4 || player1 == 4 && player2 == 1){
                drawRD(g);
            }else if(player1 == 1 && player2 == 5 || player1 == 5 && player2 == 1){
                drawPR(g);
            }else if(player1 == 2 && player2 == 3 || player1 == 3 && player2 == 2){
                drawBE(g);
            }else if(player1 == 2 && player2 == 4 || player1 == 4 && player2 == 2){
                drawBD(g);
            }else if(player1 == 2 && player2 == 5 || player1 == 5 && player2 == 2){
                drawPB(g);
            }else if(player1 == 3 && player2 == 4 || player1 == 4 && player2 == 3){
                drawED(g);
            }else if(player1 == 3 && player2 == 5 || player1 == 5 && player2 == 3){
                drawPE(g);
            }else if(player1 == 4 && player2 == 5 || player1 == 5 && player2 == 4){
                drawPD(g);
            }
        }
    }
            
        
     

    private void loadAnimations() {
        BgImg2 = LoadSave.GetSpriteAtlas(LoadSave.BGIMG2);
        BgImg3 = LoadSave.GetSpriteAtlas(LoadSave.BGIMG3);
        BgImg4 = LoadSave.GetSpriteAtlas(LoadSave.BGIMG4);
        BgImg5 = LoadSave.GetSpriteAtlas(LoadSave.BGIMG5);
        
        r1 = LoadSave.GetSpriteAtlas(LoadSave.ROUND1);
        r2 = LoadSave.GetSpriteAtlas(LoadSave.ROUND2);
        r3 = LoadSave.GetSpriteAtlas(LoadSave.ROUND3);
        r4 = LoadSave.GetSpriteAtlas(LoadSave.ROUND4);
        r5 = LoadSave.GetSpriteAtlas(LoadSave.ROUND5);
        Player2wins = LoadSave.GetSpriteAtlas(LoadSave.PLAYER2WINS);
        Player1wins = LoadSave.GetSpriteAtlas(LoadSave.PLAYER1WINS);
        
        load1 = LoadSave.GetSpriteAtlas(LoadSave.LOAD1);
        load2 = LoadSave.GetSpriteAtlas(LoadSave.LOAD2);
        load3 = LoadSave.GetSpriteAtlas(LoadSave.LOAD3);
        load4 = LoadSave.GetSpriteAtlas(LoadSave.LOAD4);
        load5 = LoadSave.GetSpriteAtlas(LoadSave.LOAD5);
        load6 = LoadSave.GetSpriteAtlas(LoadSave.LOAD6);
        load7 = LoadSave.GetSpriteAtlas(LoadSave.LOAD7);
        
        bd = LoadSave.GetSpriteAtlas(LoadSave.BD);
        be = LoadSave.GetSpriteAtlas(LoadSave.BE);
        ed = LoadSave.GetSpriteAtlas(LoadSave.ED);
        pb = LoadSave.GetSpriteAtlas(LoadSave.PB);
        pd = LoadSave.GetSpriteAtlas(LoadSave.PD);
        pe = LoadSave.GetSpriteAtlas(LoadSave.PE);
        pr = LoadSave.GetSpriteAtlas(LoadSave.PR);
        rb = LoadSave.GetSpriteAtlas(LoadSave.RB);
        rd = LoadSave.GetSpriteAtlas(LoadSave.RD);
        re = LoadSave.GetSpriteAtlas(LoadSave.RE);
    }
    
    private int GIFwidth = 1664;
    private int GIFheight = 896;
    private int DialogWidth = 1664;
    private int DialogHeight = 896;
    
    private void drawover1(Graphics g){
        g.drawString("/2", 118, 50);
    }
    private void drawover2(Graphics g){
        g.drawString("/2", 1515, 50);
    }
    
    
    private void drawScore1(Graphics a, int score1){
        a.setColor(Color.white);
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 40 );
        a.setFont(stringFont);
        
        a.drawString(Integer.toString(score1), 95, 50);
    }
    private void drawScore2(Graphics b, int score2){
        b.setColor(Color.white);
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 40 );
        b.setFont(stringFont);
        b.drawString(Integer.toString(score2), 1495, 50);
    }
    private void drawline(Graphics g){
        g.setColor(Color.white);
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 40 );
        g.setFont(stringFont);
        g.drawString("|", 818, 50);
    }
    
    private void drawBD(Graphics g) {
        g.drawImage(bd, 0, 0, DialogWidth, DialogHeight, null);  
    }
    private void drawBE(Graphics g) {
        g.drawImage(be, 0, 0, DialogWidth, DialogHeight, null);  
    }
    private void drawED(Graphics g) {
        g.drawImage(ed, 0, 0, DialogWidth, DialogHeight, null);   
    }
    private void drawPB(Graphics g) {
        g.drawImage(pb, 0, 0, DialogWidth, DialogHeight, null);  
    }    
    private void drawPD(Graphics g) {
        g.drawImage(pd, 0, 0, DialogWidth, DialogHeight, null);  
    }
    private void drawPE(Graphics g) {
        g.drawImage(pe, 0, 0, DialogWidth, DialogHeight, null);   
    }
    private void drawPR(Graphics g) {
        g.drawImage(pr, 0, 0, DialogWidth, DialogHeight, null);  
    }
    private void drawRB(Graphics g) {
        g.drawImage(rb, 0, 0, DialogWidth, DialogHeight, null);  
    }
    private void drawRD(Graphics g) {
        g.drawImage(rd, 0, 0, DialogWidth, DialogHeight, null);   
    }
    private void drawRE(Graphics g) {
        g.drawImage(re, 0, 0, DialogWidth, DialogHeight, null);  
    }
    
    
    
    

    private void drawGIF1(Graphics g) {
        g.drawImage(load1, 0, 0, GIFwidth, GIFheight, null);  
    }
    private void drawGIF2(Graphics g) {
        g.drawImage(load2, 0, 0, GIFwidth, GIFheight, null);  
    }
    private void drawGIF3(Graphics g) {
        g.drawImage(load3, 0, 0, GIFwidth, GIFheight, null);   
    }
    private void drawGIF4(Graphics g) {
        g.drawImage(load4, 0, 0, GIFwidth, GIFheight, null);  
    }    
    private void drawGIF5(Graphics g) {
        g.drawImage(load5, 0, 0, GIFwidth, GIFheight, null);  
    }
    private void drawGIF6(Graphics g) {
        g.drawImage(load6, 0, 0, GIFwidth, GIFheight, null);   
    }
    private void drawGIF7(Graphics g) {
        g.drawImage(load7, 0, 0, GIFwidth, GIFheight, null);  
    }
                
        
    
    
    private void BgImage1(Graphics g) {
        g.drawImage(BgImg2, 0, 0, BGwidth, BGheight, null);   
    }
    private void BgImage2(Graphics g) {
        g.drawImage(BgImg3, 0, 0, BGwidth, BGheight, null);   
    }
    private void BgImage3(Graphics g) {
        g.drawImage(BgImg4, 0, 0, BGwidth, BGheight, null);   
    }
    private void BgImage4(Graphics g) {
        g.drawImage(BgImg5, 0, 0, BGwidth, BGheight, null);   
    }
    
    
    
    
    public void Round1(Graphics g){
        g.drawImage(r1, 0, 0, BGwidth, BGheight, null); 
    }
    
    public void Round2(Graphics g){
        g.drawImage(r2, 0, 0, BGwidth, BGheight, null); 
    }
     
    public void Round3(Graphics g){
        g.drawImage(r3, 0, 0, BGwidth, BGheight, null); 
    }
    
    public void Round4(Graphics g){
        g.drawImage(r4, 0, 0, BGwidth, BGheight, null); 
    }
     
    public void Round5(Graphics g){
        g.drawImage(r5, 0, 0, BGwidth, BGheight, null); 
    }
    
    public void p1wins(Graphics g){
        g.drawImage(Player1wins, 0, 0, (int)(800 * Game.SCALE), (int)(450 * Game.SCALE), null); 
    }
    
    public void p2wins(Graphics g){
        g.drawImage(Player2wins, 0, 0, (int)(800 * Game.SCALE), (int)(450 * Game.SCALE), null); 
    }
     
    public void PlayAgain(Graphics g){
       
    } 
}

