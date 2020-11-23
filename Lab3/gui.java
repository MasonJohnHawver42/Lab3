import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class gui extends JPanel {

  public gui() {
    JTabbedPane tabbedPane = new JTabbedPane();

    tabbedPane.addTab("Part 1", new SorterGui());
    tabbedPane.addTab("Part 2", new RecuriveStuffGui());

    add(tabbedPane);
  }

  class RecuriveStuffGui extends JPanel implements ActionListener {
    public RecuriveStuffGui() {
      pow = new Function(2, "pow");
      changeBase = new Function(2, "change base");

      pow.inputs[0].setText("3");
      pow.inputs[1].setText("4");
      pow.output.setText("81");

      changeBase.inputs[0].setText("11");
      changeBase.inputs[1].setText("2");
      changeBase.output.setText("1011");

      pow.calc.addActionListener(this);
      changeBase.calc.addActionListener(this);

      BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
      setLayout(boxlayout);

      add(pow);
      add(changeBase);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd == "pow") {
          int val = 1;
          int power = 0;
          try {
            val = Integer.parseInt(pow.inputs[0].getText());
            power = Integer.parseInt(pow.inputs[1].getText());
          }
          catch (RuntimeException exp) { return; }

          Integer res = RecursionStuff.pow(val, power);
          pow.output.setText(res.toString());
        }

        if (cmd == "change base") {
          int dec = 1;
          int base = 10;
          try {
            dec = Integer.parseInt(changeBase.inputs[0].getText());
            base = Integer.parseInt(changeBase.inputs[1].getText());
          } catch (RuntimeException exp) { return; }

          Integer res = 0;
          try { res = RecursionStuff.convertBase(dec, base); } catch (RuntimeException exp) { return; }
          changeBase.output.setText(res.toString());
        }
      }

    private Function pow;
    private Function changeBase;

    private class Function extends JPanel {
      private Function(int input_amt, String name) {
        inputs = new JTextField[input_amt];

        calc = new JButton(name);
        output = new JTextField(10);

        add(calc);
        add(new JLabel("( "));

        for (int i = 0; i < input_amt; i++) {
          JTextField in = new JTextField(3);
          add(in); inputs[i] = in;
          if (i < input_amt - 1) { add(new JLabel(", ")); }
        }

        add(new JLabel(") = "));

        add(output);
      }

      private JTextField[] inputs;
      private JButton calc;
      private JTextField output;
    }
  }

  class SorterGui extends JPanel implements ActionListener {

    public SorterGui() {

      BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
      setLayout(boxlayout);

      JPanel selector = new JPanel();

      algos = new JComboBox(Sorter.Algorithm.values());
      select = new JButton("Select");
      selector.add(select);
      selector.add(algos);

      select.addActionListener(this);

      selections = new JPanel();

      boxlayout = new BoxLayout(selections, BoxLayout.Y_AXIS);
      selections.setLayout(boxlayout);

      selections.add(new Selection(Sorter.Algorithm.QuickSort));
      selections.add(new Selection(Sorter.Algorithm.MergeSort));

      JPanel actions = new JPanel();

      race = new JButton("Race");
      clear = new JButton("Clear");

      race.addActionListener(this);
      clear.addActionListener(this);

      actions.add(race);
      actions.add(clear);

      result = new JLabel("Hit Race to race the selected Sorting Algorithms");

      JPanel bot = new JPanel();
      bot.add(result);

      add(selector);
      add(selections);
      add(actions);
      add(bot);

    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd == "Select") {
          Sorter.Algorithm algo = (Sorter.Algorithm) algos.getSelectedItem();
          boolean add = true;
          for(Component c : selections.getComponents()) {
            if (c instanceof Selection) {
              Selection s = (Selection)c;
              if (s.algo == algo) {
                add = false; break;
          } } }
          if (add) {
            selections.add(new Selection(algo));
            revalidate();
          } else { result.setText("This Algorithm has already been Selected"); }
        }

        if (cmd == "Race") {
          generate(10000);

          Double min = -1.0;
          Sorter.Algorithm fastest = null;

          for(Component c : selections.getComponents()) {
            if (c instanceof Selection) {
              Selection s = (Selection)c;

              long startTime = System.nanoTime();
              s.algo.sort(sorte);
              long endTime = System.nanoTime();

              Double duration = (endTime - startTime) / 1000000.0;

              if (duration < min || min == -1) {
                min = duration;
                fastest = s.algo;
              }

              s.result.setText(duration.toString() + " ms");
              revalidate();
            }
          }
          if (fastest != null) {
            result.setText(fastest.toString() + " Wins!");
          } else { result.setText("Select a Sorting Algorithm with the Select Button"); }
        }

        if (cmd == "Clear") {
          for(Component c : selections.getComponents()) {
            selections.remove(c);
          }
          revalidate();
        }
    }

    private JComboBox<Sorter.Algorithm> algos;
    private JButton select;

    private JPanel selections;

    private JButton race;
    private JButton clear;

    private JLabel result;

    private class Selection extends JPanel {
      public Selection(Sorter.Algorithm a) {
        algo = a;

        name = new JLabel(algo.toString());
        result = new JTextField(10);

        add(name);
        add(result);
      }

      private Sorter.Algorithm algo;

      private JLabel name;
      private JTextField result;

    }


    private void generate(int n) {
      Random r = new Random();
      sorte = new LinkedList<Integer>();
      for (int i = 0; i < n; i++) {
        sorte.add(r.nextInt(n));
      }
    }

    LinkedList<Integer> sorte;
  }


  public static void main(String[] args) {
    JFrame frame = new JFrame("Lab 3");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);

    JPanel g = new gui();

    frame.add(g);

    frame.setVisible(true);
  }
}
