/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheat;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Patrick
 */
public class GameWindow extends javax.swing.JFrame {

    /**
     * Creates new form GameWindow
     */
    private Game game;
    private ArrayList<JButton> cardButtons;
    final int XCARDAREA = 1000;
    final int XCARDSIZE = 71;
    final int YCARDAREA = 550;
    final long sleepTime = 1000;
    private ArrayList<JLabel> card1Labels;
    private ArrayList<JLabel> card2Labels;
    private ArrayList<JLabel> card3Labels;
    private JLabel discard;
    private JLabel discardNumber;
    private JLabel roundDisplay;
    private ArrayList<String> log;
    private String[] roundString = {"Two","Three","Four","Five","Six","Seven",
                            "Eight","Nine","Ten","Jack","Queen","King","Ace"};
    public GameWindow() {
        cardButtons = new ArrayList<JButton>();
        card1Labels = new ArrayList<JLabel>();
        card2Labels = new ArrayList<JLabel>();
        card3Labels = new ArrayList<JLabel>();
        log = new ArrayList<String>();
        game = new Game();
        initComponents();
        //DRAW DISCARD
        discard = new JLabel();
        discard.setLocation(465, 355);
        discard.setSize(71,96);
        discard.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b1fv.png")));
        discard.setVisible(true);
        add(discard);
        discardNumber = new JLabel();
        discardNumber.setLocation(550, 355);
        discardNumber.setSize(200,100);
        discardNumber.setVisible(true);
        discardNumber.setFont(new java.awt.Font("Times New Roman", 1, 24));
        add(discardNumber);
        roundDisplay = new JLabel();
        roundDisplay.setLocation(350, 455);
        roundDisplay.setSize(400,100);
        roundDisplay.setVisible(true);
        roundDisplay.setFont(new java.awt.Font("Times New Roman", 1, 24));
        add(roundDisplay);
        updateDisplay();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Play Cards");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Call Cheat");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(1034, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    //PLAY CARDS BUTTON
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        Player humanPlayer = game.players[0];
        Hand playerHand =humanPlayer.hand;
        ArrayList<Card> playerCards = playerHand.getCards();
        ArrayList<Card> selectedCards = new ArrayList();
        for(int i = 0; i < playerCards.size(); i++){
            if(playerCards.get(i).getSelected()){
                selectedCards.add(playerCards.get(i));
            }
        }
//////////////////////////////////////////////////////////////////////////////
    //CHECK PLAYER TURN
///////////////////////////////////////////////////////////////////////////////
        if(selectedCards.size() > 4){
           updateLog("Too Many Cards Selected: \n Select Up To 4 Cards");
           updateDisplay();
            return;
        }else{
//////////////////////////////////////////////////////////////////////////////
    //PERFORM PLAYER TURN
///////////////////////////////////////////////////////////////////////////////
            game.quantityLastPlayed = selectedCards.size();
            game.discard.addCardsToDiscard(selectedCards);
            game.players[0].hand.removeCards(selectedCards);
            for(int j = 0; j<selectedCards.size(); j++){
                if(selectedCards.get(j).getValue() != game.round%13){
                    game.lastPlayerLied = true;
                    break;
                }else{
                    game.lastPlayerLied = false;
                }
            }
            updateLog(game.players[0].getName() + " Played " + 
                    Integer.toString(selectedCards.size()) + " on " + 
                    roundString[game.round%13]+" round");
            game.round = game.round + 1;
            updateDisplay();
            //check win condition
            if(game.checkWinCondition()){
                gameWon((game.round-1)%4);
            }
//////////////////////////////////////////////////////////////////////////////
    //Computer 1
///////////////////////////////////////////////////////////////////////////////
            int[] numplayed;
            numplayed = game.computerPlayerTurn(1);
            updateLog(game.players[1].getName() + " Played " + 
                numplayed[0] + " on " + 
                roundString[game.round%13]+" round");
            game.round = game.round + 1;
            updateDisplay();
            
//////////////////////////////////////////////////////////////////////////////
    //Computer 2
///////////////////////////////////////////////////////////////////////////////
            numplayed = game.computerPlayerTurn(2);
            updateLog(game.players[2].getName() + " Played " + 
                numplayed[0] + " on " + 
                roundString[game.round%13]+" round");
            game.round = game.round + 1;
            updateDisplay();
//////////////////////////////////////////////////////////////////////////////
    //Computer 3
///////////////////////////////////////////////////////////////////////////////
            numplayed = game.computerPlayerTurn(3);
            updateLog(game.players[3].getName() + " Played " + 
                numplayed[0] + " on " + 
                roundString[game.round%13]+" round");
            game.round = game.round + 1;
            updateDisplay();
        
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    //CALL CHEAT BUTTON
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(game.discard.discardCards.isEmpty()){
            updateLog("Cannot Call Cheat: ");
            updateLog("Discard is Empty");
            return;
        }else{
            int[] result = game.callCheat();
            updateLog(game.players[0].name + " Called Cheat!");
            if (result[0] == 0){
                updateLog(game.players[0].name + " Was Wrong! ");
                updateLog(game.players[0].name + " Recieved: " + result[1] + "Cards");
            }else{
                updateLog(game.players[0].name + " Was Right! ");
                updateLog(game.players[result[0]].name + " Recieved: " + result[1] + "Cards");
            }    
        }
        updateDisplay();
    }//GEN-LAST:event_jButton2ActionPerformed
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    //SELECT CARDS BUTTON
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
    private void cardButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //GET SOURVE
        Object temp =evt.getSource();
        //GET PLAYER CARDS
        Player humanPlayer = game.players[0];
        Hand playerHand =humanPlayer.hand;
        ArrayList<Card> playerCards = playerHand.getCards();
        //CHECK WHICH CARD WAS CLICKED
        for(int i = 0;  i < cardButtons.size(); i++){
            if(cardButtons.get(i).equals(temp)){
                //SET CARD BOOLEAN TO SELECTED
               game.players[0].hand.getCards().get(i).setSelected(!playerCards.get(i).getSelected());
               //REDISPLAY CARDS
               updateDisplay();
               revalidate();
               repaint();
               return;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private void updateDisplay() {
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //ERASE OLD CARDS
        //REMOVE BUTTONS FROM INTERFACE
        for (int j = 0; j < cardButtons.size(); j++){
            cardButtons.get(j).setVisible(false);
        }
        //REMOVE BUTTONS FROM ARRAYLIST
        cardButtons.removeAll(cardButtons);
        
        //REMOVE LABELS FROM INTERFACE
        //cp1
         for (int j = 0; j < card1Labels.size(); j++){
            card1Labels.get(j).setVisible(false);
        }
        //REMOVE LABELS FROM ARRAYLIST
        card1Labels.removeAll(card1Labels);
        //cp2
        for (int j = 0; j < card2Labels.size(); j++){
            card2Labels.get(j).setVisible(false);
        }
        //REMOVE LABELS FROM ARRAYLIST
        card2Labels.removeAll(card2Labels);
        //cp3
         for (int j = 0; j < card3Labels.size(); j++){
            card3Labels.get(j).setVisible(false);
        }
        //REMOVE LABELS FROM ARRAYLIST
        card3Labels.removeAll(card3Labels);
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //DISPLAY PLAYER HAND:
       
        //GET PLAYER CARDS TO DRAW
        Player player = game.players[0];
        Hand hand =player.hand;
        ArrayList<Card> cards = hand.getCards();
        
        //SET XCARD START AND XCARD SPACING
        int xCardStart;
        int xCardSpace;
        if(cards.size()*XCARDSIZE < XCARDAREA){
            xCardStart = (XCARDAREA - (XCARDSIZE*cards.size()))/2;
            xCardSpace = XCARDSIZE;
        }else{
            xCardSpace = (XCARDSIZE -(((XCARDSIZE*cards.size()-XCARDAREA)/cards.size())+1));
            xCardStart = 10;
        }
        
        //CREATE BUTTONS FOR PLAYER CARDS
        for(int i = 0; i < cards.size(); i++){
            cardButtons.add(i, new JButton());
            cardButtons.get(i).addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cardButtonActionPerformed(evt);
                }
            });
            //IF CARD IS SELECTED MOVE IT UP OTHERWISE DRAW ON BOTTOM
            if(cards.get(i).getSelected()){
                cardButtons.get(i).setLocation(xCardStart+(i*xCardSpace), 650);
            }else{
                cardButtons.get(i).setLocation(xCardStart+(i*xCardSpace), 700);
            }
            //SET SETTINGS FOR BUTTON AND DISPLAY
            cardButtons.get(i).setVisible(true);
            cardButtons.get(i).setSize(71, 96);
            cardButtons.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource(cards.get(i).getImageValue())));
            add(cardButtons.get(i));
        }
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //DISPLAY COMPUTERS 2 HAND
        
        player = game.players[2];
        hand =player.hand;
        cards = hand.getCards();
        
        //SET XCARD START AND XCARD SPACING
        if(cards.size()*XCARDSIZE < XCARDAREA){
            xCardStart = (XCARDAREA - (XCARDSIZE*cards.size()))/2;
            xCardSpace = XCARDSIZE;
        }else{
            xCardSpace = (XCARDSIZE -(((XCARDSIZE*cards.size()-XCARDAREA)/cards.size())+1));
            xCardStart = 10;
        }
        
        //CREATE BUTTONS FOR PLAYER CARDS
        for(int i = 0; i < cards.size(); i++){
            card2Labels.add(i, new JLabel());
            //SET SETTINGS FOR BUTTON AND DISPLAY
            card2Labels.get(i).setLocation(xCardStart+(i*xCardSpace), 1);
            card2Labels.get(i).setSize(71, 96);
            card2Labels.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b1fv.png")));
            card2Labels.get(i).setVisible(true);
            add(card2Labels.get(i));
        }
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //DRAW COMPUTER 1 CARDS
        player = game.players[1];
        hand =player.hand;
        cards = hand.getCards();
        int yCardStart;
        int yCardSpace;
        
        //SET XCARD START AND XCARD SPACING
        if(cards.size()*XCARDSIZE < YCARDAREA){
            yCardStart = (YCARDAREA - (XCARDSIZE*cards.size()))/2;
            yCardSpace = XCARDSIZE;
        }else{
            yCardSpace = (XCARDSIZE -(((XCARDSIZE*cards.size()-YCARDAREA)/cards.size())+1));
            yCardStart = 10;
        }
        
        //CREATE BUTTONS FOR PLAYER CARDS
        for(int i = 0; i < cards.size(); i++){
            card1Labels.add(i, new JLabel());
            //SET SETTINGS FOR BUTTON AND DISPLAY
            card1Labels.get(i).setLocation(900,90+ yCardStart+(i*yCardSpace));
            card1Labels.get(i).setSize(96, 71);
            card1Labels.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b1fh.png")));
            card1Labels.get(i).setVisible(true);
            add(card1Labels.get(i));
        }
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //DRAW COMPUTER 3 CARDS
        player = game.players[3];
        hand =player.hand;
        cards = hand.getCards();
        
        //SET XCARD START AND XCARD SPACING
        if(cards.size()*XCARDSIZE < YCARDAREA){
            yCardStart = (YCARDAREA - (XCARDSIZE*cards.size()))/2;
            yCardSpace = XCARDSIZE;
        }else{
            yCardSpace = (XCARDSIZE -(((XCARDSIZE*cards.size()-YCARDAREA)/cards.size())+1));
            yCardStart = 10;
        }
        
        //CREATE LABELS FOR PLAYER CARDS
        for(int i = 0; i < cards.size(); i++){
            card3Labels.add(i, new JLabel());
            //SET SETTINGS FOR BUTTON AND DISPLAY
            card3Labels.get(i).setLocation(1,90+ yCardStart+(i*yCardSpace));
            card3Labels.get(i).setSize(96,71);
            card3Labels.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("images/b1fh.png")));
            card3Labels.get(i).setVisible(true);
            add(card3Labels.get(i));
        }
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //UPDATE ROUND NUMBER
        discardNumber.setText("DISCARD SIZE:\n" + Integer.toString(game.discard.discardSize()));
///////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
        //UPDATE ROUND DISPLAY
        roundDisplay.setText("VALUE TO PLAY:" + roundString[game.round%13]);
    }
    private void updateLog(String s){
        jTextArea1.append("\n" + s);
        
    }
    private void gameWon(int i){
        updateLog(game.players[i] + "has won the game!");
    }
}
