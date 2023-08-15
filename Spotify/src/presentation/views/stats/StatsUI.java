package presentation.views.stats;

import business.entities.Statistic;
import presentation.Globals;
import presentation.views.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * StatsUI class that represents the view to display the stats.
 *
 * @author Group 6
 * @version 1.0
 */
public class StatsUI extends Screen {

    /**
     * ArrayList of Statistic instances to display.
     */
    private ArrayList<Statistic> stats;

    /**
     * Constructor method of StatsUI.
     */
    public StatsUI() {
        configLayout();
    }

    /**
     * Method that configures the general layout of the view.
     */
    private void configLayout() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);

        configTop();
        configCenter();
    }

    /**
     * Method that updates the stats of the view with the newest ones
     */
    public void setStats(ArrayList<Statistic> stats) {
        this.stats = stats;
    }

    /**
     * Method that configures the top of the view.
     */
    private void configTop() {
        JPanel topPanel = new JPanel(new GridLayout(1, 3)), box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        topPanel.setBackground(Color.black);
        box.setBackground(Color.black);

        // Left border
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.setBackground(Color.black);

        // Center
        JLabel title = new JLabel("STATISTICS");
        title.setFont(new Font("Calibri", Font.BOLD, 30));
        title.setForeground(Globals.greenSpotify);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(title);

        box.add(Box.createRigidArea(new Dimension(10, 5)));

        JLabel inspiredTitle = new JLabel("Check the global trends!");
        inspiredTitle.setFont(new Font("Calibri", Font.ITALIC, 20));
        inspiredTitle.setForeground(Globals.greenSpotify);
        inspiredTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.add(inspiredTitle);

        topPanel.add(box);

        // Right border
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.setBackground(Color.black);

        this.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Method that configures the center of the view.
     */
    private void configCenter() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
        centerPanel.setBackground(Color.black);


        centerPanel.add(new DrawPanel());


        this.add(new DrawPanel(), BorderLayout.CENTER);
    }

    /**
     * Class that represents the panel where the stats are drawn.
     */
    public class DrawPanel extends JPanel {

        /**
         * Method that draws the stats.
         *
         * @param graphics Graphics instance of the computer screen.
         */
        @Override
        public void paintComponent(Graphics graphics) {
            if (stats == null) {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setBackground(Color.black);
                g2d.setFont(new Font("Calibri", Font.BOLD, 30));
                g2d.setColor(Color.white);
                g2d.drawString("No statistics to show", (int)(this.getWidth() * 0.4), (int)(this.getHeight() * 0.4));
            }
            else {
                super.paintComponent(graphics);
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.setBackground(Color.black);
                g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

                Color[] colors = {Color.cyan, Color.yellow, Color.green, Color.blue, Color.orange,
                        Color.magenta, Color.red, Color.lightGray, Color.pink, Color.darkGray};
                g2d.setStroke(new BasicStroke(2));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Rectangle bounds = this.getBounds();
                int x = (int) (bounds.width * 0.25);
                int y = (int) (bounds.height * 0.1);
                Font font = new Font("Calibri", Font.PLAIN, 20);
                String maxString = "";
                g2d.setColor(Color.black);
                int i;

                for (i = 0; i < stats.size(); i++) {
                    if (stats.get(i).getGenre().length() > maxString.length()) {
                        maxString = stats.get(i).getGenre();
                    }
                    if (i == 9) break;
                }

                double centerY = (10 - (i + 1)) / 2.0;
                y += centerY * 40;

                String maxNum = stats.get(0).getNumSongs() + "";
                int multi = (bounds.width - (2 * x) - 15 - g2d.getFontMetrics(font).stringWidth(maxString) -
                        g2d.getFontMetrics(font).stringWidth(maxNum)) / stats.get(0).getNumSongs();


                for (i = 0; i < stats.size(); i++) {
                    // draw the genres
                    String genre = stats.get(i).getGenre();
                    g2d.setFont(font);
                    g2d.setColor(Color.white);
                    g2d.drawString(genre, x + getGenresDiff(g2d, font, genre, maxString), y);

                    // draw the bar
                    Rectangle rectangle = new Rectangle(x + 10 + g2d.getFontMetrics(font).stringWidth(maxString), y - 10,
                            stats.get(i).getNumSongs() * multi, 10);
                    g2d.setColor(colors[i]);
                    g2d.draw(rectangle);
                    g2d.fill(rectangle);

                    g2d.setColor(Color.white);
                    g2d.drawString(stats.get(i).getNumSongs() + "", x + 15 + g2d.getFontMetrics(font).stringWidth(maxString) +
                            rectangle.width, y);

                    y += 40;

                    if (i == 9) break;
                }

                int startX = x + 10 + g2d.getFontMetrics(font).stringWidth(maxString);
                int startY = (int) (bounds.height * 0.075 + 40 * centerY), endY = y - startY - 30;
                int divisor = 0, max = stats.get(0).getNumSongs();

                while (divisor == 0) {
                    if (max % 10 == 0) {
                        divisor = 10;
                    } else if (max % 7 == 0) {
                        divisor = 7;
                    } else if (max % 5 == 0) {
                        divisor = 5;
                    } else if (max % 4 == 0) {
                        divisor = 4;
                    } else if (max % 3 == 0) {
                        divisor = 3;
                    } else if (max % 2 == 0) {
                        divisor = 2;
                    } else if (max == 1) {
                        divisor = 1;
                    } else {
                        max++;
                    }
                }
                for (i = 0; i <= divisor; i++) {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setColor(Color.lightGray);
                    g2d.drawRect(startX + max * multi * i / divisor, startY, 0, endY);

                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(Color.white);
                    int num = (max * i / divisor);

                    g2d.drawString(num + "", startX - 7 + max *
                            multi * i / divisor, y - 10);

                }
            }
        }

        /**
         * Method that calculates the pixel length difference between the largest string and the current string.
         *
         * @param g2d Graphics2D instance of the computer screen.
         * @param font Font of the strings.
         * @param genre String to be drawn.
         * @param maxString Largest string.
         * @return The difference between the largest string and the current string in pixels.
         */
        public int getGenresDiff(Graphics2D g2d, Font font, String genre, String maxString) {
            return (g2d.getFontMetrics(font).stringWidth(maxString) - g2d.getFontMetrics(font).stringWidth(genre));
        }
    }

    @Override
    public void clearFields() {

    }

    /**
     * Method that retrieves the name of the component
     *
     * @return String indicating the name of the component
     */
    @Override
    public String getTag() {
        return getName();
    }
}
