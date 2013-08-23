package cc.openhome;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.nio.file.*;

public class JNotePad extends JFrame {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuSaveAs;
    private JMenuItem menuClose;
    private JMenu editMenu;
    private JMenuItem menuCut;
    private JMenuItem menuCopy;
    private JMenuItem menuPaste;
    private JMenu aboutMenu;
    private JMenuItem menuAbout;
    private JTextArea textArea;
    private JLabel stateBar;
    private JPopupMenu popUpMenu;
    private TextDAO textDAO;
    private JFileChooser fileChooser;

    public JNotePad(TextDAO textDAO) {
        this();
        this.textDAO = textDAO;
    }

    protected JNotePad() {
        initComponents();
        initEventListeners();
    }

    private void initComponents() {
        setTitle("新增纯文本文件");
        setSize(400, 300);

        // 设置“文件”菜单
        fileMenu = new JMenu("文件");
        menuOpen = new JMenuItem("打开文件");
        menuSave = new JMenuItem("保存文件");
        menuSaveAs = new JMenuItem("另存新文件");
        menuClose = new JMenuItem("关闭");

        fileMenu.add(menuOpen);
        fileMenu.addSeparator(); // 分隔线
        fileMenu.add(menuSave);
        fileMenu.add(menuSaveAs);
        fileMenu.addSeparator(); // 分隔线
        fileMenu.add(menuClose);

        // 设置“编辑”菜单        
        editMenu = new JMenu("编辑");
        menuCut = new JMenuItem("剪切");
        menuCopy = new JMenuItem("复制");
        menuPaste = new JMenuItem("粘贴");

        editMenu.add(menuCut);
        editMenu.add(menuCopy);
        editMenu.add(menuPaste);

        // 设置“关于”菜单        
        aboutMenu = new JMenu("关于");
        menuAbout = new JMenuItem("关于JNotePad");
        aboutMenu.add(menuAbout);

        // 菜单栏
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        // 设置菜单栏
        setJMenuBar(menuBar);

        // 文字编辑区
        textArea = new JTextArea();
        textArea.setFont(new Font("细明体", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        JScrollPane panel = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Container contentPane = getContentPane();
        contentPane.add(panel, BorderLayout.CENTER);

        // 状态栏
        stateBar = new JLabel("未修改");
        stateBar.setHorizontalAlignment(SwingConstants.LEFT);
        stateBar.setBorder(
                BorderFactory.createEtchedBorder());
        contentPane.add(stateBar, BorderLayout.SOUTH);

        popUpMenu = editMenu.getPopupMenu();
        fileChooser = new JFileChooser();
    }

    private void initEventListeners() {
        // 单击窗口开关按钮事件处理
        addWindowListener(
                new WindowAdapter() {

                    public void windowClosing(WindowEvent e) {
                        closeFile();
                    }
                });

        // 菜单 - 打开文件
        menuOpen.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        openFile();
                    }
                });

        // 菜单 - 保存文件
        menuSave.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });

        //菜单 - 另存新文件
        menuSaveAs.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        saveFileAs();
                    }
                });


        // 菜单 - 关闭文件
        menuClose.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        closeFile();
                    }
                });

        // 菜单 - 剪切
        menuCut.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        cut();
                    }
                });

        // 菜单 - 复制
        menuCopy.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        copy();
                    }
                });

        // 菜单 - 粘贴
        menuPaste.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        paste();
                    }
                });

        // 菜单 - 关于
        menuAbout.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        // 显示对话框
                        JOptionPane.showOptionDialog(null,
                                "JNotePad 0.1\n来自 http://openhome.cc",
                                "关于JNotePad",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null, null, null);
                    }
                });

        // 编辑区键盘事件
        textArea.addKeyListener(
                new KeyAdapter() {

                    public void keyTyped(KeyEvent e) {
                        jtextAreaActionPerformed();
                    }
                });

        // 编辑区鼠标事件
        textArea.addMouseListener(
                new MouseAdapter() {

                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            popUpMenu.show(editMenu, e.getX(), e.getY());
                        }
                    }

                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            popUpMenu.setVisible(false);
                        }
                    }
                });

        // 快捷键设置
        menuOpen.setAccelerator(
                KeyStroke.getKeyStroke(
                KeyEvent.VK_O,
                InputEvent.CTRL_MASK));
        menuSave.setAccelerator(
                KeyStroke.getKeyStroke(
                KeyEvent.VK_S,
                InputEvent.CTRL_MASK));
        menuClose.setAccelerator(
                KeyStroke.getKeyStroke(
                KeyEvent.VK_Q,
                InputEvent.CTRL_MASK));
        menuCut.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_X,
                InputEvent.CTRL_MASK));
        menuCopy.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_MASK));
        menuPaste.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_V,
                InputEvent.CTRL_MASK));
    }

    private void openFile() {
        if (stateBar.getText().equals("未修改")) {     // 文件是否为保存状态
            showFileDialog();    // 打开文件
        } else {
            int option = JOptionPane.showConfirmDialog( // 显示对话框
                    null, "文件已修改，是否保存？",
                    "保存文件？", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null);
            switch (option) {
                case JOptionPane.YES_OPTION: // 确认文件保存
                    saveFile(); // 保存文件
                    break;
                case JOptionPane.NO_OPTION:  // 放弃文件保存
                    showFileDialog();
                    break;
            }
        }
    }

    private void saveFile() {
        // 从标题栏取得文件名
        Path path = Paths.get(getTitle());
        if (Files.notExists(path)) { // 若指定的文件不存在
            saveFileAs();          // 执行另存新文件
        } else {
            try {
                // 保存文件
                textDAO.save(path.toString(), textArea.getText());
                // 设定状态栏为未修改
                stateBar.setText("未修改");
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, e.toString(),
                        "写入文档失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFileAs() {
        // 显示文件对话框
        int option = fileChooser.showDialog(null, null);
        // 如果确认选取文件
        if (option == JFileChooser.APPROVE_OPTION) {
            // 在标题栏上设定文件名
            setTitle(fileChooser.getSelectedFile().toString());
            textDAO.create(   // 建立文件
                    fileChooser.getSelectedFile().toString());
            saveFile();  //进行文件保存
        }
    }

    private void closeFile() {
        if (stateBar.getText().equals("未修改")) { // 是否已保存文件
            dispose();   // 释放窗口资源，儿后关闭程序
        } else {
            int option = JOptionPane.showConfirmDialog(
                    null, "文件已修改，是否保存？",
                    "保存文件？", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null);
            switch (option) {
                case JOptionPane.YES_OPTION:
                    saveFile();
                    break;
                case JOptionPane.NO_OPTION:
                    dispose();
            }
        }
    }

    private void cut() {
    }

    private void copy() {
    }

    private void paste() {
    }

    private void jtextAreaActionPerformed() {
    }

    private void showFileDialog() {
        int option = fileChooser.showDialog(null, null); // 文件选取对话框

        // 使用者单击确认按钮
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                setTitle( // 设定文件标题
                        fileChooser.getSelectedFile().toString());
                textArea.setText("");          // 清除前一次文件
                stateBar.setText("未修改");    // 设定状态栏

                String text = textDAO.read( // 读取文件
                        fileChooser.getSelectedFile().toString());
                textArea.setText(text);         // 附加至文字编辑区
            } catch (Throwable e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.toString(),
                        "文件打开失败", JOptionPane.ERROR_MESSAGE);
                
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JNotePad(new FileTextDAO()).setVisible(true);
            }
        });
    }
}
