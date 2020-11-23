import java.util.*;

public interface Sorter {

  interface Sort { public <E extends Comparable> List<E> sort(List<E> list); }

  class QuickSort implements Sort {
    public QuickSort() {}
    public <E extends Comparable> List<E> sort(List<E> list) {
      if (list.size() <= 1) { return list; }
      E pivit = list.get(0);
      List<E> smaller = new LinkedList<E>();
      List<E> larger = new LinkedList<E>();

      for (int i = 1; i < list.size(); i++ ) {
        E value = list.get(i);
        if (value.compareTo(pivit) < 0) { smaller.add(value); }
        else { larger.add(value); }
      }

      List<E> sorted = new LinkedList<E>();
      sorted.addAll(Algorithm.QuickSort.algo.sort(smaller));
      sorted.add(pivit);
      sorted.addAll(Algorithm.QuickSort.algo.sort(larger));

      return sorted;
    }
  }

  class MergeSort implements Sort {
    public MergeSort() {}
      public <E extends Comparable> List<E> sort(List<E> list) {
        if (list.size() <= 1) { return list; }
        int pivit = (list.size() / 2);
        List<E> left = Algorithm.MergeSort.algo.sort(list.subList(0, pivit));
        List<E> right = Algorithm.MergeSort.algo.sort(list.subList(pivit, list.size()));

        List<E> sorted = new LinkedList<E>();

        int l = 0;
        int r = 0;

        while (l < left.size() || r < right.size()) {
          if (l == left.size()) { sorted.add(right.get(r)); r++; }
          else if (r == right.size()) { sorted.add(left.get(l)); l++; }
          if (l < left.size() && r < right.size()) {
            if (left.get(l).compareTo(right.get(r)) < 0) { sorted.add(left.get(l)); l++; }
            else { sorted.add(right.get(r)); r++; }
          }
        }

        return sorted;
      }
  }

  enum Algorithm {
    QuickSort(new QuickSort()), MergeSort(new MergeSort());

    public <E extends Comparable> List<E> sort(List<E> list) { return algo.sort(list); }

    private Sort algo;
    private Algorithm(Sort a) { algo = a; }
  }

  public static <E extends Comparable> List<E> sort(List<E> list, Algorithm algo) {
    return algo.sort(list);
  }
}
