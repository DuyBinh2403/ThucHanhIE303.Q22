package src;

import src.Product;
import src.ImageLabel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class ShopGUI extends JFrame {

    private ImageLabel lblMainImage;
    private JLabel lblMainTitle;
    private JLabel lblMainPrice;
    private JLabel lblMainBrand;
    private JLabel lblMainDesc;

    private List<Product> productList;

    public ShopGUI() {
        setTitle("Product Store");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(20, 0));
        getContentPane().setBackground(Color.WHITE);

        initData();
        setupLeftPanel();
        setupRightPanel();

        if (!productList.isEmpty()) {
            updateLeftPanel(productList.get(0));
        }
    }

    private void setupLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(350, 0));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        lblMainImage = new ImageLabel();
        lblMainImage.setPreferredSize(new Dimension(300, 300));

        lblMainTitle = new JLabel("Tên Sản Phẩm");
        lblMainTitle.setFont(new Font("Arial", Font.BOLD, 22));

        lblMainPrice = new JLabel("$0.00");
        lblMainPrice.setFont(new Font("Arial", Font.BOLD, 20));

        lblMainBrand = new JLabel("Thương hiệu");
        lblMainBrand.setForeground(Color.GRAY);

        lblMainDesc = new JLabel("Mô tả...");
        lblMainDesc.setForeground(Color.DARK_GRAY);

        leftPanel.add(lblMainImage);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(lblMainTitle);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(lblMainPrice);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(lblMainBrand);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(lblMainDesc);

        add(leftPanel, BorderLayout.WEST);
    }

    private void setupRightPanel() {
        JPanel rightGrid = new JPanel(new GridLayout(0, 4, 15, 15));
        rightGrid.setBackground(Color.WHITE);
        rightGrid.setBorder(new EmptyBorder(30, 0, 30, 30));

        for (Product p : productList) {
            JPanel card = createProductCard(p);
            rightGrid.add(card);
        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(rightGrid, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setBackground(new Color(248, 249, 250));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        javax.swing.border.Border line = BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true);
        javax.swing.border.Border padding = new EmptyBorder(15, 15, 15, 15);
        card.setBorder(BorderFactory.createCompoundBorder(line, padding));

        JPanel topText = new JPanel();
        topText.setLayout(new BoxLayout(topText, BoxLayout.Y_AXIS));
        topText.setOpaque(false);

        JLabel lblTitle = new JLabel(p.title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        topText.add(lblTitle);

        String shortDesc = p.desc.length() > 25 ? p.desc.substring(0, 25) + "..." : p.desc;
        JLabel lblDesc = new JLabel(shortDesc);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(Color.GRAY);

        topText.add(Box.createVerticalStrut(5));
        topText.add(lblDesc);

        card.add(topText, BorderLayout.NORTH);

        JLabel lblImg = new JLabel();
        lblImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblImg.setIcon(scaleImage(p.imagePath, 140, 140));
        card.add(lblImg, BorderLayout.CENTER);

        JPanel bottomText = new JPanel(new BorderLayout());
        bottomText.setOpaque(false);

        JLabel lblBrand = new JLabel(p.brand);
        lblBrand.setFont(new Font("Arial", Font.PLAIN, 12));
        lblBrand.setForeground(Color.GRAY);
        bottomText.add(lblBrand, BorderLayout.WEST);

        JLabel lblPrice = new JLabel(p.price);
        lblPrice.setFont(new Font("Arial", Font.BOLD, 16));
        bottomText.add(lblPrice, BorderLayout.EAST);

        card.add(bottomText, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateLeftPanel(p);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Set hover effect
                card.setBackground(new Color(235, 235, 235));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLUE, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));

            }
            @Override
            public void mouseExited(MouseEvent e) {
                // Remove hover effect
                card.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        return card;
    }

    // Update data after user click any shoes
    private void updateLeftPanel(Product p) {
        lblMainTitle.setText(p.title);
        lblMainPrice.setText(p.price);
        lblMainBrand.setText(p.brand);
        lblMainDesc.setText("<html>" + p.desc + "</html>");
        lblMainImage.setImageWithFade(scaleImage(p.imagePath, 300, 300));
    }

    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            Image img = new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            return new ImageIcon();
        }
    }

    private void initData() {
        productList = new ArrayList<>();
        productList.add(new Product("4DFWD PULSE SHOES", "$160.00", "Adidas", "This product is excluded from all promotional discounts and offers.", "images/img1.png"));
        productList.add(new Product("FORUM MID SHOES", "$100.00", "Adidas", "This product is excluded from all promotional discounts and offers.", "images/img2.png"));
        productList.add(new Product("SUPERNOVA SHOES", "$150.00", "Adidas", "NMD City Stock 2", "images/img3.png"));
        productList.add(new Product("Adidas", "$160.00", "Adidas", "NMD City Stock 2", "images/img4.png"));
        productList.add(new Product("Adidas", "$120.00", "Adidas", "NMD City Stock 2", "images/img5.png"));
        productList.add(new Product("4DFWD PULSE SHOES", "$160.00", "Adidas", "This product is excluded from all promotional discounts and offers.", "images/img6.png"));
        productList.add(new Product("4DFWD PULSE SHOES", "$160.00", "Adidas", "This product is excluded from all promotional discounts and offers.", "images/img1.png"));
        productList.add(new Product("FORUM MID SHOES", "$100.00", "Adidas", "This product is excluded from all promotional discounts and offers.", "images/img2.png"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShopGUI().setVisible(true));
    }
}