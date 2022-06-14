package affichage_jeu;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;


public class Fenetre_Menu extends JFrame implements KeyListener, ActionListener {
    JButton jouer, quitter, niveau;
    JPanel panelMain;

    // Constructeur Fenetre Menu
    public Fenetre_Menu(Fenetre fen){
        panelMain = this.Menu(fen);
        this.setContentPane(panelMain);
        this.requestFocus();
        this.setVisible(true);
    }


    public JPanel Menu(Fenetre fen){
        JPanel panel = new JPanel();
        this.setTitle("~ ~ S O K O B A N ~ ~ ");
        this.setSize(805, 640);
        panel.setLayout(null);
        JLabel title = new JLabel("Sokoban");
        title.setFont(new Font("Dialog Bold", Font.BOLD, 40));

        // Creation de bouton
        jouer = new JButton("   JOUER   ");
        niveau = new JButton("   NIVEAU   ");
        quitter = new JButton("   QUITTER   ");

        // Creation d'une image de fond pour la Fenetre Graphique
        ImageIcon icon = new ImageIcon(this.getClass().getResource("images/image_menu.png"));
        JButton button = new JButton(icon);
        button.setContentAreaFilled(false);
        button.setBounds(0,-17,805,640);

        // Changer la couleur des boutons!
        jouer.setForeground(Color.WHITE);
        jouer.setBackground(Color.BLACK);

        niveau.setForeground(Color.WHITE);
        niveau.setBackground(Color.BLACK);

        quitter.setForeground(Color.WHITE);
        quitter.setBackground(Color.BLACK);

        // Dimension des boutons
        jouer.setBounds(350, 175, 150, 40);
        niveau.setBounds(350, 275, 150, 40);
        quitter.setBounds(350, 375, 150, 40);

        // Event click : Lancer Niveau 1
        jouer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué jouer.");
                Fenetre_Menu.this.setVisible(false);
                Fenetre_Menu.this.setContentPane(panelMain);
                Fenetre_Menu.this.revalidate();
                new Fenetre_Jeu(fen);
            }
        });
        // Event click : Lancer Fentre Niveau
        niveau.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué sur Niveau.");
                fen.setVisible(false);
                Fenetre_Menu.this.setVisible(false);
                Fenetre_Menu.this.setContentPane(panelMain);
                Fenetre_Menu.this.revalidate();
                new Fenetre_Niveau(fen);
            }
        });
        // Event click : Quitter jeu
        quitter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Vous avez cliqué sur Quitter.");
                fen.setVisible(false);
                Fenetre_Menu.this.setVisible(false);
                Fenetre_Menu.this.setContentPane(panelMain);
                Fenetre_Menu.this.revalidate();
                System.exit(0); //pour quitter le programme !
            }
        });

        // afficher les boutons
        panel.add(jouer);
        panel.add(niveau);
        panel.add(quitter);
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




