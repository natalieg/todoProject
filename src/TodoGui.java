import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Natnat on 05.04.2016.
 */
public class TodoGui {

    private JFrame _frame;
    private JPanel _left;
    private JPanel _right;
    private JCheckBox _checkBox;
    private ArrayList<JCheckBox> _checkboxes;
    private JButton _resetButton;
    private JButton _refreshButton;

    private int _height = 200;
    private int _width = 800;

    private SimpleDateFormat _uhrzeit;

    private FileHandler fileHandler;
    private ListController _todayList;
    private ListController _everDayList;

    public TodoGui() throws IOException, ParseException {
        fileHandler = new FileHandler();
        fileHandler.createDirectory("lists");
        _todayList = new ListController("Weeklist");

        _frame = new JFrame("Weekly and Daily Todo");
        _frame.setLayout(new BorderLayout());
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(_width, _height);
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);

        _left = new JPanel();
        _left.setBackground(Color.white);
        _left.setPreferredSize(new Dimension(_width / 2, _height));

        _refreshButton = new JButton("Refresh");
        _frame.add(_refreshButton);
        _right = new JPanel();
        _right.setBackground(Color.white);
        _right.setPreferredSize(new Dimension(_width / 2, _height));

        _frame.add(_left, BorderLayout.WEST);
        _frame.add(_right, BorderLayout.EAST);

        ArrayList<String> changemeTodoList = _todayList.getWeekTodoList();
        _left.add(initialisiereWeeklyTodo(changemeTodoList));

        _frame.pack();

        assert _checkBox != null;
        for (final JCheckBox checkbox : _checkboxes) {
            checkbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    //Task is DONE
                    if (checkbox.isSelected()) {
                        changeTodoStatus(checkbox, "done");
                        checkbox.setBackground(Color.yellow);
                        checkbox.setForeground(new Color(0, 0, 0, 64));
                    } else {
                        changeTodoStatus(checkbox, "undone");
                        checkbox.setBackground(Color.white);
                        checkbox.setForeground(new Color(0, 0, 0, 255));
                    }
                }
            });
        }

        /**
         * Setzt die gesamte Liste auf "unfertig" bzw "false"
         */
        _resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox checkbox : _checkboxes) {
                    checkbox.setSelected(false);
                }
                 _frame.validate();
            }
        });
        //_frame.repaint();

        _refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                _left.removeAll();
                try {
                    _left.add(initialisiereWeeklyTodo(changemeTodoList));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                _frame.validate();
            }
        });

    }

    /**
     * Ändert den Status des Todos, abhängig von dem Checkbox status
     *
     * @param checkbox Die übergebene Checkbox
     * @param status   Der Status der übergebenen Checkbox "done" oder "undone"
     */
    private void changeTodoStatus(JCheckBox checkbox, String status) {
        switch (status) {
            case "done":
                try {
                    //FIXME
                    _todayList.changeTodoStatus(checkbox.getLabel() + ", false", checkbox.getLabel() + ", true");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "undone":
                try {
                    _todayList.changeTodoStatus(checkbox.getLabel() + ", true", checkbox.getLabel() + ", false");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public JPanel initialisiereWeeklyTodo(ArrayList<String> todoList) throws IOException, ParseException {
        JPanel weeklyPanel = new JPanel(new GridLayout(0, 2));
        JLabel time = new JLabel();

        weeklyPanel.setOpaque(false);
        _checkboxes = new ArrayList<JCheckBox>();
        _uhrzeit = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        String myTime = _uhrzeit.format(cal.getTime());
        Date d = _uhrzeit.parse(myTime);
        cal.setTime(d);

        for (String s : todoList) {

            String name;
            String value;
            if (s.indexOf(',') > 0) {
                String[] parts = s.split(", ");
                name = parts[0]; // 004
                value = parts[1];
            } else {
                name = s;
                value = "false";
                _todayList.changeTodoStatus(s, s + ", false");
                System.out.println("Füge false an Task " + s);
            }

            Boolean done = Boolean.valueOf(value);
            // Setzt das Label der Checkbox
            _checkBox = new JCheckBox(name);
            if (done) {
                _checkBox.setSelected(true);
                _checkBox.setBackground(Color.yellow);
                _checkBox.setForeground(new Color(0, 0, 0, 64));
                time = new JLabel("Done");

            } else {
                _checkBox.setBackground(Color.white);
                String newTime = _uhrzeit.format(cal.getTime());
                time = new JLabel(newTime);
                cal.add(Calendar.MINUTE, 30);
            }
            _checkboxes.add(_checkBox);
            weeklyPanel.add(_checkBox);
            weeklyPanel.add(time);
        }



        _resetButton = new JButton("Reset");
        weeklyPanel.add(_resetButton);
        return weeklyPanel;
    }



    public static void main(String[] args) throws IOException, ParseException {
        TodoGui testgui = new TodoGui();

    }

}
