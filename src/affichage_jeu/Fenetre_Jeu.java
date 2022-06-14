package affichage_jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Fenetre_Jeu extends JFrame implements KeyListener, ActionListener  {
    JButton human, algo, retour;
    JPanel panelJeu;

    // Constructeur Fenetre Menu
    public Fenetre_Jeu(Fenetre fen){

        panelJeu = this.Jeu(fen);
        this.setContentPane(panelJeu);
        this.requestFocus();
        this.setVisible(true);
    }


    public JPanel Jeu(Fenetre fen){
        JPanel panel = new JPanel();
        this.setTitle("~ ~ S O K O B A N ~ ~ ");
        this.setSize(805, 640);
        panel.setLayout(null);
        JLabel title = new JLabel("Sokoban");
        title.setFont(new Font("Dialog Bold", Font.BOLD, 40));

        // Creation de bouton
        human = new JButton("   Humain  ");
        algo = new JButton("   Algorithme   ");
        retour = new JButton("   Retour   ");

        // Creation d'une image de fond pour la Fenetre Graphique
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/image_menu.png"));
        JButton button = new JButton(icon);
        button.setContentAreaFilled(false);
        button.setBounds(0,-17,805,640);

        // Changer la couleur des boutons!
        human.setForeground(Color.WHITE);
        human.setBackground(Color.BLACK);

        algo.setForeground(Color.WHITE);
        algo.setBackground(Color.BLACK);

        retour.setForeground(Color.WHITE);
        retour.setBackground(Color.BLACK);

        // Dimension des boutons
        human.setBounds(350, 175, 150, 40);
        algo.setBounds(350, 275, 150, 40);
        retour.setBounds(350, 375, 150, 40);

        // Event click : Lancer Niveau 1
        human.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué jouer.");
                fen.setVisible(true);
                Fenetre_Jeu.this.setVisible(false);
                Fenetre_Jeu.this.setContentPane(panelJeu);
                Fenetre_Jeu.this.revalidate();
            }
        });
        // Event click : Lancer Fentre Niveau
        algo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué sur Niveau.");
                fen.setVisible(false);
                Fenetre_Jeu.this.setVisible(false);
                Fenetre_Jeu.this.setContentPane(panelJeu);
                Fenetre_Jeu.this.revalidate();
                try {
                    Interface_Graphique m = new Interface_Graphique();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        // Event click : Retour Fenetre Menu
        retour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué sur Retour.");
                Fenetre_Jeu.this.setVisible(false);
                Fenetre_Jeu.this.setContentPane(panelJeu);
                Fenetre_Jeu.this.revalidate();
                new Fenetre_Menu(fen);
            }
        });

        // afficher les boutons
        panel.add(human);
        panel.add(algo);
        panel.add(retour);
        panel.add(button);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){

    }

    @Override
    public void keyTyped(KeyEvent keyEvent){

    }

    @Override
    public void keyPressed(KeyEvent keyEvent){

    }

    @Override
    public void keyReleased(KeyEvent keyEvent){

    }
}




